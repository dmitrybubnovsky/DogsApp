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

import java.util.List;

import static com.andersen.dogsapp.dogs.ui.owners.NewOwnerFormFragment.NEW_OWNER_TAG;
import static com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment.OWNERS_TAG;

public class MainAppDescriptionActivity extends AppCompatActivity
        implements OwnersListFragment.IFragmentOwnerListener<Owner> {
    private static final String TAG = "#";
    private static final String DOGS_TAG = "dogs_tag";
    private static final String BREEDS_TAG = "breeds_tag";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragMan;

//    public OwnersListFragment.IFragmentOwnerListener callback;

    private List<Owner> owners;
    private String fragmentTag;

    private Fragment fragment;
    private Class fragmentClass;

//    public void setOnFragmentListener(OwnersListFragment.IFragmentOwnerListener callback) {
//        this.callback = callback;
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_subscription);

        initViews();

    }

    private void initFragment(Class<?> fragmentClass, String fragmentTag, int stringResId) {
        this.fragmentClass = fragmentClass;
        toolbar.setTitle(stringResId);
        this.fragmentTag = fragmentTag;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragMan = getSupportFragmentManager();
        Log.d(TAG, "Main: onResume");
        owners = OwnersRepository.get().getOwners();

        if (getSupportFragmentManager().findFragmentByTag(OWNERS_TAG) == null) {
            initFragment(OwnersListFragment.class, OWNERS_TAG, R.string.title_owners_list);
            addFragment(fragmentClass, fragmentTag);
        }
        if (owners.isEmpty()) {
            initFragment(NewOwnerFormFragment.class, NEW_OWNER_TAG, R.string.toolbar_title_add_owner);
            replaceFragment(fragmentClass, fragmentTag);
            Log.d(TAG, "getFragments.size " + fragMan.getFragments().size());
        }
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
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        fragment = null;
        fragmentClass = null;

        switch (menuItem.getItemId()) {
            case R.id.owners_list_fragment:
//                startActivity(new Intent(this, OwnersListActivity.class));
                fragmentClass = OwnersListFragment.class;
                toolbar.setTitle(R.string.title_owners_list);
                fragmentTag = OwnersListFragment.class.getName();
                break;
            case R.id.dogs_list_fragment:
                toolbar.setTitle(R.string.title_dogs_list);
//                fragmentTag = getResources().getString(R.string.title_dogs_list);
//                fragmentClass = DogsListFragment.class;
                break;
            case R.id.breeds_fragment:
                toolbar.setTitle(R.string.title_breeds);
//                fragmentClass = BreedsListFragment.class;
                break;
            default:
//                if (owners.isEmpty()) {
//                    fragmentClass = NewOwnerFormFragment.class;
//                    fragmentTag = NewOwnerFormFragment.class.getName();
//
//                } else {
//                    fragmentClass = OwnersListFragment.class;
//                    fragmentTag = OwnersListFragment.class.getName();
//                }
        }

        replaceFragment(fragmentClass, fragmentTag);

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    private void replaceFragment(Class<?> fragmentClass, String fragmentTag) {
        try {
            fragment = (Fragment) (fragmentClass != null ? fragmentClass.newInstance() : null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragMan.beginTransaction()
                .replace(R.id.host_fragment_container, fragment, fragmentTag)
                .commit();
    }

    private void addFragment(Class<?> fragmentClass, String fragmentTag) {
        try {
            fragment = (Fragment) (fragmentClass != null ? fragmentClass.newInstance() : null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragMan.beginTransaction()
                .add(R.id.host_fragment_container, fragment, fragmentTag)
                .commit();
        Log.d(TAG, "added " + fragment.getClass().toString());
    }

    @Override
    public void onFragmentOwnerListener(Owner owner) {
        Log.d(TAG, "HOST-activity: onFragmentOwnerListener owner "+ owner.getOwnerName());
    }
}


