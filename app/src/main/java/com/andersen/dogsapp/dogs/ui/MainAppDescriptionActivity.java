package com.andersen.dogsapp.dogs.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.andersen.dogsapp.dogs.AppFragmentManager;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.repositories.OwnersRepository;
import com.andersen.dogsapp.dogs.ui.breeds.BreedsListFragment;
import com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment;
import com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment;
import com.andersen.dogsapp.dogs.ui.owners.NewOwnerFormFragment;
import com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment;

import java.util.List;

import static com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment.DOGS_ARG;
import static com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment.DOGS_TAG;
import static com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment.OWNER_ARG;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment.BREED_ARG;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment.NEW_DOG_ARG;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment.NEW_DOG_TAG;
import static com.andersen.dogsapp.dogs.ui.owners.NewOwnerFormFragment.NEW_OWNER_TAG;
import static com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment.OWNERS_TAG;

public class MainAppDescriptionActivity extends AppCompatActivity
        implements OwnersListFragment.IFragmentOwnerListener<Owner>,
        NewOwnerFormFragment.IAddedOwnerFragmentListener,
        DogsListFragment.IAddDogFragmentListener<Owner>,
        OwnersListFragment.IaddOwnerFragmentListener,
        NewDogFormFragment.ISetBreedFragmentListener<Owner>,
        NewDogFormFragment.IDogFinishedFragmentListener<Owner>,
        BreedsListFragment.IOnBreedFragmentListener<DogKind, Owner> {
    private static final String TAG = "#";
    private static final String BREEDS_TAG = "breeds_tag";
    private static final String NEW_OWNER_FRAGMENT = NewOwnerFormFragment.class.getName();
    private static final String OWNERS_FRAGMENT = OwnersListFragment.class.getName();
    private static final String NEW_DOG_FRAGMENT = NewDogFormFragment.class.getName();
    private static final String DOGS_FRAGMENT = DogsListFragment.class.getName();
    private static final String BREEDS_FRAGMENT = BreedsListFragment.class.getName();
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Main: onResume");
        owners = OwnersRepository.get().getOwners();

//        if (fragManager.findFragmentByTag(OWNERS_TAG) == null) {}
//            initFragment(OwnersListFragment.class, OWNERS_TAG, R.string.title_owners_list);
//            AppFragmentManager.getInstance(this)
//                    .replaceAddToBackStack(this, fragmentClass.getName(), fragmentTag);



        if (owners.isEmpty()) {
//            initFragment(NewOwnerFormFragment.class, NEW_OWNER_TAG, R.string.toolbar_title_add_owner);
//            AppFragmentManager.getInstance(this)
//                    .replaceAddToBackStack(this, fragmentClass.getName(), fragmentTag);
            addNewOwnerFragment();
        } else {
            addOwnersListFragment();
        }
    }

    public void addOwnersListFragment(){
        Fragment ownersFragment = fragManager.findFragmentByTag(OWNERS_TAG);
        if(ownersFragment == null){
            ownersFragment = Fragment.instantiate(this, OWNERS_FRAGMENT);
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, ownersFragment)
//                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }
    }

    public void addNewOwnerFragment(){
        Fragment newOwnerFragment = fragManager.findFragmentByTag(NEW_OWNER_TAG);
        if(newOwnerFragment == null){
            newOwnerFragment = Fragment.instantiate(this, NEW_OWNER_FRAGMENT);
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, newOwnerFragment)
//                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }
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

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void initFragment(Class<?> fragmentClass, String fragmentTag, int stringResId) {
        this.fragmentClass = fragmentClass;
        toolbar.setTitle(stringResId);
        this.fragmentTag = fragmentTag;
    }

    private void initViews() {
        toolbar = DogToolBar.init(this, R.string.app_name);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = setupDrawerToggle();

        navigationView = findViewById(R.id.navigation_drawer);
        setupDrawerContent(navigationView);
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

        AppFragmentManager.getInstance(this)
                .replaceAddToBackStack(this, fragmentClass.getName(), fragmentTag);

        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }


    // callback метода IFragmentOwnerListener<Owner>
    // вызывается из OwnersListFragment клик по списку
    @Override
    public void onFragmentOwnerListener(Owner owner) {
        if (owner.getDogs().isEmpty()) {
            // запускает NewDogFormFragment
//            AppFragmentManager.getInstance(this)
//                    .replaceFragmentWithEntity(this, NEW_DOG_FRAGMENT,
//                            NEW_DOG_TAG, NEW_DOG_ARG, owner);
            Bundle bundleArgs = new Bundle();
            bundleArgs.putParcelable(NEW_DOG_ARG, (Parcelable) owner);

            Fragment newDogFragment = fragManager.findFragmentByTag(NEW_DOG_TAG);
            if (newDogFragment == null) {
                newDogFragment = Fragment.instantiate(this, NEW_DOG_FRAGMENT, bundleArgs);
                fragManager.beginTransaction()
                        .replace(R.id.host_fragment_container, newDogFragment, NEW_DOG_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        } else {
            // запускает DogsListFragment
            AppFragmentManager.getInstance(this)
                    .replaceFragmentWithEntity(this, DOGS_FRAGMENT,
                            DOGS_TAG, OWNER_ARG, owner);
        }
    }

    // callback метода of IAddDogFragmentListener<Dog> вызывается из DogsListFragment в menu
    // start NewDogFormFragment(owner)
    @Override
    public void onAddDogFragmentListener(Owner owner) {
//        AppFragmentManager.getInstance(this)
//                .replaceFragmentWithEntity(this, NEW_DOG_FRAGMENT,
//                        NEW_DOG_TAG, NEW_DOG_ARG, owner);
        Bundle bundleArgs = new Bundle();
        bundleArgs.putParcelable(NEW_DOG_ARG, (Parcelable) owner);

        Fragment newDogFragment = fragManager.findFragmentByTag(NEW_DOG_TAG);
        if (newDogFragment == null) {
            newDogFragment = Fragment.instantiate(this, NEW_DOG_FRAGMENT, bundleArgs);

            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, newDogFragment, NEW_DOG_TAG)
                    .addToBackStack(null)
                    .commit();
        }
    }

    // callback метода  вызывается из BreedsListFragment
    // запускает NewDogFragment
    @Override
    public void onBreedFragmentListener(DogKind dogKind, Owner owner) {
        Bundle bundleArgs = new Bundle();
        bundleArgs.putParcelable(BREED_ARG, (Parcelable) dogKind);
        bundleArgs.putParcelable(NEW_DOG_ARG, (Parcelable) owner);

        Fragment breedsFragment = fragManager.findFragmentByTag(BREEDS_TAG);
        fragManager.popBackStack();

        Fragment newDogFragment = fragManager.findFragmentByTag(NEW_DOG_TAG);
        if (newDogFragment == null) {
            newDogFragment = Fragment.instantiate(this, NEW_DOG_FRAGMENT, bundleArgs);
            fragManager.beginTransaction()
                    .add(R.id.host_fragment_container, newDogFragment, NEW_DOG_TAG)
                    .addToBackStack(null)
                    .commit();
        }

//        AppFragmentManager.getInstance(this)
//                .replaceFragmentWithBundle(this, NEW_DOG_FRAGMENT,
//                        NEW_DOG_TAG, bundleArgs);
    }



    // callback метода IaddOwnerFragmentListener вызывается из OwnersListFragment в menu
    @Override
    public void onAddOwnerFragmentListener() {
//        AppFragmentManager.getInstance(this)
//                .replaceAddToBackStack(this, NEW_OWNER_FRAGMENT, NEW_OWNER_TAG);

        addNewOwnerFragment();

    }


    // callback метода  вызывается из NewDogFormFragment по нажатию на породы breedEditText
    @Override
    public void onSetBreedListener(Owner owner) {
        Log.d(TAG, "Main: onSetBreedListener");
        AppFragmentManager.getInstance(this)
//                .replaceFragmentWithEntity(this, BREEDS_FRAGMENT, BREEDS_TAG, BreedsListFragment.OWNER_ARG, owner);
                .replaceBreedFragment(this, BREEDS_FRAGMENT, BREEDS_TAG, BreedsListFragment.OWNER_ARG, owner);
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
        Log.d(TAG, "Main onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void onDogFinishedListener(Owner owner) {
        AppFragmentManager.getInstance(this)
                .replaceFragmentWithEntity(this, DOGS_FRAGMENT, DOGS_ARG, OWNER_ARG, owner);
    }


    public void popAll() {
        fragManager = getSupportFragmentManager();
        fragManager.popBackStack(BACK_STACK_ROOT_TAG, fragManager.POP_BACK_STACK_INCLUSIVE);
    }

    // вызывается при клике добавить owner'a в NewOwnerFormFragment
    @Override
    public void onAddedOwnerListener() {

        Fragment newOwnerFragment = fragManager.findFragmentByTag(NEW_OWNER_TAG);
        if(newOwnerFragment != null){
            fragManager.beginTransaction()
                    .remove(newOwnerFragment)
                    .commit();
        }

        addOwnersListFragment();
    }
}




