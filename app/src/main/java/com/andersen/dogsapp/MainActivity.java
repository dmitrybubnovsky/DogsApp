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
    private Button btnActTwo;
    private Button btnActThree;
    private Button btnActFour;
    private Button btnActFive;
    private Button btnActSix;
    private Button btnStartActDogs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnActTwo = findViewById(R.id.btnActTwo);
        btnActTwo.setOnClickListener(this);

        btnActThree = findViewById(R.id.btnActThree);
        btnActThree.setOnClickListener(this);

        btnActFour = findViewById(R.id.btnActFour);
        btnActFour.setOnClickListener(this);

        btnActFive = findViewById(R.id.btnActFive);
        btnActFive.setOnClickListener(this);

        btnActSix = findViewById(R.id.btnActSix);
        btnActSix.setOnClickListener(this);

        btnStartActDogs = findViewById(R.id.btnStartActDogs);
        btnStartActDogs.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnActTwo:
                intent = new Intent(this, ActivityTwo.class);
                startActivity(intent);
                break;
            case R.id.btnActThree:
                startActivity(new Intent(this, ActivityThree.class));
                break;
            case R.id.btnActFour:
                startActivity(new Intent(this, ActTextViewInFrameLayout.class));
                break;
            case R.id.btnActFive:
                startActivity(new Intent(this, ActTextViewInRelativeLayout.class));
                break;
            case R.id.btnActSix:
                startActivity(new Intent(this, ActTextViewInConstLayout.class));
                break;
            case R.id.btnStartActDogs:
                startActivity(new Intent(this, ActMainDogSubscription.class));
                break;
            default:
                break;
        }
    }
}