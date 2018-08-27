package com.andersen.dogsapp.dogs.ui.breeds;

import android.content.Context;
import android.content.Intent;
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
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.data.repositories.BreedsRepository;
import com.andersen.dogsapp.dogs.data.web.imageloader.BreedPicasso;
import com.andersen.dogsapp.dogs.data.web.retrofitapi.IResponseImageCallback;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BreedsListActivity extends AppCompatActivity
        implements IRecyclerItemListener<DogKind>, IResponseImageCallback {
    public static final String TAG = "#";
    public static final String EXTRA_SELECTED_KIND = "extra_kind";
    private static final String BREEDS_BUNDLE_KEY = "breeds_bundle_key";
    private List<DogKind> dogKinds;
    private ProgressBar progressBar;
    private DogsKindAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list);

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
        BreedsRepository.getInstance().getDogsKinds(dogBreeds -> {
            dogKinds = dogBreeds;
            runOnUiThread(this::updateUI);
        });
    }

    private void updateUI() {
        if (dogKinds != null) {
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
            BreedsRepository.getInstance().getBreedsImage(dogKindString, uriBreedString -> {
                final File breedImageFile = getImageBreedFile(getApplicationContext(), dogKindString);
                dogKindInstance.setImageString(breedImageFile.getAbsolutePath());
                // обновить поле imageString в БД
                BreedsRepository.getInstance().updateBreedDBWithUriImage(dogKindInstance);

                Target target = BreedPicasso.get(getApplicationContext())
                        .getTarget(itemProgressBar, dogKindImageView, breedImageFile);
                dogKindImageView.setTag(target);
                BreedPicasso.get(getApplicationContext())
                        .intoTarget(uriBreedString, target);
            });
        } else {
            BreedPicasso.get(getApplicationContext())
                    .intoImageView(dogKindInstance.getUriImageString(), dogKindImageView);
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

