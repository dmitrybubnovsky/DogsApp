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
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity;

public class NewOwnerFormAcitivty extends AppCompatActivity {
    public static final String TAG = "#";
    EditText ownerNameEditText;
    EditText ownerSurnameEditText;
    EditText preferredKindEditText;

    public static Intent newIntent(Context context, Class<?> activityClass) {
        Intent i = new Intent(context, activityClass);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_owner_form_acitivty);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_owner);
        setSupportActionBar(toolbar);

        ownerNameEditText = findViewById(R.id.owner_name_edittext);
        ownerSurnameEditText = findViewById(R.id.surname_edittext);
        preferredKindEditText = findViewById(R.id.preferred_kind_edit_text);
        Button button = findViewById(R.id.add_owner_button);

        button.setOnClickListener(view -> {
            Owner owner = addOwner(this);
            Intent intent = new Intent(this, DogsListActivity.class);
            intent.putExtra(DogsListActivity.EXTRA_OWNER, owner);
            startActivity(intent);
        });

    }

    public Owner addOwner(Context context) {
        String ownerName = ownerNameEditText.getText().toString();
        String ownerSurname = ownerSurnameEditText.getText().toString();
        String preferredDogKind = preferredKindEditText.getText().toString();

        // change it to DataRepository method addOwner
        DBHelper dbHelper = DBHelper.getInstance(context);
        OwnersSQLiteDataSource ownersSQLiteDataSource = OwnersSQLiteDataSource.getInstance(dbHelper);
        ownersSQLiteDataSource.addOwner(ownerName, ownerSurname, preferredDogKind);
        Owner owner = ownersSQLiteDataSource.getLastAddedOwner();

        String res= (owner == null)?"null":owner.getOwnerName();
        Log.d(TAG, "NewOwnerActivity EXTRA_OWNER = "+res);
        return owner;
    }
}
