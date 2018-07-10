package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.andersen.dogsapp.R;
import java.util.Random;

public class OwnerDogsActivity extends AppCompatActivity {
    public static final String EXTRA_OWNER_NAME = "com.andersen.dogsapp.dogs.OwnerDogsActivity.owner_name";
    public static final String EXTRA_DOGS_QUANTITY = "com.andersen.dogsapp.dogs.OwnerDogsActivity.quantity";

    private LayoutInflater layoutInflater;
    private LinearLayout dogsLinearLayout;
    private View inflatedView;

    private TextView ownerNameTextView;
    private TextView textViewKindDog;
    private TextView textViewDogName;

    private Toolbar toolbar;

    private String kindOfDog[];
    private String dogName[];
    private String ownerName;

    private int dogsQuantity;

    private Resources resources;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dogs_list_activity);

        toolbar = new DogToolBar().get(this, R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        // init dogName[] and kindOfDog[] from Resources
        resources = getResources();
        initResources(R.array.dog_name, R.array.kind_of_dogs);

        ownerName = getIntent().getStringExtra(EXTRA_OWNER_NAME);
        dogsQuantity = getIntent().getIntExtra(EXTRA_DOGS_QUANTITY, 0);

        dogsLinearLayout = findViewById(R.id.dogs_container);

        ownerNameTextView = new TextViewCreator().create(this, R.id.owner_name_textview, ""+ownerName);

        layoutInflater = getLayoutInflater();
        Random r = new Random();
        for(int i=0; i<dogsQuantity; i++){
            inflatedView = layoutInflater.inflate(R.layout.dog_item, dogsLinearLayout,  false);
            String kindOfDogElem = kindOfDog[r.nextInt(10)];
            String dogNameElem = dogName[r.nextInt(10)];

            textViewKindDog = new TextViewCreator().create(inflatedView, R.id.kind_of_dog_textview, ""+kindOfDogElem);
            textViewDogName = new TextViewCreator().create(this, inflatedView, R.id.dog_name_textview, ""+dogNameElem, R.style.TextViewSubTitle);

            dogsLinearLayout.addView(inflatedView);

            inflatedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String kindDog = ((TextView)view.findViewById(R.id.kind_of_dog_textview)).getText().toString();
                    String dogNm = ((TextView)view.findViewById(R.id.dog_name_textview)).getText().toString();

                    Intent i = new Intent (getApplicationContext(), DogsInfoActivity.class);
                    i.putExtra(DogsInfoActivity.EXTRA_KIND_DOG, kindDog);
                    i.putExtra(DogsInfoActivity.EXTRA_DOG_NAME, dogNm);
                    startActivity(i);
                }
            });
        }
    }

    protected void initResources(int dogNameArray, int kindDogArray){
        dogName = getResources().getStringArray(R.array.dog_name);
        kindOfDog = getResources().getStringArray(R.array.kind_of_dogs);
    }
}
