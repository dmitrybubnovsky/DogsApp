package com.andersen.dogsapp.dogs.ui.owners;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.repositories.OwnersRepository;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeOwner;

public class NewOwnerFormAcitivty extends AppCompatActivity {
    public static final String TAG = "#";
    private EditText ownerNameEditText;
    private EditText ownerSurnameEditText;
    private EditText preferredBreedEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_new_owner_form);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_owner);
        setSupportActionBar(toolbar);

        ownerNameEditText = findViewById(R.id.owner_name_edittext);
        ownerSurnameEditText = findViewById(R.id.surname_edittext);
        preferredBreedEditText = findViewById(R.id.preferred_kind_edit_text);

        // just for test
        testingFillEditText();

        Button button = findViewById(R.id.add_owner_button);
        button.setOnClickListener(view -> {
            Owner owner = addOwner();
            Intent intent = new Intent(this, DogsListActivity.class);
            intent.putExtra(DogsListActivity.EXTRA_OWNER, owner);
            startActivity(intent);
        });
    }

    private Owner addOwner() {
        String ownerName = ownerNameEditText.getText().toString();
        String ownerSurname = ownerSurnameEditText.getText().toString();
        String preferredBreed = preferredBreedEditText.getText().toString();
        Owner owner = new Owner(ownerName, ownerSurname, preferredBreed);
        owner = OwnersRepository.getInstance().addOwner(owner);
        return owner;
    }

    private void testingFillEditText() {
        ownerNameEditText.setText(SomeOwner.getInstance().name());
        ownerSurnameEditText.setText(SomeOwner.getInstance().surname());
        preferredBreedEditText.setText(SomeDog.getInstance().kind());
    }
}
