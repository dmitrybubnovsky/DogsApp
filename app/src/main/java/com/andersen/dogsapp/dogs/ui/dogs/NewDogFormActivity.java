package com.andersen.dogsapp.dogs.ui.dogs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.dogskinds.DogsKindAdapter;
import com.andersen.dogsapp.dogs.ui.dogskinds.DogsKindsListActivity;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;


import static com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity.EXTRA_OWNER;
import static com.andersen.dogsapp.dogs.ui.dogskinds.DogsKindsListActivity.EXTRA_DOG_KIND;

public class NewDogFormActivity extends AppCompatActivity {
    public final int REQUEST_CODE_DOG_KIND = 103;

    public static final String EXTRA_NEW_OWNER = "new owner dog";
    public static final String TAG = "#";

    private EditText dogNameEditText;
    private EditText dogKindEditText;
    private EditText dogAgeEditText;
    private EditText dogTallEditText;
    private EditText dogWeightEditText;
    private TextView dogKindSelectTextview;
    private Owner owner;
    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog_form);
        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_dog);
        setSupportActionBar(toolbar);

        owner = getIntent().getParcelableExtra(EXTRA_NEW_OWNER);
        int ownerId = owner.getOwnerId();

        // TEST
//        String res = (owner == null) ? "null" : owner.getOwnerName();
//        Log.d(TAG, "NewDogActivity EXTRA_OWNER = " + res);
//        Log.d(TAG, "NewDogActivity: EXTRA_NEW_OWNER Id = " + ownerId);

        initView();
        testingFillEditText();

        String dogName = dogNameEditText.getText().toString();
        int dogAge = Integer.parseInt(dogAgeEditText.getText().toString());
        int dogTall = Integer.parseInt(dogTallEditText.getText().toString());
        int dogWeight = Integer.parseInt(dogWeightEditText.getText().toString());
        dog = new Dog(dogName, owner, owner.getOwnerId(), dogAge, dogTall, dogWeight);

        // TEST EditTexts' FILLING

//        dogKindEditText.setEnabled(false);
        dogKindSelectTextview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DogsKindsListActivity.class);
                intent.putExtra(EXTRA_DOG_KIND, dog);
                startActivityForResult(intent, REQUEST_CODE_DOG_KIND);
            }
        });

        Button addDogButton = findViewById(R.id.add_dog_button);
        addDogButton.setOnClickListener(view -> {
            addDog(this, dog);

            Intent intent = new Intent(this, DogsListActivity.class);
            intent.putExtra(EXTRA_OWNER, owner);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void addDog(Context context, Dog dog) {


        DBHelper dbHelper = DBHelper.getInstance(context);
        DogsSQLiteDataSource dogsSQLiteDataSource = DogsSQLiteDataSource.getInstance(dbHelper);
        // лезет в БД
        this.dog = DataRepository.get().addDog(dog);
//        dogsSQLiteDataSource.addDog(ownerID, dogAge, dogTall, dogWeight, dogName, dogKind);
    }

    private void initView(){
        dogNameEditText = findViewById(R.id.dog_name_edittext);
        dogKindEditText = findViewById(R.id.dog_kind_edittext);
        dogAgeEditText = findViewById(R.id.dog_age_edittext);
        dogTallEditText = findViewById(R.id.dog_tall_edittext);
        dogWeightEditText = findViewById(R.id.dog_weight_edittext);
        dogKindSelectTextview = findViewById(R.id.dog_kind_select_textview);
    }

    // TEST EditTexts' FILLING
    private void testingFillEditText() {
        dogKindEditText.setText(SomeDog.get().kind()); // !!!!!!!

        dogNameEditText.setText(SomeDog.get().name());
        dogAgeEditText.setText(""+SomeDog.get().age());
        dogWeightEditText.setText("" + 60);
        dogTallEditText.setText("" + 55);
    }
}
