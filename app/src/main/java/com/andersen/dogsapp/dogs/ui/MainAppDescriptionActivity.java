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
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.repositories.OwnersRepository;
import com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment;
import com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment;
import com.andersen.dogsapp.dogs.ui.owners.NewOwnerFormFragment;
import com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment;

import java.util.List;

import static com.andersen.dogsapp.dogs.ui.owners.NewOwnerFormFragment.NEW_OWNER_TAG;
import static com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment.OWNERS_TAG;

public class MainAppDescriptionActivity extends AppCompatActivity
        implements OwnersListFragment.IFragmentOwnerListener<Owner>,
        DogsListFragment.IAddDogFragmentListener<Owner>,
        OwnersListFragment.IaddOwnerFragmentListener {
    private static final String TAG = "#";
    private static final String BREEDS_TAG = "breeds_tag";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private FragmentManager fragManager;

    private List<Owner> owners;
    private String fragmentTag;

    private Fragment fragment;
    private Class fragmentClass;

    private static final String BACK_STACK_ROOT_TAG = "root_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_subscription);
        fragManager = getSupportFragmentManager();

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
        Log.d(TAG, "Main: onResume");
        owners = OwnersRepository.get().getOwners();

        if (fragManager.findFragmentByTag(OWNERS_TAG) == null) {
            Log.d(TAG, "!!!!!!!!!!!!!   findFragmentByTag(OWNERS_TAG) == null)");
            initFragment(OwnersListFragment.class, OWNERS_TAG, R.string.title_owners_list);
            replaceAddToBackstackFragment(fragmentClass, fragmentTag);
        }
        if (owners.isEmpty()) {
            initFragment(NewOwnerFormFragment.class, NEW_OWNER_TAG, R.string.toolbar_title_add_owner);
            replaceAddToBackstackFragment(fragmentClass, fragmentTag);
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
//            case R.id.add_new_menu_item:
//                Log.d(TAG, "case R.id.add_new_menu_item: ");
//
//                startNewOwnerFormFragment();
//                break;
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
                fragmentClass = OwnersListFragment.class;
                toolbar.setTitle(R.string.title_owners_list);
                fragmentTag = OWNERS_TAG;
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
                fragmentClass = OwnersListFragment.class;
                fragmentTag = OWNERS_TAG;
        }

        replaceAddToBackstackFragment(fragmentClass, fragmentTag);

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }

    private void replaceAddToBackstackFragment(Class<?> fragmentClass, String fragmentTag) {
        if (fragManager.findFragmentByTag(fragmentTag) == null) {
            try {
                fragment = (Fragment) (fragmentClass != null ? fragmentClass.newInstance() : null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, fragment, fragmentTag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    // Overridden callback method of IFragmentOwnerListener<Owner> interface
    // which is called from OwnersListFragment
    @Override
    public void onFragmentOwnerListener(Owner owner) {
//
        if(owner.getDogs().isEmpty()){
            startNewDogFormFragment(owner);
        } else {
            startDogsListFragment(owner); // TODO fix
        }

//        findFragmentByTagAndAdd("DogsListFragment", DogsListFragment.DOGS_TAG, DogsListFragment.DOGS_ARG, (Owner)owner);

    }

    // Overridden callback method of IAddDogFragmentListener<Dog> interface
    // which is called in DogsListFragment in menu
    @Override
    public void onAddDogFragmentListener(Owner owner) {
        startNewDogFormFragment(owner);
    }

    // Overridden callback method of IaddOwnerFragmentListener interface
    // which is called from OwnersListFragment
    @Override
    public void onNewOwnerFragmentListener() {
        startNewOwnerFormFragment();
    }

    private void startNewDogFormFragment(Owner owner) {
        Fragment fragm = fragManager.findFragmentByTag(NewDogFormFragment.NEW_DOG_ARG);
        if (fragm == null) {
            fragm = NewDogFormFragment.newInstance(owner);
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, fragm)
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.d(TAG, "NOT added ");
        }
    }

    private void startDogsListFragment(Owner owner) {
        Fragment fragm = fragManager.findFragmentByTag(DogsListFragment.DOGS_TAG);
        if (fragm == null) {
            fragm = DogsListFragment.newInstance(owner);
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, fragm)
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.d(TAG, "NOT added ");
        }
    }


    private void startNewOwnerFormFragment() {
        Fragment fragm = fragManager.findFragmentByTag(NewOwnerFormFragment.NEW_OWNER_TAG);
        if (fragm == null) {
            fragm = NewOwnerFormFragment.newInstance();
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, fragm)
                    .addToBackStack(null)
                    .commit();
        } else {
            Log.d(TAG, "MainActivity: startNewOwnerFormFragment: frag != null ");
        }
    }



    @Override
    public void onBackPressed() {
        if (fragManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


//
//    private <T> void findFragmentByTagAndAdd(String fragmentName, String fragmentTag, String keyArgs, T t ) {
//        Fragment fragm = fragManager.findFragmentByTag(fragmentTag);
//        if (fragm == null) {
//            Bundle bundleArgs = new Bundle();
//            bundleArgs.putParcelable(keyArgs, (Parcelable) t);
//            fragm = Fragment.instantiate(this, fragmentName, bundleArgs);
//
//            fragManager.beginTransaction()
//                    .add(R.id.host_fragment_container, fragm)
//                    .commit();
//        }
//    }

}




