package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andersen.dogsapp.R;

import java.util.Random;

public class DogsInfoActivity extends AppCompatActivity {
    public static final String EXTRA_DOG_NAME = "com.andersen.dogsapp.dogs.OwnerDogsActivity.dogname";
    public static final String EXTRA_KIND_DOG = "com.andersen.dogsapp.dogs.OwnerDogsActivity.kinddog";

    private TextView dogNameTextView;
    private TextView kindDogTextView;
    private TextView dogAgeTextView;
    private TextView dogTallTextView;
    private TextView dogWeightTextView;
    private Toolbar toolbar;
    private ImageView dogsPhoto;

    private String dogAges[];
    private String dogTalls[];
    private String dogWeights[];

    private String kindDog;
    private String dogName;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dogs_info);

        toolbar = DogToolBar.init(this, R.string.toolbar_title_detail_info);

        setSupportActionBar(toolbar);

        initResources(R.array.dogage, R.array.dogtall, R.array.dogweight);
        intent = getIntent();
        initViews(intent);
    }

    private void initResources (int dogage, int doagtall, int dogweight){
        Resources resources = getResources();
        dogAges = resources.getStringArray(dogage);
        dogTalls = resources.getStringArray(doagtall);
        dogWeights = resources.getStringArray(dogweight);
    }

    private void initViews(Intent intent){
        dogsPhoto = findViewById(R.id.dog_imageview);
        dogsPhoto.setImageResource(DogImageLab.get(this).getRandomDogImageResource());

        getDogInfo(intent);

        dogNameTextView = findViewById(R.id.dog_name_textview);
        dogNameTextView.setText(dogName);

        kindDogTextView = findViewById(R.id.kind_dog_textview);
        kindDogTextView.setText(kindDog);

        Random r = new Random();

        // random data fetching for age, tall and weight
        // and set those datas to appropriate textViews
        dogAgeTextView = findViewById(R.id.dog_age_textview);
        dogAgeTextView.setText(dogAges[r.nextInt(10)]);

        dogTallTextView = findViewById(R.id.dog_tall_textview);
        dogTallTextView.setText(dogTalls[r.nextInt(10)]+" cm");

        dogWeightTextView = findViewById(R.id.dog_weight_textview);
        dogWeightTextView.setText(dogWeights[r.nextInt(10)]+" kg");
        }

    private void getDogInfo(Intent intent){
        dogName = intent.getStringExtra(EXTRA_DOG_NAME);
        kindDog = intent.getStringExtra(EXTRA_KIND_DOG);
    }
}
