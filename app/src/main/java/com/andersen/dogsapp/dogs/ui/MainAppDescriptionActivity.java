package com.andersen.dogsapp.dogs.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IChangeFragmentListener;
import com.andersen.dogsapp.dogs.data.repositories.OwnersRepository;
import com.andersen.dogsapp.dogs.ui.breeds.BreedsListFragment;
import com.andersen.dogsapp.dogs.ui.dogs.DogPhotoPreviewFragment;
import com.andersen.dogsapp.dogs.ui.dogs.DogsInfoFragment;
import com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment;
import com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment;
import com.andersen.dogsapp.dogs.ui.owners.NewOwnerFormFragment;
import com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment;

import java.util.List;

import static com.andersen.dogsapp.dogs.ui.dogs.DogsInfoFragment.DOG_INFO_ARG;
import static com.andersen.dogsapp.dogs.ui.dogs.DogsInfoFragment.DOG_INFO_TAG;
import static com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment.DOGS_TAG;
import static com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment.OWNER_ARG;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment.BREED_ARG;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment.NEW_DOG_ARG;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment.NEW_DOG_TAG;
import static com.andersen.dogsapp.dogs.ui.owners.NewOwnerFormFragment.NEW_OWNER_TAG;
import static com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment.OWNERS_TAG;

