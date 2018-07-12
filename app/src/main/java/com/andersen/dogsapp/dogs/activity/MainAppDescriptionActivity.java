package com.andersen.dogsapp.dogs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andersen.dogsapp.R;

public class MainAppDescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    TextView descriptionTextView;
    TextView appNameTextView;
    Button startWoofBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_subscription);

        startWoofBtn = findViewById(R.id.woof_button);
        startWoofBtn.setOnClickListener(this);

        descriptionTextView = findViewById(R.id.app_description_textview);
        descriptionTextView.setOnClickListener(this);

        appNameTextView = findViewById(R.id.app_name_textview);
        appNameTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, DogOwnersListActivity.class));
    }
}