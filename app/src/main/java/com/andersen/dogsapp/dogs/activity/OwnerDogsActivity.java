package com.andersen.dogsapp.dogs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.DataRepository;
import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.DogToolBar;

import java.util.List;

import com.andersen.dogsapp.dogs.JsonDogsDataSource;
import com.andersen.dogsapp.dogs.JsonOwnersDataSource;
import com.andersen.dogsapp.dogs.Owner;
import com.andersen.dogsapp.dogs.RecyclerViewAdapter;
import com.andersen.dogsapp.dogs.data.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.IOwnersDataSource;

import android.widget.Toast;
import android.util.Log;

public class OwnerDogsActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {
    public static final String TAG = "#";
    public static final String EXTRA_OWNER = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner";

    private DataRepository dataRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dogs_list_recyclerview);

        Owner owner = getIntent().getParcelableExtra(EXTRA_OWNER);

        // json имплементация
        IOwnersDataSource iOwnersDataSource = JsonOwnersDataSource.getInstance(this);
        IDogsDataSource iDogsDataSource = JsonDogsDataSource.getInstance(this);

        DataRepository dataRepository = DataRepository.get(iOwnersDataSource, iDogsDataSource);

        List<Dog> ownerDogs = dataRepository.getDogs(owner);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list, owner.getOwnerFullName());
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, ownerDogs, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);

        OwnerDogsActivity.DogsSpanSizeLookup dogsSpanSizeLookup = new OwnerDogsActivity.DogsSpanSizeLookup();
        layoutManager.setSpanSizeLookup(dogsSpanSizeLookup);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(Dog dog) {
        Intent intent = new Intent(getApplicationContext(), DogsInfoActivity.class);
        intent.putExtra(DogsInfoActivity.EXTRA_DOG, dog);
        startActivity(intent);
    }

    private static class DogsSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int position) {
            return (position % 3 == 0) ? 2 : 1;
        }
    }
}

