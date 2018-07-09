package com.andersen.dogsapp.dogs;
import android.content.Intent;
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
    private Toolbar toolbar;

    private String kindOfDog[];
    private String dogName[];
    private String ownerName;

    private int dogsQuantity;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_owners_dog);

        toolbar = new DogToolBar().get(this, R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        kindOfDog = getResources().getStringArray(R.array.kind_of_dogs);
        dogName = getResources().getStringArray(R.array.dog_name);

        ownerName = getIntent().getStringExtra(EXTRA_OWNER_NAME);
        dogsQuantity = getIntent().getIntExtra(EXTRA_DOGS_QUANTITY, 0);

        dogsLinearLayout = findViewById(R.id.lin_layout_in_scrooll_dogs_list);
        ownerNameTextView = findViewById(R.id.text_view_owner_name);
        ownerNameTextView.setText(ownerName);
        layoutInflater = getLayoutInflater();
        Random r = new Random();
        for(int i=0; i<dogsQuantity; i++){
            inflatedView = layoutInflater.inflate(R.layout.owners_dog_item, dogsLinearLayout,  false);
            String kindOfDogElem = kindOfDog[r.nextInt(10)];
            String dogNameElem = dogName[r.nextInt(10)];

            TextView textViewKindDog = inflatedView.findViewById(R.id.kind_of_dog);
            textViewKindDog.setText(kindOfDogElem);

            TextView textViewDogName = inflatedView.findViewById(R.id.dog_name);
            textViewDogName.setText(dogNameElem);
            textViewDogName.setTextAppearance(this, R.style.TextViewSubTitle);

            dogsLinearLayout.addView(inflatedView);

            inflatedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String kindDog = ((TextView)view.findViewById(R.id.kind_of_dog)).getText().toString();
                    String dogNm = ((TextView)view.findViewById(R.id.dog_name)).getText().toString();

                    Intent i = new Intent (getApplicationContext(), DogsInfoActivity.class);
                    i.putExtra(DogsInfoActivity.EXTRA_KIND_DOG, kindDog);
                    i.putExtra(DogsInfoActivity.EXTRA_DOG_NAME, dogNm);
                    startActivity(i);
                }
            });

        }
        // TEST
        Toast.makeText(getApplicationContext(), "dogQuant = "+dogsQuantity, Toast.LENGTH_SHORT).show();


    }
}
