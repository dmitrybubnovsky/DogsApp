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
    public static final String EXTRA_OWNER = "extra_owner";

    private Drawable divider;
    private RecyclerView recyclerView;

    private Owner owner;
    private DogsAdapter adapter;
    private List<Dog> ownerDogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dogs_list_recyclerview);

        owner = getIntent().getParcelableExtra(EXTRA_OWNER);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list, owner.getOwnerFullName());
        setSupportActionBar(toolbar);
// json имплементация
//        IOwnersDataSource iOwnersDataSource = JsonOwnersDataSource.getInstance(this);
//        IDogsDataSource iDogsDataSource = JsonDogsDataSource.getInstance(this);
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_menu_item:
                Intent intent = new Intent(this, NewDogFormActivity.class);
                intent.putExtra(NewDogFormActivity.EXTRA_NEW_OWNER, owner);
                startActivityForResult(intent, REQUEST_CODE_NEW_DOG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_NEW_DOG:
                    owner = data.getParcelableExtra(EXTRA_OWNER);
//                    updateUI();
                    Toast.makeText(getApplicationContext(), "" + owner.getOwnerFullName() + " now has a new dog", Toast.LENGTH_LONG).show();
                    break;
            }
        } else {
            Log.d(TAG, "RESULT was NOT OK in NewDogActivity");
        }
    }

    private void updateUI() {
        ownerDogs = DataRepository.get().getOwnerDogs(owner);

        if (ownerDogs.size() == 0) {
            Toast.makeText(this, "Пока собак нет", Toast.LENGTH_LONG).show();
            addNewDogActivity();
        } else {
            initRecyclerView();
            if (adapter == null) {
                adapter = new DogsAdapter(this, ownerDogs, this);
            } else {
                adapter.initAdapter(this, ownerDogs, this);
                adapter.notifyDataSetChanged();
            }
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onRecyclerItemClick(Dog dog) {
        Intent intent = new Intent(getApplicationContext(), DogsInfoActivity.class);
        intent.putExtra(DogsInfoActivity.EXTRA_DOG, dog);
        startActivity(intent);
    }

    private void addNewDogActivity() {
        Intent intent = new Intent(this, NewDogFormActivity.class);
        intent.putExtra(NewDogFormActivity.EXTRA_NEW_OWNER, owner);
        startActivityForResult(intent, REQUEST_CODE_NEW_DOG);
    }

    private void initRecyclerView(){
        divider = getResources().getDrawable(R.drawable.dogs_divider);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));
    }
}

