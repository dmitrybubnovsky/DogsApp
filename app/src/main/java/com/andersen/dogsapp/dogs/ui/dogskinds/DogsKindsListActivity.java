package com.andersen.dogsapp.dogs.ui.dogskinds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.ICallback;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;


import java.util.ArrayList;
import java.util.List;

public class DogsKindsListActivity extends AppCompatActivity
        implements IRecyclerItemListener<DogKind> {
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
            Log.d(TAG, "dogKinds null");
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
        if(progressBar != null && dogKinds != null){
            progressBar.setVisibility(View.INVISIBLE);
        }
        adapter.setBreeds(dogKinds);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
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

