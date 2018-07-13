package com.andersen.dogsapp.dogs.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andersen.dogsapp.R;

public class MainAppDescriptionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView descriptionTextView;
    private TextView appNameTextView;
    private Button startWoofBtn;
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

        setTypeFonts();
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, DogOwnersListActivity.class));
    }

    private void setTypeFonts(){
        appNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Aclonica.ttf"));
        descriptionTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Roboto_Thin.ttf"));
    }
}
