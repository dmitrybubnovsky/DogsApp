package com.andersen.dogsapp.dogs.ui.dogskinds;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;

public class DogsKindsListActivity extends AppCompatActivity implements IRecyclerItemListener<DogKind> {
    public static final String TAG = "#";
    public static final String EXTRA_SELECTED_KIND = "extra_kind";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_kind_selector);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_kinds_list);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.dogs_kinds_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DogsKindAdapter adapter = new DogsKindAdapter(this, this);
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
