package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import java.util.Random;

public class OwnerDogsActivity extends AppCompatActivity {
    public static final String EXTRA_OWNER_NAME = "com.andersen.dogsapp.dogs.OwnerDogsActivity.owner_name";
    public static final String EXTRA_DOGS_QUANTITY = "com.andersen.dogsapp.dogs.OwnerDogsActivity.quantity";

    private LayoutInflater layoutInflater;
    private LinearLayout dogsLinearLayout;
    private View inflatedView;

    private TextView ownerNameTextView;
    private TextView textViewKindDog;
    private TextView textViewDogName;

    private Toolbar toolbar;

    private String kindOfDogs[];
    private String dogNames[];
    private String ownerName;

    private int dogsQuantity;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);

        toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        // init dogNames[] and kindOfDogs[] from Resources
        initResources(R.array.dog_name, R.array.kind_of_dogs);

        ownerName = getIntent().getStringExtra(EXTRA_OWNER_NAME);
        dogsQuantity = getIntent().getIntExtra(EXTRA_DOGS_QUANTITY, 0);

        dogsLinearLayout = findViewById(R.id.dogs_container);


        ownerNameTextView = AppTextView.newInstance(this, R.id.owner_name_textview)
                .text(ownerName)
                .build();

        layoutInflater = getLayoutInflater();
        Random r = new Random();
        for(int i=0; i<dogsQuantity; i++){
            inflatedView = layoutInflater.inflate(R.layout.dog_item, dogsLinearLayout,  false);
            String kindOfDogElem = kindOfDogs[r.nextInt(10)];
            String dogNameElem = dogNames[r.nextInt(10)];

            // initialize appropriate textview inside inflatedView
            textViewKindDog = AppTextView.newInstance(inflatedView, R.id.kind_of_dog_textview)
                    .text(""+kindOfDogElem)
                    .build();
            textViewDogName = AppTextView.newInstance(inflatedView, R.id.dog_name_textview)
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

    protected void initResources(int dogNameArray, int kindDogArray){
        Resources resources = getResources();
        dogNames = resources.getStringArray(dogNameArray);
        kindOfDogs = resources.getStringArray(kindDogArray);
    }
}
