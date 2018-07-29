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

import static com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity.EXTRA_OWNER;

public class NewDogFormActivity extends AppCompatActivity {
    public static final String EXTRA_NEW_OWNER = "new owner dog";
    public static final String TAG = "#";

    public static Intent newIntent(Context context, Class<?> activityClass) {
        Intent i = new Intent(context, activityClass);
        return i;
    }

    EditText dogNameEditText;
    EditText dogKindEditText;
    EditText dogAgeEditText;
    EditText dogTallEditText;
    EditText dogWeightEditText;
    Button addDogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog_form);
//        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_dog, colorCustomBlueGrey);
//        setSupportActionBar(toolbar);

        Owner owner = getIntent().getParcelableExtra(EXTRA_NEW_OWNER);
        String res= (owner == null)?"null":owner.getOwnerName();
        Log.d(TAG, "NewDogActivity EXTRA_OWNER = "+res);

        int ownerId = owner.getOwnerId();
        Log.d(TAG, "NewDogActivity: EXTRA_NEW_OWNER Id = "+ownerId);

        initViews();

        addDogButton.setOnClickListener(view -> {
            addDog(this, ownerId);
            Intent intent = new Intent(this, DogsListActivity.class);
            intent.putExtra(EXTRA_OWNER, owner);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    public void addDog(Context context, int ownerID) {
        String dogName = dogNameEditText.getText().toString();
        String dogKind = dogKindEditText.getText().toString();
        int dogAge = Integer.parseInt(dogAgeEditText.getText().toString());
        int dogTall = Integer.parseInt(dogTallEditText.getText().toString());
        int dogWeight = Integer.parseInt(dogWeightEditText.getText().toString());

        // change it to DataRepository method addOwner
        DBHelper dbHelper = DBHelper.getInstance(context);
        DogsSQLiteDataSource dogsSQLiteDataSource = DogsSQLiteDataSource.getInstance(dbHelper);
        dogsSQLiteDataSource.addDog(ownerID, dogAge, dogTall, dogWeight, dogName, dogKind);
    }

    public void initViews() {
        dogNameEditText = findViewById(R.id.dog_name_edittext);
        dogNameEditText.setText("Chuck");
        dogKindEditText = findViewById(R.id.dog_kind_edittext);
        dogKindEditText.setText("German Sheepdog");
        dogAgeEditText = findViewById(R.id.dog_age_edittext);
        dogAgeEditText.setText(""+11);
        dogTallEditText = findViewById(R.id.dog_tall_edittext);
        dogTallEditText.setText(""+55);
        dogWeightEditText = findViewById(R.id.dog_weight_edittext);
        dogWeightEditText.setText(""+60);
        addDogButton = findViewById(R.id.add_dog_button);
    }
}
