package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.andersen.dogsapp.R;

import java.util.Random;

public class DogsInfoActivity extends AppCompatActivity {
    public static final String EXTRA_DOG_NAME = "com.andersen.dogsapp.dogs.OwnerDogsActivity.dogname";
    public static final String EXTRA_KIND_DOG = "com.andersen.dogsapp.dogs.OwnerDogsActivity.kinddog";

    private TextView textViewDogName;
    private TextView textViewKindDog;
    private TextView textViewDogAge;
    private TextView textViewDogTall;
    private TextView textViewDogWeight;
    private Toolbar toolbar;
    private ImageView dogsPhoto;

    private String dogAges[];
    private String dogTalls[];
    private String dogWeights[];

    private String kindOfDog;
    private String dogName;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dogs_info);

        toolbar = new DogToolBar().get(this, R.string.toolbar_title_detail_info);
        setSupportActionBar(toolbar);

        // get extras and put into dogName and kindOfDog
        intent = new Intent();
        getExtrasFromIntent(intent);

        dogName = getIntent().getStringExtra(EXTRA_DOG_NAME);
        kindOfDog = getIntent().getStringExtra(EXTRA_KIND_DOG);

        initResources(R.array.dogage, R.array.dogtall, R.array.dogweight);

        dogsPhoto = findViewById(R.id.dogImage);

        findViewsByid();

        dogsPhoto.setImageResource(DogImageLab.get(this).someImage());
        setTextInTextViews();
    }

    protected void initResources (int dogage, int doagtall, int dogweight){
        Resources resources = getResources();
        dogAges = resources.getStringArray(dogage);
        dogTalls = resources.getStringArray(doagtall);
        dogWeights = resources.getStringArray(dogweight);
    }

    private void setTextInTextViews(){
        textViewDogName.setText(dogName);
        textViewKindDog.setText(kindOfDog);
        // random data fetching for age, tall and weight
        // and set those datas to appropriate textViews
        Random r = new Random();
        textViewDogAge.setText(dogAges[r.nextInt(10)]);
        textViewDogTall.setText(dogTalls[r.nextInt(10)]+" cm");
        textViewDogWeight.setText(dogWeights[r.nextInt(10)]+" kg");
    }

    private void findViewsByid(){
        textViewDogName = findViewById(R.id.text_view_dog_name);
        textViewKindDog = findViewById(R.id.text_view_kind_dog);
        textViewDogAge = findViewById(R.id.text_view_dog_age);
        textViewDogTall = findViewById(R.id.text_view_dog_tall);
        textViewDogWeight = findViewById(R.id.text_view_dog_weight);
    }

    private void getExtrasFromIntent(Intent intent){
        dogName = intent.getStringExtra(EXTRA_DOG_NAME);
        kindOfDog = intent.getStringExtra(EXTRA_KIND_DOG);
    }
}
