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
import com.andersen.dogsapp.dogs.data.entities.Breed;
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
        implements IRecyclerItemListener<Breed>, IResponseImageCallback {
    public static final String TAG = "#";
    public static final String EXTRA_SELECTED_BREED = "extra_breed";
    private static final String BREEDS_BUNDLE_KEY = "breeds_bundle_key";
    private List<Breed> breeds;
    private ProgressBar progressBar;
    private BreedsAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list);

        if (savedInstanceState != null) {
            breeds = savedInstanceState.getParcelableArrayList(BREEDS_BUNDLE_KEY);
            Log.d(TAG, "breeds " + breeds.size());
        }

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_breeds_list);
        setSupportActionBar(toolbar);

        initViews();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(BREEDS_BUNDLE_KEY, (ArrayList<Breed>) breeds);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BreedsRepository.getInstance().getBreeds(dogBreeds -> {
            breeds = dogBreeds;
            runOnUiThread(this::updateUI);
        });
    }

    private void updateUI() {
        if (breeds != null) {
            adapter.setBreeds(breeds);
            adapter.setResponseBreedCallbackListener(this);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            if (progressBar != null && breeds != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onResponseImageListener(String breedString, ImageView breedImageView, Breed breedEntity, ProgressBar itemProgressBar) {

        if (breedEntity.getUriImageString().isEmpty()) {
            BreedsRepository.getInstance().getBreedsImage(breedString, uriBreedString -> {
                // обновить поле imageString в БД
                BreedsRepository.getInstance().updateBreedDBWithUriImage(breedEntity);
                // сохранить картинку породы
                saveBreedImageSetToView(uriBreedString, breedString, breedImageView, breedEntity, itemProgressBar);
            });
        } else {
            BreedPicasso.getInstance(getApplicationContext())
                    .intoImageView(breedEntity.getUriImageString(), breedImageView);
        }
    }

    private void saveBreedImageSetToView(String uriBreedString, String breedString, ImageView breedImageView, Breed breedInstance, ProgressBar itemProgressBar) {
        final File breedImageFile = getImageBreedFile(getApplicationContext(), breedString);
        breedInstance.setImageString(breedImageFile.getAbsolutePath());

        Target target = BreedPicasso.getInstance(getApplicationContext())
                .getTarget(itemProgressBar, breedImageView, breedImageFile);
        breedImageView.setTag(target);
        BreedPicasso.getInstance(getApplicationContext())
                .intoTarget(uriBreedString, target);
    }

    private File getImageBreedFile(Context context, String breedFileNameString) {
        File filesDir = context.getFilesDir();
        String timeStamp = String.valueOf(breedFileNameString + ".jpeg");
        return new File(filesDir, timeStamp);
    }

    @Override
    public void onRecyclerItemClick(Breed breed) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_BREED, breed);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initViews() {
        progressBar = findViewById(R.id.network_breeds_progress_bar);
        if (breeds == null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        recyclerView = findViewById(R.id.breeds_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BreedsAdapter(this, this);
    }
}

