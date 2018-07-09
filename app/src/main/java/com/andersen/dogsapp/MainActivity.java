package com.andersen.dogsapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andersen.dogsapp.dogs.MainAppDescriptionActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnStartActDogs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartActDogs = findViewById(R.id.btnStartActDogs);
        btnStartActDogs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartActDogs:
                startActivity(new Intent(this, MainAppDescriptionActivity.class));
                break;
            default:
                break;
        }
    }
}