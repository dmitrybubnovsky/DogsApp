package com.andersen.dogsapp.dogs.ui;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.andersen.dogsapp.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_entity, menu);
        return super.onCreateOptionsMenu(menu);
    }
}