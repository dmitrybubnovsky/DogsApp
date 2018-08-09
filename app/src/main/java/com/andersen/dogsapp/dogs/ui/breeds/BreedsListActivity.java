package com.andersen.dogsapp.dogs.ui.breeds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsOnCallback;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.utils.NetworkManager;

import java.util.List;

public class BreedsListActivity extends AppCompatActivity implements IRecyclerItemListener<DogKind>, IBreedsOnCallback {
    public static final String TAG = "#";
    public static final String EXTRA_SELECTED_KIND = "extra_kind";
    public static final int SHOW_PROGRESS_BAR = 1;
    public static final int HIDE_PROGRESS_BAR = 0;
    private boolean networkOk;

    private List<DogKind> dogKinds;

    private TextView textView;
    private ProgressBar progressBar;

    private BreedsAdapter adapter;
    private  RecyclerView recyclerView;

    private static final String JSON_URL = "https://dog.ceo/api/breeds/list/all";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list); //
        Log.d(TAG, "BreedsListActivity "+ Thread.currentThread().getName());

        progressBar = findViewById(R.id.get_breeds_progress_bar);

        networkOk = NetworkManager.hasNetWorkAccess(this);


// START TEST
        textView = findViewById(R.id.textview);
//        textView.setOnClickListener(view -> {
//
//        });
// END TEST

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_kinds_list);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.breeds_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BreedsAdapter(this, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DataRepository.get().getDogKinds(list -> {
            dogKinds = list;
            Log.d(TAG, "onResume() dogKinds.size()"+dogKinds.size());
            runOnUiThread(() -> updateUI());
        });

    }

    private void updateUI() {
        adapter.setBreeds(dogKinds);
        Log.d(TAG, "updateUI()"+dogKinds.size());
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

    @Override
    public void breedsCallBack(List<DogKind> dogBreeds) {

    }
}

