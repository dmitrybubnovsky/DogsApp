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
        setContentView(R.layout.main_activity);

        btnStartActDogs = findViewById(R.id.start_app_button);
        btnStartActDogs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_app_button:
                startActivity(new Intent(this, MainAppDescriptionActivity.class));
                break;
            default:
                break;
        }
    }
}