package com.andersen.dogsapp.dogs.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DogKindsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.DogKind;

public class TempActivity extends AppCompatActivity {
    public static final String TAG = "#";
    private EditText breedNameEditText;
    private EditText uriStringEditText;
    private ImageView breedImageView;
    private Button addBreedButton;
    private Button updateBreedButton;
    DBHelper dbHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_layout_form);
        initViews();

         dbHelper = DBHelper.getInstance(this);

        // just for test
        testingFillEditText();

        // ADD BREED IN DATABASE
        addBreedButton.setOnClickListener(view -> {
            DogKind dogKind = addDogKind();
            Log.d(TAG, "dogKindInstance "+ dogKind.getKind() + " imageString " + dogKind.getUriImageString() + " dogId "+ dogKind.getId());
        });

        updateBreedButton.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"UPDATE",Toast.LENGTH_SHORT).show();
            Log.d(TAG, "update");
        });

        // UPDATE LONG CLICK
        updateBreedButton.setOnLongClickListener(view -> {
            if(uriStringEditText.getText().toString().length() == 0){
                uriStringEditText.setText("https://images.dog.ceo/breeds/akita/Akita_Dog.jpg");
            } else {
                uriStringEditText.setText("");
            }
            return true;
        });
    }

    private void initViews() {
        breedNameEditText = findViewById(R.id.breed_name_edittext);
        uriStringEditText = findViewById(R.id.uri_string_edittext);
        breedImageView = findViewById(R.id.breed_photo_imageview);
        addBreedButton = findViewById(R.id.add_breed_button);
        updateBreedButton = findViewById(R.id.update_breed_button);
    }

    private DogKind addDogKind (){
        DogKind dogKind = new DogKind();
        String dogKindString = breedNameEditText.getText().toString();
        if (dogKindString != ""){
            dogKind.setKind(dogKindString);
        } else {
            Log.d(TAG, "dogKindString is empty ");
        }
        dogKind = DogKindsSQLiteDataSource.getInstance().addDogKind(dogKind);
        return dogKind;
    }

    private void testingFillEditText() {
        breedNameEditText.setText("akita");
//        preferredKindEditText.setText(SomeDog.get().kind());
    }

}
