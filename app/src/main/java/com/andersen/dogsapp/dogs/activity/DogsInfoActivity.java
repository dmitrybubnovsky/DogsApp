package com.andersen.dogsapp.dogs.activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.DataRepository;
import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.DogImageLab;
import com.andersen.dogsapp.dogs.DogToolBar;
import com.andersen.dogsapp.dogs.Owner;

public class DogsInfoActivity extends AppCompatActivity {
    public static final String EXTRA_DOG_ID = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.dogid";
    public static final String EXTRA_DOG = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.dog";

    Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dogs_info);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_detail_info);
        setSupportActionBar(toolbar);

        dog = getIntent().getParcelableExtra(EXTRA_DOG);
        initViews(dog);
    }

    private void initViews(Dog dog) {
        ImageView dogsPhoto = findViewById(R.id.dog_imageview);
        dogsPhoto.setImageResource(DogImageLab.get().getRandomDogImageResource());

        TextView dogNameTextView = findViewById(R.id.dog_name_textview);
        dogNameTextView.setText(dog.getDogName());

        TextView kindDogTextView = findViewById(R.id.kind_dog_textview);
        kindDogTextView.setText(dog.getDogName());

        TextView dogAgeTextView = findViewById(R.id.dog_age_textview);
        dogAgeTextView.setText(""+ dog.getDogAge() + " "+ getResources().getString(R.string.age_months_measure));

        TextView dogTallTextView = findViewById(R.id.dog_tall_textview);
        dogTallTextView.setText(""+ dog.getDogTall() + " " + getResources().getString(R.string.tall_measure));

        TextView dogWeightTextView = findViewById(R.id.dog_weight_textview);
        dogWeightTextView.setText(""+ dog.getDogWeight() + " " + getResources().getString(R.string.weight_measure));
        }
}
