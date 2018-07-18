package com.andersen.dogsapp.dogs.activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.content.res.ResourcesCompat;

import com.andersen.dogsapp.R;

public class MainAppDescriptionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_subscription);

        ConstraintLayout rootLayout = findViewById(R.id.root_layout);
        rootLayout.setOnClickListener(view -> startActivity(new Intent(this, DogOwnersListActivity.class)));

        TextView descriptionTextView = findViewById(R.id.app_description_textview);
        TextView appNameTextView = findViewById(R.id.app_name_textview);

        setTypeFonts(this, descriptionTextView, appNameTextView);
    }

    private void setTypeFonts(Context context, TextView appNameTextView, TextView descriptionTextView){
        // first way through the asset folder
        appNameTextView.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/Aclonica.ttf"));
        // second way through the R.font resource
        descriptionTextView.setTypeface(ResourcesCompat.getFont(context, R.font.droid_serif_italic));
    }
}
