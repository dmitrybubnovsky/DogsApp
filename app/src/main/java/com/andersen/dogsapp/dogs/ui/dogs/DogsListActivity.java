package com.andersen.dogsapp.dogs.ui.dogs;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.content.Intent;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.andersen.dogsapp.dogs.ui.AppTextView;
import com.andersen.dogsapp.dogs.ui.DogToolBar;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.andersen.dogsapp.dogs.data.DataRepository;

import com.andersen.dogsapp.dogs.ui.HorizontalDividerItemDecoration;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;
import com.andersen.dogsapp.dogs.ui.MenuActivity;

public class DogsListActivity extends MenuActivity implements IRecyclerItemListener<Dog> {
    public final int REQUEST_CODE_NEW_DOG = 2;
    public static final String TAG = "#";
    public static final String EXTRA_OWNER = "com.andersen.dogsapp.dogs.activity.DogsListActivity.owner";

    Owner owner;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_new_menu_item:
//                Intent intent = NewDogFormAcitivty.newIntent(getApplicationContext(), NewDogFormAcitivty.class);
//                startActivityForResult(intent, REQUEST_CODE_NEW_DOG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NEW_DOG:
                    owner = getIntent().getParcelableExtra(EXTRA_OWNER);

                    Toast.makeText(getApplicationContext(), ""+owner.getOwnerFullName()+"+ now has a new dog)",Toast.LENGTH_LONG).show();
                    break;
            }
        } else {
            Log.d(TAG, "RESULT was NOT OK in NewDogActivity");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dogs_list_recyclerview);

        owner = getIntent().getParcelableExtra(EXTRA_OWNER);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        Drawable divider = getResources().getDrawable(R.drawable.dogs_divider);
// json имплементация
//        IOwnersDataSource iOwnersDataSource = JsonOwnersDataSource.getInstance(this);
//        IDogsDataSource iDogsDataSource = JsonDogsDataSource.getInstance(this);

        // sqlite имплементация
        DBHelper dbHelper = DBHelper.getInstance(this);
        IOwnersDataSource iOwnersDataSource = OwnersSQLiteDataSource.getInstance(dbHelper);
        IDogsDataSource iDogsDataSource = DogsSQLiteDataSource.getInstance(dbHelper);

        DataRepository dataRepository = DataRepository.get(iOwnersDataSource, iDogsDataSource);

        List<Dog> ownerDogs = dataRepository.getOwnerDogs(owner);

        // если owner без единой собаки
        if ( ownerDogs.size() == 0){
//        if (owner.getDogsIds().length == 0){
            Toast.makeText(this, "DogsListActivity:Owner doesn't have any dog", Toast.LENGTH_SHORT).show();
            Intent intent = NewDogFormActivity.newIntent(this, NewDogFormActivity.class);
            intent.putExtra(NewDogFormActivity.EXTRA_NEW_OWNER, owner);
            startActivityForResult(intent, REQUEST_CODE_NEW_DOG);
        }


        TextView ownerName =  AppTextView.newInstance(this, R.id.owner_name_detail_textview)
                .style(this, R.style.BoldRobotoThin35sp)
                .build();

        ownerName.setText("" + owner.getOwnerFullName());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        DogsAdapter adapter = new DogsAdapter(this, ownerDogs, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecyclerItemClick(Dog dog) {
        Intent intent = new Intent(getApplicationContext(), DogsInfoActivity.class);
        intent.putExtra(DogsInfoActivity.EXTRA_DOG, dog);
        startActivity(intent);
    }
}

