package com.andersen.dogsapp.dogs.ui.breeds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.services.WebBreedService;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.utils.NetworkHelper;

import java.util.List;

public class BreedsListActivity extends AppCompatActivity implements IRecyclerItemListener<DogKind> {
    public static final String TAG = "#";
    public static final String EXTRA_SELECTED_KIND = "extra_kind";
    public static final int SHOW_PROGRESS_BAR = 1;
    public static final int HIDE_PROGRESS_BAR = 0;
    private boolean networkOk;

    private List<DogKind> dogKinds;

    private TextView textView;
    private ProgressBar progressBar;



    private static final String JSON_URL = "https://dog.ceo/api/breeds/list/all";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list); //
        Log.d(TAG, "BreedsListActivity "+ Thread.currentThread().getName());

        progressBar = findViewById(R.id.get_breeds_progress_bar);

        networkOk = NetworkHelper.hasNetWorkAccess(this);

        dogKinds = DataRepository.get().getDogKinds();
// START TEST
        textView = findViewById(R.id.textview);
        textView.setOnClickListener(view -> {
            Log.d(TAG, "dogKinds.size() "+dogKinds.size());
        });
// END TEST

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_kinds_list);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.breeds_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BreedsAdapter adapter = new BreedsAdapter(this, this);
        recyclerView.setAdapter(adapter);

    }



    @Override
    public void onRecyclerItemClick(DogKind dogKind) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_KIND, dogKind);
        setResult(RESULT_OK, intent);
        finish();
    }


}

