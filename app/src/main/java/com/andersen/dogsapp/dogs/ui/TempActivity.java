package com.andersen.dogsapp.dogs.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.andersen.dogsapp.R;

public class TempActivity extends AppCompatActivity {
    public static final String TAG = "#";
    private EditText breedName;
    private EditText uriStringEditText;
    private ImageView breedImageView;
    private Button breedButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_layout_form);
        initViews();

        // just for test
        testingFillEditText();


        breedButton.setOnClickListener(view -> {
            Log.d(TAG, "pressed");
        });
    }

    private void initViews() {
        breedName = findViewById(R.id.breed_name_edittext);
        uriStringEditText = findViewById(R.id.uri_string_edittext);
        breedImageView = findViewById(R.id.breed_photo_imageview);
        breedButton = findViewById(R.id.add_breed_button);
    }

    private void testingFillEditText() {
        breedName.setText("akita");
        uriStringEditText.setText("https://images.dog.ceo/breeds/akita/Akita_Inu_dog.jpg");
//        preferredKindEditText.setText(SomeDog.get().kind());
    }
}
