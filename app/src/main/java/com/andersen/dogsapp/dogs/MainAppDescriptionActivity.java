package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        setContentView(R.layout.activity_act_main_dog_subscription);

        startWoofBtn = findViewById(R.id.btn_woof);
        startWoofBtn.setOnClickListener(this);
        descriptionTextView = findViewById(R.id.textViewAppDescription);
        descriptionTextView.setOnClickListener(this);
        appNameTextView = findViewById(R.id.textViewAppName);
        appNameTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, DogOwnersListActivity.class));
    }
}
