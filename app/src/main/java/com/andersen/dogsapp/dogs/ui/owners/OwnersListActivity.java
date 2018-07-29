package com.andersen.dogsapp.dogs.ui.owners;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.ui.HorizontalDividerItemDecoration;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;
import com.andersen.dogsapp.dogs.ui.MenuActivity;
import com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity;

import java.util.List;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class OwnersListActivity extends MenuActivity implements IRecyclerItemListener<Owner> {
    private static final String TAG = "#";

    private RecyclerView ownersRecyclerView;
    private OwnersAdapter ownersAdapter;
    private List<Owner> owners;
    private List<Dog> dogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);
//      json имплементация
//        IOwnersDataSource iOwnersDataSource = JsonOwnersDataSource.getInstance(this);
//        IDogsDataSource iDogsDataSource = JsonDogsDataSource.getInstance(this);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        initRecyclerView();

        updateUI();
    }

    public void initRecyclerView(){
        Drawable divider = getResources().getDrawable(R.drawable.owners_divider);

        ownersRecyclerView = findViewById(R.id.owners_recycler_view);
        ownersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ownersRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_new_menu_item:
                Intent intent = NewOwnerFormAcitivty.newIntent(getApplicationContext(), NewOwnerFormAcitivty.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateUI();
    }

    @Override
    public void onRecyclerItemClick(Owner owner) {
        Intent intent = new Intent(getApplicationContext(), DogsListActivity.class);
        intent.putExtra(DogsListActivity.EXTRA_OWNER, owner);
        startActivity(intent);
    }

    private void updateUI(){
//        Log.d(TAG, "called update. owners.size() ");
        DBHelper dbHelper = DBHelper.getInstance(this);
        IOwnersDataSource iOwnersDataSource = OwnersSQLiteDataSource.getInstance(dbHelper);
        IDogsDataSource iDogsDataSource = DogsSQLiteDataSource.getInstance(dbHelper);

        DataRepository dataRepository = DataRepository.get(iOwnersDataSource, iDogsDataSource);
        owners = dataRepository.getOwners();
        dogs = dataRepository.getDogs();
        Log.d(TAG, "update "+dogs.size());

        if (owners == null){
            Intent intent = NewOwnerFormAcitivty.newIntent(getApplicationContext(), NewOwnerFormAcitivty.class);
            startActivity(intent);
            Toast.makeText(this, "There is no any dog owner yet", Toast.LENGTH_SHORT).show();
        } else {
            if( ownersAdapter == null){
                ownersAdapter = new OwnersAdapter(this, owners, dogs, this);
                ownersRecyclerView.setAdapter(ownersAdapter);
//                Log.d(TAG, "new OwnersAdapter(this, owners = "+owners.size()+", this)");
            } else {
                Log.d(TAG, "notifyDatas "+dogs.size());
                ownersAdapter.initAdapter(this, owners, dogs, this);
                ownersAdapter.notifyDataSetChanged();
//                Log.d(TAG, "notifyDatas owners.size() "+owners.size());
            }
        }


    }
}