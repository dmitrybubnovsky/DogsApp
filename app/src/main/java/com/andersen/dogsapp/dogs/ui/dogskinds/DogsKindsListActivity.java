package com.andersen.dogsapp.dogs.ui.dogskinds;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.database.DogKindsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IDatabaseCallback;
import com.andersen.dogsapp.dogs.data.web.IWebCallback;
import com.andersen.dogsapp.dogs.data.web.retrofitapi.IResponseImageCallback;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DogsKindsListActivity extends AppCompatActivity
        implements IRecyclerItemListener<DogKind>, IResponseImageCallback<String, ImageView, DogKind, ProgressBar> {
    public static final String TAG = "#";
    private static final String BREEDS_BUNDLE_KEY = "breeds_bundle_key";
    public static final String EXTRA_SELECTED_KIND = "extra_kind";

    private List<DogKind> dogKinds;
    private ProgressBar progressBar;
    private ProgressBar itemProgressBar;
    private DogsKindAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list);

//        dogKinds = (((MyApp) getApplicationContext()).dogBreedsList != null)
//                ? ((MyApp) getApplicationContext()).dogBreedsList : new ArrayList<>();

        if (savedInstanceState != null) {
            dogKinds = savedInstanceState.getParcelableArrayList(BREEDS_BUNDLE_KEY);
            Log.d(TAG, "dogKinds " + dogKinds.size());
        }

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_kinds_list);
        setSupportActionBar(toolbar);

        initViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BREEDS_BUNDLE_KEY, (ArrayList<DogKind>) dogKinds);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DataRepository.get().getDogKinds(new IWebCallback<List<DogKind>>() {
            @Override
            public void onWebCallback(List<DogKind> dogBreeds) {
                dogKinds = dogBreeds;
                DogKindsSQLiteDataSource.getInstance().addBreedsToDatabase(dogKinds);
                runOnUiThread(() -> updateUI());
            }
        }, new IDatabaseCallback<List<DogKind>>() {
            @Override
            public void onDatabaseCallback(List<DogKind> dogBreeds) {
                dogKinds = dogBreeds;
                runOnUiThread(() -> updateUI());
            }
        });
        updateUI();
    }

    private void updateUI() {
        if (dogKinds != null) {  // TODO if !dogKinds.isEmpty()
            adapter.setBreeds(dogKinds);
            adapter.setResponseBreedCallbackListener(this);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            if (progressBar != null && dogKinds != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onResponseImageListener(String dogKindString, ImageView dogKindImageView, DogKind dogKindInstance, ProgressBar itemProgressBar) {

        if (dogKindInstance.getUriImageString().isEmpty()) {
            DataRepository.get().getBreedsImage(dogKindString, new IWebCallback<String>() {
                @Override
                public void onWebCallback(String uriBreedString) {
                    final File breedImageFile = getImageBreedFile(getApplicationContext(), dogKindString);
                    dogKindInstance.setImageString(breedImageFile.getAbsolutePath());
                    // обновить поле imageString в БД uri-стрингой
                    Log.d(TAG, "dogKindInstance.getUriImageString().isEmpty() is " + dogKindInstance.getUriImageString().isEmpty());
                    DataRepository.get().updateBreedDBWithUriImage(dogKindInstance);

                    final Target target = new Target() {
                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                            dogKindImageView.setImageBitmap(bitmap);
                            itemProgressBar.setVisibility(View.INVISIBLE);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    FileOutputStream fos = null;
                                    try {
                                        fos = new FileOutputStream(breedImageFile);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            fos.close();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }).start();
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                            if (placeHolderDrawable != null) {
                            }
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                            Log.d(TAG, "DogsKindsListActivity: onBitmapFailed");
                        }
                    };

                    dogKindImageView.setTag(target);

                    Log.d(TAG, "onWebCallback " + dogKindInstance.getUriImageString());
                    Picasso.get()
                            .load(uriBreedString)
                            .placeholder(R.drawable.smiled_dog_face)
                            .error(R.drawable.smiled_dog_face)
                            .into(target);
                }
            });
        } else {

            Picasso.get()
                    .load(dogKindInstance.getUriImageString())
                    .placeholder(R.drawable.smiled_dog_face)
                    .error(R.drawable.smiled_dog_face)
                    .into(dogKindImageView);
        }
    }

    private File getImageBreedFile(Context context, String breedFileNameString) {
        File filesDir = context.getFilesDir();
        String timeStamp = String.valueOf(breedFileNameString + ".jpeg");
        return new File(filesDir, timeStamp);
    }

    @Override
    public void onRecyclerItemClick(DogKind dogKind) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_KIND, dogKind);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initViews() {
        progressBar = findViewById(R.id.network_breeds_progress_bar);
        if (dogKinds == null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        recyclerView = findViewById(R.id.breeds_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DogsKindAdapter(this, this);
    }
}

