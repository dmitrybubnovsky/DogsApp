package com.andersen.dogsapp.dogs.ui.dogskinds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.ICallback;
import com.andersen.dogsapp.dogs.data.web.retrofitapi.IResponseBreedCallback;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class DogsKindsListActivity extends AppCompatActivity
        implements IRecyclerItemListener<DogKind>, IResponseBreedCallback <String, ImageView, DogKind> {
    public static final String TAG = "#";
    private static final String BREEDS_BUNDLE_KEY = "breeds_bundle_key";
    public static final String EXTRA_SELECTED_KIND = "extra_kind";

    private List<DogKind> dogKinds;
    private ProgressBar progressBar;
    private DogsKindAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list);
        if(savedInstanceState != null){
            dogKinds = savedInstanceState.getParcelableArrayList(BREEDS_BUNDLE_KEY);
            Log.d(TAG, "dogKinds "+ dogKinds.size());
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
        if (dogKinds == null){
            DataRepository.get().getDogKinds(new ICallback<List<DogKind>>() {
                @Override
                public void onResponseICallback(List<DogKind> dogBreeds) {
                    dogKinds = dogBreeds;
                    runOnUiThread(() -> updateUI());
                }
            });
        } else {
            updateUI();
        }
    }

    private void updateUI() {
        adapter.setBreeds(dogKinds);
        adapter.setResponseBreedCallbackListener(this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        if(progressBar != null && dogKinds != null){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResponseBreedCallbackListener(String dogKindString, ImageView dogKindImageView, DogKind dogKindInstance) {
        DataRepository.get().getBreedsImage(dogKindString, new ICallback<String>() {
            @Override
            public void onResponseICallback(String uriBreedString) {
                dogKindInstance.setImageString(uriBreedString);
                Log.d(TAG, "onResponseICallback "+ dogKindInstance.geUriImageString());
                Picasso.get()
                        .load(uriBreedString)
                        .placeholder(R.drawable.afghan_hound)
                        .error(R.drawable.afghan_hound)
                        .into(dogKindImageView);
            }
        });
    }

    @Override
    public void onRecyclerItemClick(DogKind dogKind) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_KIND, dogKind);
        setResult(RESULT_OK, intent);

        Log.d(TAG, " getBreedsImage( "+dogKind.getKind()+" )");
        DataRepository.get().getBreedsImage(dogKind.getKind(), new ICallback<String>() {
            @Override
            public void onResponseICallback(String breedString) {
                Log.d(TAG, " onResponseICallback = "+ breedString);
            }
        });
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

