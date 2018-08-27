package com.andersen.dogsapp.dogs.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.repositories.OwnersRepository;
import com.andersen.dogsapp.dogs.ui.owners.NewOwnerFormFragment;
import com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment;

import java.io.File;
import java.util.List;

public class MainAppDescriptionActivity extends AppCompatActivity {
    private static final String TAG = "#";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private List<Owner> owners;

    private Fragment fragment;
    private Class fragmentClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_subscription);

        initViews();
        fragmentClass = NewOwnerFormFragment.class;
        replaceFragment(fragmentClass);

    }

    @Override
    protected void onResume(){
        super.onResume();
        owners = OwnersRepository.get().getOwners();
    }

    private void initViews() {
        toolbar = DogToolBar.init(this, R.string.app_name);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = setupDrawerToggle();

        navigationView = findViewById(R.id.navigation_drawer);
        setupDrawerContent(navigationView);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Синхронизировать состояние переключения после того,
        // как возникнет onRestoreInstanceState
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Передать любые изменения конфигурации переключателям drawer'а
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Действие home/up action bar'а должно открывать или закрывать drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);

    }

    private void setupDrawerContent(NavigationView navigViewDrawer) {
        navigViewDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return false;
                    }
                }
        );
    }

    private void selectDrawerItem(MenuItem menuItem) {
//        Fragment fragment = null;
//        Class fragmentClass = null;
        fragment = null;
        fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.owners_list_fragment:
//                startActivity(new Intent(this, OwnersListActivity.class));
                fragmentClass = OwnersListFragment.class;
                toolbar.setTitle(R.string.title_owners_list);
                break;
            case R.id.dogs_list_fragment:
                toolbar.setTitle(R.string.title_dogs_list);
//                fragmentClass = DogsListFragment.class;
                break;
            case R.id.breeds_fragment:
                toolbar.setTitle(R.string.title_breeds);
//                fragmentClass = BreedsListFragment.class;
                break;
            default:
                if (owners.isEmpty()) {
                    fragmentClass = NewOwnerFormFragment.class;
                } else {
                    fragmentClass = OwnersListFragment.class;
                }
        }

        replaceFragment(fragmentClass, );
//////
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    private void replaceFragment(Class<? extends BaseFragment> fragmentClass){
        try {
            fragment = (Fragment) (fragmentClass != null ? fragmentClass.newInstance() : null);
            Log.d(TAG, "selectDrawerItem "+fragment.getClass().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.host_fragment_container, fragment)
                .commit();
    }
}


