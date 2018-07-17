package com.andersen.dogsapp.dogs.activity;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.DogImageLab;
import com.andersen.dogsapp.dogs.DogToolBar;

import java.util.Random;

public class DogsInfoActivity extends AppCompatActivity {
    public static final String EXTRA_DOG_NAME = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.dogname";
    public static final String EXTRA_DOG_KIND = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.kinddog";
    MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.dog_sound);

    private String dogAges[];
    private String dogTalls[];
    private String dogWeights[];

    private String kindDog;
    private String dogName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_dogs_info);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_detail_info);
        setSupportActionBar(toolbar);

        initResources(R.array.dog_age, R.array.dog_tall, R.array.dog_weight);

        initViews();

        playSound(mediaPlayer);
    }

    private void initResources(int dogAge, int dogTall, int dogWeight) {
        Resources resources = getResources();
        dogAges = resources.getStringArray(dogAge);
        dogTalls = resources.getStringArray(dogTall);
        dogWeights = resources.getStringArray(dogWeight);
    }

    private void initViews() {
        ImageView dogsPhoto = findViewById(R.id.dog_imageview);
        dogsPhoto.setImageResource(DogImageLab.get().getRandomDogImageResource());

        getDogInfo();

        Button btnVoice = findViewById(R.id.button_voice);
        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound(mediaPlayer);

            }
        });


        TextView dogNameTextView = findViewById(R.id.dog_name_textview);
        dogNameTextView.setText(dogName);

        TextView kindDogTextView = findViewById(R.id.kind_dog_textview);
        kindDogTextView.setText(kindDog);

        Random r = new Random();

        // random data fetching for age, tall and weight
        // and set those datas to appropriate textViews
        TextView dogAgeTextView = findViewById(R.id.dog_age_textview);
        dogAgeTextView.setText(dogAges[r.nextInt(10)]);

        TextView dogTallTextView = findViewById(R.id.dog_tall_textview);
        dogTallTextView.setText(dogTalls[r.nextInt(10)]+" cm");

        TextView dogWeightTextView = findViewById(R.id.dog_weight_textview);
        dogWeightTextView.setText(dogWeights[r.nextInt(10)]+" kg");
        }

    private void getDogInfo() {
        dogName = getIntent().getStringExtra(EXTRA_DOG_NAME);
        kindDog = getIntent().getStringExtra(EXTRA_DOG_KIND);
    }

    private void playSound(MediaPlayer mediaPlayer){
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();
    }
}
