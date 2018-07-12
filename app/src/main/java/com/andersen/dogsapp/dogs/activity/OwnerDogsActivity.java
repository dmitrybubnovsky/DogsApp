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

    private LinearLayout dogsLinearLayout;
    private String dogsKinds[];
    private String dogsNames[];
    private String ownerName;

    private TextView dogKindTextview;
    private TextView dogNameTextview;

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

        // init dogsNames[] and dogsKinds[] from Resources
        initResources(R.array.dog_name, R.array.dogs_kinds);

        ownerName = getIntent().getStringExtra(EXTRA_OWNER_NAME);
        int dogsQuantity = getIntent().getIntExtra(EXTRA_DOGS_QUANTITY, 0);

        dogsLinearLayout = findViewById(R.id.dogs_container);

        AppTextView.newInstance(this, R.id.owner_name_textview)
                .text(ownerName)
                .build();

        Random random = new Random();
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < dogsQuantity; i++) {
            View itemView = initItemView(layoutInflater, random);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick();
                }
            });
            dogsLinearLayout.addView(itemView);
        }

    }

    private View initItemView(LayoutInflater layoutInflater, Random random) {
        View itemView = layoutInflater.inflate(R.layout.dog_item, dogsLinearLayout, false);

        // initialize appropriate textview inside inflatedView
        String kindOfDogElem = dogsKinds[random.nextInt(10)];
        dogKindTextview = AppTextView.newInstance(itemView, R.id.dog_kind_textview)
                .text("" + kindOfDogElem)
                .build();

        String dogNameElem = dogsNames[random.nextInt(10)];
        dogNameTextview = AppTextView.newInstance(itemView, R.id.dog_name_textview)
                .text("" + dogNameElem)
                .style(this, R.style.TextViewSubTitle)
                .build();
        return itemView;
    }

    private void initResources(int dogNameArrayRes, int dogKindsArrayRes) {
        Resources resources = getResources();
        dogsNames = resources.getStringArray(dogNameArrayRes);
        dogsKinds = resources.getStringArray(dogKindsArrayRes);
    }

    private void onItemClick() {
        Intent i = new Intent(getApplicationContext(), DogsInfoActivity.class);
        i.putExtra(DogsInfoActivity.EXTRA_DOG_KIND, dogKindTextview.getText().toString());
        i.putExtra(DogsInfoActivity.EXTRA_DOG_NAME, dogNameTextview.getText().toString());
        startActivity(i);
    }
}
