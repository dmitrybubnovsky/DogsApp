package com.andersen.dogsapp.dogs.ui.owners;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_menu_item:
                Intent intent = new Intent(this, NewOwnerFormAcitivty.class);
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

    private void updateUI() {
        owners = DataRepository.get().getOwners();
        dogs = DataRepository.get().getDogs();

        if (owners.size() == 0) {
            Intent intent = new Intent(this, NewOwnerFormAcitivty.class);
            startActivity(intent);
            Toast.makeText(this, "Список владельцев пуст", Toast.LENGTH_LONG).show();
        } else {
            if (ownersAdapter == null) {
                ownersAdapter = new OwnersAdapter(this, owners, dogs, this);
                ownersRecyclerView.setAdapter(ownersAdapter);
            } else {
                ownersAdapter.initAdapter(this, owners, dogs, this);
                ownersAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initRecyclerView() {
        Drawable divider = getResources().getDrawable(R.drawable.owners_divider);

        ownersRecyclerView = findViewById(R.id.owners_recycler_view);
        ownersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ownersRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));
    }
}