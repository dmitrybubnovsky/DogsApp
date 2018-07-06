package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andersen.dogsapp.R;

public class ActMainDogSubscription extends AppCompatActivity implements View.OnClickListener {
    private TextView textViewDescription;
    private TextView textViewAppName;
    private Button button_start_woof;
    private Typeface type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_main_dog_subscription);

        button_start_woof = findViewById(R.id.btn_woof);
        button_start_woof.setOnClickListener(this);

        textViewDescription = findViewById(R.id.textViewAppDescription);
        textViewDescription.setTypeface(Typeface.DEFAULT, Typeface.ITALIC);
        textViewDescription.setOnClickListener(this);

        type = Typeface.createFromAsset(getAssets(),"Roboto_Thin.ttf");
        textViewAppName = findViewById(R.id.textViewAppName);
        textViewAppName.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);
        textViewAppName.setTypeface(type);
        textViewAppName.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, ActDogOwnersList.class));
    }
}
