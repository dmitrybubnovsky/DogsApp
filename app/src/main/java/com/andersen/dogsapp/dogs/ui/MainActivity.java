package com.andersen.dogsapp.dogs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import com.andersen.dogsapp.R;

public class MainActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStartActDogs = findViewById(R.id.start_app_button);
        btnStartActDogs.setOnClickListener(view -> startActivity(new Intent(this, MainAppDescriptionActivity.class)));
    }
}