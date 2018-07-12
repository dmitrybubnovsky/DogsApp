package com.andersen.dogsapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.andersen.dogsapp.activities.with.textview.ActTextViewInConstLayout;
import com.andersen.dogsapp.activities.with.textview.ActTextViewInFrameLayout;
import com.andersen.dogsapp.activities.with.textview.ActTextViewInRelativeLayout;
import com.andersen.dogsapp.activities.with.textview.ActivityThree;
import com.andersen.dogsapp.activities.with.textview.ActivityTwo;
import com.andersen.dogsapp.dogs.ActMainDogSubscription;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
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
                startActivity(new Intent(this, ActMainDogSubscription.class));
                break;
            default:
                break;
        }
    }
}