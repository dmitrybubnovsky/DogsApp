package com.andersen.dogsapp.dogs;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.andersen.dogsapp.R;
import java.util.Random;
public class ActDogsInfo extends AppCompatActivity {
    public static final String EXTRA_DOG_NAME = "com.andersen.dogsapp.dogs.ActOwnersDog.dogname";
    public static final String EXTRA_KIND_DOG = "com.andersen.dogsapp.dogs.ActOwnersDog.kinddog";
    private String dogAges[];
    private String dogTalls[];
    private String dogWeights[];
    private String kindOfDog;
    private String dogName;
    private TextView textViewDogName;
    private TextView textViewKindDog;
    private TextView textViewDogAge;
    private TextView textViewDogTall;
    private TextView textViewDogWeight;
    private Button btnVoice;
    private Toolbar toolbar;
    private ImageView dogsPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dogs_info);

        dogsPhoto = findViewById(R.id.dogImage);

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

        dogsPhoto.setImageResource(DogImageLab.get(this).someImage());
        textViewDogName.setText(dogName);
        textViewKindDog.setText(kindOfDog);

        // random data fetching for age, tall and weight
        // and set those datas to appropriate textViews
        textViewDogAge.setText(dogAges[r.nextInt(10)]);
        textViewDogTall.setText(dogTalls[r.nextInt(10)]+" cm");
        textViewDogWeight.setText(dogWeights[r.nextInt(10)]+" kg");


    }
}
