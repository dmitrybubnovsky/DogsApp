package com.andersen.dogsapp.dogs.ui.owners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeOwner;

public class NewOwnerFormAcitivty extends AppCompatActivity {
    public static final String TAG = "#";
    DataRepository dataRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_owner_form_acitivty);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_owner);
        setSupportActionBar(toolbar);

        EditText ownerNameEditText = findViewById(R.id.owner_name_edittext);
        EditText ownerSurnameEditText = findViewById(R.id.surname_edittext);
        EditText preferredKindEditText = findViewById(R.id.preferred_kind_edit_text);
        // just for test
        testingFillEditText(ownerNameEditText, ownerSurnameEditText, preferredKindEditText);

        Button button = findViewById(R.id.add_owner_button);

        button.setOnClickListener(view -> {
            Owner owner = addOwner(this, ownerNameEditText,
                    ownerSurnameEditText, preferredKindEditText);
            Intent intent = new Intent(this, DogsListActivity.class);
            intent.putExtra(DogsListActivity.EXTRA_OWNER, owner);
            startActivity(intent);
        });
    }

    private Owner addOwner (Context context, EditText ownerNameEditText, EditText ownerSurnameEditText, EditText preferredKindEditText) {
        String ownerName = ownerNameEditText.getText().toString();
        String ownerSurname = ownerSurnameEditText.getText().toString();
        String preferredDogKind = preferredKindEditText.getText().toString();

        DBHelper dbHelper = DBHelper.getInstance(context);
        OwnersSQLiteDataSource ownersSQLiteDataSource = OwnersSQLiteDataSource.getInstance(dbHelper);
        DataRepository.get().addOwner(ownerName, ownerSurname, preferredDogKind);
        Owner owner = ownersSQLiteDataSource.getLastAddedOwner();

        String res = (owner == null) ? "null" : owner.getOwnerName();
        Log.d(TAG, "NewOwnerActivity EXTRA_OWNER = " + res);
        return owner;
    }

    private void testingFillEditText(EditText ownerNameEditText, EditText ownerSurnameEditText, EditText preferredKindEditText) {
        ownerNameEditText.setText(SomeOwner.get().name());
        ownerSurnameEditText.setText(SomeOwner.get().surname());
        preferredKindEditText.setText(SomeDog.get().kind());
    }
}
