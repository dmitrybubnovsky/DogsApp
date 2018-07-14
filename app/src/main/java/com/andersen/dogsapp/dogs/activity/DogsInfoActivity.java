package com.andersen.dogsapp.dogs.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.DataRepository;
import com.andersen.dogsapp.dogs.DogImageLab;
import com.andersen.dogsapp.dogs.DogToolBar;

public class DogsInfoActivity extends AppCompatActivity {
    public static final String EXTRA_DOG_ID = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.dogid";

    private int dogId;
    private int dogAges;
    private int dogTalls;
    private int dogWeights;

    private String dogKind;
    private String dogName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dogs_info);

        dogId = getIntent().getIntExtra(EXTRA_DOG_ID,0);

        DataRepository dataRepository = DataRepository.get(this);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_detail_info);
        setSupportActionBar(toolbar);

        initResources(dataRepository);

        initViews();
    }

    private void initResources(DataRepository dataRepository) {
        dogName = dataRepository.getDogById(dogId).getDogName();
        dogKind = dataRepository.getDogById(dogId).getDogKind();
        dogAges = dataRepository.getDogById(dogId).getDogAge();
        dogTalls = dataRepository.getDogById(dogId).getDogTall();
        dogWeights = dataRepository.getDogById(dogId).getDogWeight();
    }

    private void initViews() {

        //  TODO add image resource in dog.json
        ImageView dogsPhoto = findViewById(R.id.dog_imageview);
        dogsPhoto.setImageResource(DogImageLab.get(this).getRandomDogImageResource());

        TextView dogNameTextView = findViewById(R.id.dog_name_textview);
        dogNameTextView.setText(dogName);

        TextView kindDogTextView = findViewById(R.id.kind_dog_textview);
        kindDogTextView.setText(dogKind);

        //  TODO add method that converts months to year and months
        TextView dogAgeTextView = findViewById(R.id.dog_age_textview);
        dogAgeTextView.setText(""+dogAges);

        //  TODO add resources cm in string resources
        TextView dogTallTextView = findViewById(R.id.dog_tall_textview);
        dogTallTextView.setText(""+dogTalls+" cm");

        //  TODO add resources kg in string resources
        TextView dogWeightTextView = findViewById(R.id.dog_weight_textview);
        dogWeightTextView.setText(""+dogWeights+" kg");
        }
}
