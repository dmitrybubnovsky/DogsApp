package com.andersen.dogsapp.dogs.ui.dogs;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.ui.DogToolBar;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class NewDogFormActivity extends AppCompatActivity {
    public static final String  EXTRA_NEW_OWNER = "new owner dog";

    public static Intent newIntent(Context context, Class<?> activityClass) {
        Intent i = new Intent(context, activityClass);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog_form);

//        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_dog, colorCustomBlueGrey);
//        setSupportActionBar(toolbar);
    }
}
