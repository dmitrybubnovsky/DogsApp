package com.andersen.dogsapp.dogs.activity;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.content.Intent;
import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import com.andersen.dogsapp.dogs.DogToolBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import com.andersen.dogsapp.dogs.data.DataRepository;
import android.support.v7.widget.GridLayoutManager;

import com.andersen.dogsapp.dogs.recyclerviewers.HorizontalDividerItemDecoration;
import com.andersen.dogsapp.dogs.recyclerviewers.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.recyclerviewers.RecyclerViewDogsAdapter;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;

public class OwnerDogsActivity extends AppCompatActivity implements IRecyclerItemListener<Dog>{
    public static final String TAG = "#";
    public static final String EXTRA_OWNER = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dogs_list_recyclerview);

        Drawable divider = getResources().getDrawable(R.drawable.dogs_divider);

        Owner owner = getIntent().getParcelableExtra(EXTRA_OWNER);

        // json имплементация
//        IOwnersDataSource iOwnersDataSource = JsonOwnersDataSource.getInstance(this);
//        IDogsDataSource iDogsDataSource = JsonDogsDataSource.getInstance(this);

        // sqlite имплементация
        DBHelper dbHelper = DBHelper.getInstance(this);
        IOwnersDataSource iOwnersDataSource = OwnersSQLiteDataSource.getInstance(dbHelper);
        IDogsDataSource iDogsDataSource = DogsSQLiteDataSource.getInstance(dbHelper);

        DataRepository dataRepository = DataRepository.get(iOwnersDataSource, iDogsDataSource);

        List<Dog> ownerDogs = dataRepository.getDogs(owner);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list, owner.getOwnerFullName());
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewDogsAdapter adapter = new RecyclerViewDogsAdapter(this, ownerDogs, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecyclerItemClick (Dog dog) {
        Intent intent = new Intent(getApplicationContext(), DogsInfoActivity.class);
        intent.putExtra(DogsInfoActivity.EXTRA_DOG, dog);
        startActivity(intent);
    }
}

