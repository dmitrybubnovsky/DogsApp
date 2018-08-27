package com.andersen.dogsapp.dogs.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.ui.owners.OwnersListActivity;

public class MainAppDescriptionActivity extends AppCompatActivity {
    private static final String TAG = "#";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app_subscription);

        ConstraintLayout rootLayout = findViewById(R.id.root_layout);
        rootLayout.setOnClickListener(view -> startActivity(new Intent(this, OwnersListActivity.class)));

        TextView appNameTextView = findViewById(R.id.app_name_textview);
        TextView descriptionTextView = findViewById(R.id.app_description_textview);

        setTypeFonts(this, descriptionTextView, appNameTextView);
    }

    private void setTypeFonts(Context context, TextView appNameTextView, TextView descriptionTextView) {
        // first way through the asset folder
        appNameTextView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Aclonica.ttf"));
        // second way through the R.font resource
        descriptionTextView.setTypeface(ResourcesCompat.getFont(context, R.font.droid_serif_italic));
    }
}
