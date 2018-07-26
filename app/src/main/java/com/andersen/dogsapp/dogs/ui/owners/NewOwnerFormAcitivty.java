package com.andersen.dogsapp.dogs.ui.owners;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.andersen.dogsapp.R;

public class NewOwnerFormAcitivty extends AppCompatActivity {
    EditText ownerNameEditText;
    EditText ownerSurnameEditText;
    EditText preferredKindEditText;

    public static Intent newIntent (Context context, Class<?> activityClass){
        Intent i = new Intent (context, activityClass);
        return i;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_owner_form_acitivty);

        ownerNameEditText = findViewById(R.id.owner_name_edittext);
        ownerSurnameEditText = findViewById(R.id.surname_edittext);
        preferredKindEditText = findViewById(R.id.preferred_kind_edit_text);
        Button button = findViewById(R.id.add_owner_button);

        button.setOnClickListener(view ->{
            Intent intent = new Intent();
//            intent.putExtra();
            setResult(RESULT_OK, intent);
            finish();
        });

    }

    public void addOwner(){
        String ownerName = ownerNameEditText.getText().toString();
        String ownerSurname = ownerSurnameEditText.getText().toString();
        String preferredDogKind = preferredKindEditText.getText().toString();
    }
}
