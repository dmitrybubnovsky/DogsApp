package com.andersen.dogsapp.dogs.ui.owners;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.ui.HorizontalDividerItemDecoration;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.ui.MenuActivity;
import com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity;

import java.util.List;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class OwnersListActivity extends MenuActivity implements IRecyclerItemListener<Owner> {
    private static final String TAG = "#";
    private RecyclerView ownersRecyclerView;
    private OwnersAdapter ownersAdapter;
    private List<Owner> owners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);
//      json имплементация
//        IOwnersDataSource iOwnersDataSource = JsonOwnersDataSource.getInstance(this);
//        IDogsDataSource iDogsDataSource = JsonDogsDataSource.getInstance(this);
        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        ownersAdapter = new OwnersAdapter(this, this);

        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        owners = DataRepository.get().getOwners();
        if (owners.isEmpty()) {
            openAddNewOwnerScreen();
        } else {
            // только если owners is NOT empty
            updateUI();
        }
    }

    private void updateUI() {
        ownersAdapter.setOwners(owners); // owners НЕ должен быть empty
        ownersAdapter.notifyDataSetChanged();
        ownersRecyclerView.setAdapter(ownersAdapter);
    }

    private void openAddNewOwnerScreen() {
        Intent intent = new Intent(this, NewOwnerFormAcitivty.class);
        startActivity(intent);
        Toast.makeText(this, "Список владельцев пуст", Toast.LENGTH_LONG).show();
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
    public void onRecyclerItemClick(Owner owner) {
        Intent intent = new Intent(getApplicationContext(), DogsListActivity.class);
        intent.putExtra(DogsListActivity.EXTRA_OWNER, owner);
        startActivity(intent);
    }

    private void initRecyclerView() {
        Drawable divider = getResources().getDrawable(R.drawable.owners_divider);
        ownersRecyclerView = findViewById(R.id.owners_recycler_view);
        ownersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ownersRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));
    }
}