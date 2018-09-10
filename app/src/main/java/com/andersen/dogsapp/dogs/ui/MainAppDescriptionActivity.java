package com.andersen.dogsapp.dogs.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppFragmentManager;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IChangeFragmentListener;
import com.andersen.dogsapp.dogs.data.repositories.OwnersRepository;
import com.andersen.dogsapp.dogs.ui.breeds.BreedsListFragment;
import com.andersen.dogsapp.dogs.ui.dogs.DogsInfoFragment;
import com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment;
import com.andersen.dogsapp.dogs.ui.dogs.NewDogFormFragment;
import com.andersen.dogsapp.dogs.ui.owners.NewOwnerFormFragment;
import com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment;

import java.util.List;

import static com.andersen.dogsapp.dogs.ui.breeds.BreedsListFragment.CALLED_FOR_SELECT_KEY;
import static com.andersen.dogsapp.dogs.ui.dogs.DogsInfoFragment.DOG_INFO_ARG;
import static com.andersen.dogsapp.dogs.ui.dogs.DogsInfoFragment.DOG_INFO_TAG;
import static com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment.DOGS_TAG;
import static com.andersen.dogsapp.dogs.ui.dogs.DogsListFragment.OWNER_ARG;
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
    private static final int DELAY_MILLIS = 5;
    public static final String BREEDS_TAG = "breeds_tag";
    private static final String NEW_OWNER_FRAGMENT = NewOwnerFormFragment.class.getName();
    private static final String OWNERS_FRAGMENT = OwnersListFragment.class.getName();
    private static final String NEW_DOG_FRAGMENT = NewDogFormFragment.class.getName();
    private static final String BREEDS_FRAGMENT = BreedsListFragment.class.getName();
    private static final String DOGS_FRAGMENT = DogsListFragment.class.getName();
    private static final String INFO_FRAGMENT = DogsInfoFragment.class.getName();
    private static final String DOG_CHANNEL_ID = "DOG_CHANNEL";
    public FragmentManager fragManager;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private List<Owner> owners;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_subscription);

        fragManager = getSupportFragmentManager();

        initViews();
        AppFragmentManager.getInstance()
                .replaceAddToBackStack(fragManager, this, OWNERS_FRAGMENT, OWNERS_TAG);
        owners = OwnersRepository.get().getOwners();
        if (owners.isEmpty()) {
            AppFragmentManager.getInstance()
                    .replaceAddToBackStack(fragManager, this, NEW_OWNER_FRAGMENT,
                            NEW_OWNER_TAG);
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
        AppFragmentManager.getInstance().clearBackStack(fragManager);
        Handler handler = new Handler();
        switch (menuItem.getItemId()) {
            case R.id.owners_list_fragment:
                handler.postDelayed(() -> AppFragmentManager.getInstance()
                        .replaceAddToBackStack(fragManager, getApplicationContext(), OWNERS_FRAGMENT,
                                OWNERS_TAG), DELAY_MILLIS);
                break;
            case R.id.dogs_list_fragment:
                toolbar.setTitle(R.string.title_dogs_list);
                // empty implementation
                break;
            case R.id.breeds_fragment:
                toolbar.setTitle(R.string.title_breeds);
                handler.postDelayed(this::startBreedsFromDrawer, DELAY_MILLIS);
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
        AppFragmentManager.getInstance().replaceAddToBackStack(fragManager, this, DOGS_FRAGMENT,
                DOGS_TAG, getParcelableBundle(OWNER_ARG, owner));
    }

    // callback метода of IAddDogFragmentListener<Dog> вызывается из DogsListFragment в menu
    // start NewDogFormFragment(owner)
    @Override
    public void onAddDogFragmentListener(Owner owner) {
        AppFragmentManager.getInstance()
                .replaceAddToBackStack(fragManager, this, NEW_DOG_FRAGMENT, NEW_DOG_TAG,
                        getParcelableBundle(NEW_DOG_ARG, owner));
    }

    // callback метода IaddOwnerFragmentListener вызывается из OwnersListFragment в menu
    @Override
    public void onAddOwnerFragmentListener() {
        AppFragmentManager.getInstance()
                .replaceAddToBackStack(fragManager, this, NEW_OWNER_FRAGMENT, NEW_OWNER_TAG);
    }

    @Override
    public void onBackPressed() {
        if (fragManager.getBackStackEntryCount() == 1) {
            Toast.makeText(this, "Chao-chao!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    // вызывается при нажатии на кнопку добавить (собачку) в NewDogFormFragment
    @Override
    public void onDogFinishedListener(Owner owner) {
        fragManager.popBackStack();
        AppFragmentManager.getInstance().replaceAddToBackStack(fragManager, this, DOGS_FRAGMENT,
                DOGS_TAG, getParcelableBundle(OWNER_ARG, owner));
    }

    // вызывается при клике по кнопке добавить owner'a в NewOwnerFormFragment
    @Override
    public void onAddedOwnerListener() {
        AppFragmentManager.getInstance().deleteFragmentByTag(fragManager, NEW_OWNER_TAG);
        fragManager.popBackStack();
        notifyOwnerAdded();
    }

    // callback метода of IAddedDogFragmentListener<Dog> вызывается из DogsListFragment
    // по клику элемента списка, запускает DogsInfoFragment
    @Override
    public void onAddedDogFragmentListener(Dog dog) {
        AppFragmentManager.getInstance().replaceAddToBackStack(fragManager, this, INFO_FRAGMENT,
                DOG_INFO_TAG, getParcelableBundle(DOG_INFO_ARG, dog));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private <T> Bundle getParcelableBundle(String keyArg, T t) {
        Bundle bundleArg = new Bundle();
        bundleArg.putParcelable(keyArg, (Parcelable) t);
        return bundleArg;
    }

    @Override
    public void onFragmentChangeListener(int toolbarTitle) {
        setToolbarTitle(toolbarTitle);
    }

    private void startBreedsFromDrawer() {
        Bundle bundleArg = new Bundle();
        bundleArg.putBoolean(CALLED_FOR_SELECT_KEY, false);
        AppFragmentManager.getInstance()
                .replaceAddToBackStack(fragManager, getApplicationContext(), BREEDS_FRAGMENT,
                        BREEDS_TAG, bundleArg);
    }

    private void notifyOwnerAdded() {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.done)
                        .setContentTitle(getResources().getString(R.string.note_owner_added))
                        .setContentText("Woof! Woof!");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(createDogChannel());
            builder.setChannelId(DOG_CHANNEL_ID);
        }
        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private NotificationChannel createDogChannel() {
        NotificationChannel channel = new NotificationChannel(DOG_CHANNEL_ID,
                "Doggy dogg channel", NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription("Dog Notification's Channel");
        channel.enableLights(true);
        channel.setLightColor(R.color.colorCustomDarkL);
        channel.enableVibration(false);
        return channel;
    }
}




