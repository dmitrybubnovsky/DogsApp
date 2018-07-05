package com.andersen.dogsapp.dogs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andersen.dogsapp.R;

import java.util.Random;

public class ActDogsInfo extends AppCompatActivity {
    public static final String EXTRA_DOG_NAME = "com.andersen.dogsapp.dogs.ActOwnersDog.dogname";
    public static final String EXTRA_KIND_DOG = "com.andersen.dogsapp.dogs.ActOwnersDog.kinddog";
    public String kindOfDog;
    public String dogName;
    public String dogAges[];
    public String dogTalls[];
    public String dogWeights[];
    TextView textViewDogName;
    TextView textViewKindDog;
    TextView textViewDogAge;
    TextView textViewDogTall;
    TextView textViewDogWeight;
    ImageView imageViewDog;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dogs_info);

        toolbar = findViewById(R.id.toolbar_dogs_app);
        toolbar.setTitle(R.string.toolbar_title_detail_info);
        setSupportActionBar(toolbar);

        Random r = new Random();
        dogName = getIntent().getStringExtra(EXTRA_DOG_NAME);
        kindOfDog = getIntent().getStringExtra(EXTRA_KIND_DOG);
        dogAges = getResources().getStringArray(R.array.dogage);
        dogTalls = getResources().getStringArray(R.array.dogtall);
        dogWeights = getResources().getStringArray(R.array.dogweight);

        textViewDogName = findViewById(R.id.text_view_dog_name);
        textViewKindDog = findViewById(R.id.text_view_kind_dog);
        textViewDogAge = findViewById(R.id.text_view_dog_age);
        textViewDogTall = findViewById(R.id.text_view_dog_tall);
        textViewDogWeight = findViewById(R.id.text_view_dog_weight);

        textViewDogName.setText(dogName);
        textViewKindDog.setText(kindOfDog);
        // random data fetching for age, tall and weight
        // and set those datas to appropriate textViews
        textViewDogAge.setText(dogAges[r.nextInt(10)]);
        textViewDogTall.setText(dogTalls[r.nextInt(10)]+" cm");
        textViewDogWeight.setText(dogWeights[r.nextInt(10)]+" kg");


    }
}
