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
import android.view.MenuItem;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment;

public class MainAppDescriptionActivity extends AppCompatActivity {
    private static final String TAG = "#";
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_subscription);

        initViews();
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
        // !!! Убедитесь, что вы передаёте допустимую ссылку на toolbar
        // ActionBarDrawToggle() не предусматривает в ней необходимости
        // и не будет отображать иконку гамбургера без неё
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
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.owners_list_fragment:
                fragmentClass = OwnersListFragment.class;
                break;
            case R.id.dogs_list_fragment:
//                fragmentClass = DogsListFragment.class;
                break;
            case R.id.breeds_fragment:
//                fragmentClass = BreedsListFragment.class;
                break;
            default:
                fragmentClass = OwnersListFragment.class;
        }

        try {
            fragment = (Fragment) (fragmentClass != null ? fragmentClass.newInstance() : null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_framelayout, fragment)
                .commit();

        menuItem.setChecked(true);
        // Установить заголовок для action bar'а
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
    }
}
