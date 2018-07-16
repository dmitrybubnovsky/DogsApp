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
    private int dogAge;
    private int dogTall;
    private int dogWeight;

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
        dogAge = dataRepository.getDogById(dogId).getDogAge();
        dogTall = dataRepository.getDogById(dogId).getDogTall();
        dogWeight = dataRepository.getDogById(dogId).getDogWeight();
    }

    private void initViews() {
        ImageView dogsPhoto = findViewById(R.id.dog_imageview);
        dogsPhoto.setImageResource(DogImageLab.get().getRandomDogImageResource());

        TextView dogNameTextView = findViewById(R.id.dog_name_textview);
        dogNameTextView.setText(dogName);

        TextView kindDogTextView = findViewById(R.id.kind_dog_textview);
        kindDogTextView.setText(dogKind);

        TextView dogAgeTextView = findViewById(R.id.dog_age_textview);
        dogAgeTextView.setText(""+ dogAge + " "+ getResources().getString(R.string.age_months_measure));

        TextView dogTallTextView = findViewById(R.id.dog_tall_textview);
        dogTallTextView.setText(""+ dogTall + " " + getResources().getString(R.string.tall_measure));

        TextView dogWeightTextView = findViewById(R.id.dog_weight_textview);
        dogWeightTextView.setText(""+ dogWeight + " " + getResources().getString(R.string.weight_measure));
        }
}
