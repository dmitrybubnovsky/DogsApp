package com.andersen.dogsapp.dogs.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.DogToolBar;

import java.util.Random;

public class OwnerDogsActivity extends AppCompatActivity {
    public static final String EXTRA_OWNER_NAME = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner_name";
    public static final String EXTRA_DOGS_QUANTITY = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.quantity";

    private String dogs_Kinds[];
    private String dogNames[];

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        // init dogNames[] and dogs_Kinds[] from Resources
        initResources(R.array.dog_name, R.array.dogs_kinds);

        String ownerName = getIntent().getStringExtra(EXTRA_OWNER_NAME);
        int dogsQuantity = getIntent().getIntExtra(EXTRA_DOGS_QUANTITY, 0);

        LinearLayout dogsLinearLayout = findViewById(R.id.dogs_container);

        AppTextView.newInstance(this, R.id.owner_name_textview)
                .text(ownerName)
                .build();

        LayoutInflater layoutInflater = getLayoutInflater();
        Random r = new Random();
        for (int i = 0; i < dogsQuantity; i++) {
            View inflatedView = layoutInflater.inflate(R.layout.dog_item, dogsLinearLayout, false);
            String kindOfDogElem = dogs_Kinds[r.nextInt(10)];
            String dogNameElem = dogNames[r.nextInt(10)];

            // initialize appropriate textview inside inflatedView
            AppTextView.newInstance(inflatedView, R.id.kind_of_dog_textview)
                    .text(""+kindOfDogElem)
                    .build();
            AppTextView.newInstance(inflatedView, R.id.dog_name_textview)
                    .text(""+dogNameElem)
                    .style(this,R.style.TextViewSubTitle)
                    .build();

            dogsLinearLayout.addView(inflatedView);

            inflatedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent (getApplicationContext(), DogsInfoActivity.class);
                    i.putExtra(DogsInfoActivity.EXTRA_KIND_DOG, ((TextView)view.findViewById(R.id.kind_of_dog_textview)).getText().toString());
                    i.putExtra(DogsInfoActivity.EXTRA_DOG_NAME, ((TextView)view.findViewById(R.id.dog_name_textview)).getText().toString());
                    startActivity(i);
                }
            });
        }
    }

    protected void initResources(int dogNameArrayRes, int dogKindsArrayRes) {
        Resources resources = getResources();
        dogNames = resources.getStringArray(dogNameArrayRes);
        dogs_Kinds = resources.getStringArray(dogKindsArrayRes);
    }
}
