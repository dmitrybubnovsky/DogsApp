package com.andersen.dogsapp.dogs.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.andersen.dogsapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnStartActDogs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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