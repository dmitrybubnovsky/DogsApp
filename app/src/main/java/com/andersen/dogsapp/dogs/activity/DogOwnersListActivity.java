package com.andersen.dogsapp.dogs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.RecyclerViewDogsAdapter;
import com.andersen.dogsapp.dogs.RecyclerViewOwnersAdapter;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.DogToolBar;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;

import java.util.List;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class DogOwnersListActivity extends AppCompatActivity
        implements RecyclerViewOwnersAdapter.OwnerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);

//      json имплементация
//        IOwnersDataSource iOwnersDataSource = JsonOwnersDataSource.getInstance(this);
//        IDogsDataSource iDogsDataSource = JsonDogsDataSource.getInstance(this);

        // sqlite имплементация
        DBHelper dbHelper = DBHelper.getInstance(this);
        IOwnersDataSource iOwnersDataSource = OwnersSQLiteDataSource.getInstance(dbHelper);
        IDogsDataSource iDogsDataSource = DogsSQLiteDataSource.getInstance(dbHelper);

        DataRepository dataRepository = DataRepository.get(iOwnersDataSource, iDogsDataSource);

        List<Owner> owners = dataRepository.getOwners();

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        RecyclerView ownersRecyclerView = findViewById(R.id.owners_recycler_view);
        ownersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewOwnersAdapter ownersAdapter = new RecyclerViewOwnersAdapter(this, owners, this);
        ownersRecyclerView.setAdapter(ownersAdapter);
    }

    @Override
    public void onItemClick(Owner owner) {
        Intent intent = new Intent(getApplicationContext(), OwnerDogsActivity.class);
        intent.putExtra(OwnerDogsActivity.EXTRA_OWNER, owner);
        startActivity(intent);
    }
}