public class MainAppDescriptionActivity extends AppCompatActivity
        implements IChangeFragmentListener,
        OwnersListFragment.IFragmentOwnerListener<Owner>,
        NewOwnerFormFragment.IAddedOwnerFragmentListener,
        DogsListFragment.IAddDogFragmentListener<Owner>,
        DogsListFragment.IAddedDogFragmentListener<Dog>,
        OwnersListFragment.IaddOwnerFragmentListener,
        NewDogFormFragment.IDogFinishedFragmentListener<Owner> {
    public static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private static final String TAG = "#";
    private static final String BREEDS_TAG = "breeds_tag";
    private static final String NEW_OWNER_FRAGMENT = NewOwnerFormFragment.class.getName();
    private static final String OWNERS_FRAGMENT = OwnersListFragment.class.getName();
    private static final String NEW_DOG_FRAGMENT = NewDogFormFragment.class.getName();
    private static final String DOGS_FRAGMENT = DogsListFragment.class.getName();
    private static final String INFO_FRAGMENT = DogsInfoFragment.class.getName();
    private static final String BACK_STACK_NEW_DOG_TAG = "root_fragment";
    public FragmentManager fragManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private List<Owner> owners;


    /*
     * TODO: вынести всю логику создающую фрагменты в AppFragmentManager
     * TODO: допилить оставшиеся пункты меню
     * TODO: FloatingActionButton
     * TODO: поосвобождать ресуры во фрагментах
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_subscription);
        Log.d(TAG, "HOST onCreate");

        fragManager = getSupportFragmentManager();

        initViews();

        AppFragmentManager.getInstance(this)
                .replaceFragmentAddToBackStack(this, OWNERS_FRAGMENT, OWNERS_TAG);

        owners = OwnersRepository.get().getOwners();
        if (owners.isEmpty()) {
            replaceNewOwnerFragmentToBackStack();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "HOST onResume");
    }

    public void replaceNewOwnerFragmentToBackStack() {
        Fragment newOwnerFragment = fragManager.findFragmentByTag(NEW_OWNER_TAG);
        if (newOwnerFragment == null) {
            newOwnerFragment = Fragment.instantiate(this, NEW_OWNER_FRAGMENT);
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, newOwnerFragment)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
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
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return false;
                }
        );
    }

    public void setToolbarTitle(int toolbarTitle) {
        toolbar.setTitle(toolbarTitle);
    }

    private void selectDrawerItem(MenuItem menuItem) {
        AppFragmentManager.getInstance(this).clearBackStackImmediate();
        switch (menuItem.getItemId()) {
            case R.id.owners_list_fragment:
                AppFragmentManager.getInstance(this)
                        .replaceFragmentAddToBackStack(this, OWNERS_FRAGMENT, OWNERS_TAG);
                break;
            case R.id.dogs_list_fragment:
                toolbar.setTitle(R.string.title_dogs_list);
                // empty implementation
                break;
            case R.id.breeds_fragment:
                toolbar.setTitle(R.string.title_breeds);
//              // empty implementation
                break;
            default:
                // empty implementation
                break;
        }

        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
    }

    // callback метода IFragmentOwnerListener<Owner>
    // вызывается из OwnersListFragment клик по списку
    @Override
    public void onFragmentOwnerListener(Owner owner) {
        startDogsListFragment(owner);
    }

    private <T> Bundle getParcelableBundle(String keyArg, T t) {
        Bundle bundleArg = new Bundle();
        bundleArg.putParcelable(keyArg, (Parcelable) t);
        return bundleArg;
    }

    private void startDogsListFragment(Owner owner) {
        Bundle bundleArgs = new Bundle();
        bundleArgs.putParcelable(OWNER_ARG, (Parcelable) owner);

        Fragment dogsListFragment = fragManager.findFragmentByTag(DOGS_TAG);
        if (dogsListFragment == null) {
            dogsListFragment = Fragment.instantiate(this, DOGS_FRAGMENT, bundleArgs);
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, dogsListFragment, DOGS_TAG)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }
    }

    private void startNewDogFragment(Owner owner) {
        Bundle bundleArgs = new Bundle();
        bundleArgs.putParcelable(NEW_DOG_ARG, (Parcelable) owner);

        Fragment newDogFragment = fragManager.findFragmentByTag(NEW_DOG_TAG);
        if (newDogFragment == null) {
            newDogFragment = Fragment.instantiate(this, NEW_DOG_FRAGMENT, bundleArgs);
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, newDogFragment, NEW_DOG_TAG)
                    .addToBackStack(BACK_STACK_NEW_DOG_TAG) // BACK_STACK_ROOT_TAG
                    .commit();
        } else {
            Log.d(TAG, "NEW_DOG_TAG != null");
        }
    }

    // callback метода of IAddDogFragmentListener<Dog> вызывается из DogsListFragment в menu
    // start NewDogFormFragment(owner)
    @Override
    public void onAddDogFragmentListener(Owner owner) {
        startNewDogFragment(owner);
    }

    // callback метода IaddOwnerFragmentListener вызывается из OwnersListFragment в menu
    @Override
    public void onAddOwnerFragmentListener() {
        replaceNewOwnerFragmentToBackStack();
    }

    public void startBreedsFromTargetFragment(Fragment frag) {
        fragManager.beginTransaction()
                .add(R.id.host_fragment_container, frag, BREEDS_TAG)
                .commit();
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
        fragManager.popBackStack();
        startDogsListFragment(owner);
    }

    // вызывается при клике добавить owner'a в NewOwnerFormFragment
    @Override
    public void onAddedOwnerListener() {
        deleteFragmentByTag(NEW_OWNER_TAG);
        fragManager.popBackStack();
    }

    public void deleteFragmentByTag(String fragmentTag) {
        Fragment newOwnerFragment = fragManager.findFragmentByTag(fragmentTag);
        if (newOwnerFragment != null) {
            fragManager.beginTransaction()
                    .remove(newOwnerFragment)
                    .commit();
        }
    }

    public void deleteFragment(Fragment fragment) {
        if (fragment != null) {
            fragManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    // callback метода of IAddedDogFragmentListener<Dog> вызывается из DogsListFragment
    // по клику элемента списка, запускает DogsInfoFragment
    @Override
    public void onAddedDogFragmentListener(Dog dog) {
        startDogsInfoFragment(dog);
    }

    private void startDogsInfoFragment(Dog dog) {
        Bundle bundleArgs = new Bundle();
        bundleArgs.putParcelable(DOG_INFO_ARG, (Parcelable) dog);

        Fragment dogsInfoFragment = fragManager.findFragmentByTag(DOG_INFO_TAG);
        if (dogsInfoFragment == null) {
            dogsInfoFragment = Fragment.instantiate(this, INFO_FRAGMENT, bundleArgs);
            fragManager.beginTransaction()
                    .replace(R.id.host_fragment_container, dogsInfoFragment, DOG_INFO_TAG)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFragmentChangeListener(int toolbarTitle) {
        setToolbarTitle(toolbarTitle);
    }
}




