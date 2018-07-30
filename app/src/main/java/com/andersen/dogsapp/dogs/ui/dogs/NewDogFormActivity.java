package com.andersen.dogsapp.dogs.ui.dogs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import android.support.v7.widget.Toolbar;

import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;


import static com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity.EXTRA_OWNER;

public class NewDogFormActivity extends AppCompatActivity {
    public static final String EXTRA_NEW_OWNER = "new owner dog";
    public static final String TAG = "#";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog_form);
        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_dog);
        setSupportActionBar(toolbar);

        Owner owner = getIntent().getParcelableExtra(EXTRA_NEW_OWNER);
        String res = (owner == null) ? "null" : owner.getOwnerName();
        Log.d(TAG, "NewDogActivity EXTRA_OWNER = " + res);

        int ownerId = owner.getOwnerId();
        Log.d(TAG, "NewDogActivity: EXTRA_NEW_OWNER Id = " + ownerId);

        EditText dogNameEditText = findViewById(R.id.dog_name_edittext);
        EditText dogKindEditText = findViewById(R.id.dog_kind_edittext);
        EditText dogAgeEditText = findViewById(R.id.dog_age_edittext);
        EditText dogTallEditText = findViewById(R.id.dog_tall_edittext);
        EditText dogWeightEditText = findViewById(R.id.dog_weight_edittext);

        // TEST EditTexts' FILLING
        testingFillEditText(dogNameEditText, dogKindEditText, dogAgeEditText, dogTallEditText,
                dogWeightEditText);

        Button addDogButton = findViewById(R.id.add_dog_button);
        addDogButton.setOnClickListener(view -> {
            addDog(this, ownerId, dogNameEditText, dogKindEditText, dogAgeEditText,
                    dogTallEditText, dogWeightEditText);
            Intent intent = new Intent(this, DogsListActivity.class);
            intent.putExtra(EXTRA_OWNER, owner);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void addDog(Context context, int ownerID, EditText dogNameEditText,
                        EditText dogKindEditText, EditText dogAgeEditText, EditText dogTallEditText,
                        EditText dogWeightEditText) {
        String dogName = dogNameEditText.getText().toString();
        String dogKind = dogKindEditText.getText().toString();
        int dogAge = Integer.parseInt(dogAgeEditText.getText().toString());
        int dogTall = Integer.parseInt(dogTallEditText.getText().toString());
        int dogWeight = Integer.parseInt(dogWeightEditText.getText().toString());

        // change it to DataRepository method addOwner
        DBHelper dbHelper = DBHelper.getInstance(context);
        DogsSQLiteDataSource dogsSQLiteDataSource = DogsSQLiteDataSource.getInstance(dbHelper);
        // лезет в БД
        dogsSQLiteDataSource.addDog(ownerID, dogAge, dogTall, dogWeight, dogName, dogKind);
    }

    // TEST EditTexts' FILLING
    private void testingFillEditText(EditText dogNameEditText, EditText dogKindEditText,
                                     EditText dogAgeEditText, EditText dogTallEditText,
                                     EditText dogWeightEditText) {
        dogNameEditText.setText(SomeDog.get().name());
        dogKindEditText.setText(SomeDog.get().kind());
        dogAgeEditText.setText("" + 11);
        dogWeightEditText.setText("" + 60);
        dogTallEditText.setText("" + 55);
    }
}
