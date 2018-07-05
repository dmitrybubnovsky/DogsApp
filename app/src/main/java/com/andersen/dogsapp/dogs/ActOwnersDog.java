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

public class ActOwnersDog extends AppCompatActivity {
    public static final String EXTRA_OWNER_NAME = "com.andersen.dogsapp.dogs.ActOwnersDog.owner_name";
    public static final String EXTRA_DOGS_QUANTITY = "com.andersen.dogsapp.dogs.ActOwnersDog.quantity";
    public String ownerName;
    public String kindOfDog[];
    public String dogName[];
    public int dogsQuantity;
    public LinearLayout linlayoutInScroollDogsList;
    private View inflatedView;
    private TextView textViewOwnerName;
    private LayoutInflater layoutInflater;
    public Toolbar toolbar;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_owners_dog);

        toolbar = findViewById(R.id.toolbar_dogs_app);
        toolbar.setTitle(R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        kindOfDog = getResources().getStringArray(R.array.kind_of_dogs);
        dogName = getResources().getStringArray(R.array.dog_name);

        ownerName = getIntent().getStringExtra(EXTRA_OWNER_NAME);
        dogsQuantity = getIntent().getIntExtra(EXTRA_DOGS_QUANTITY, 0);

        linlayoutInScroollDogsList = findViewById(R.id.lin_layout_in_scrooll_dogs_list);
        textViewOwnerName = findViewById(R.id.text_view_owner_name);
        textViewOwnerName.setText(ownerName);
        layoutInflater = getLayoutInflater();
        Random r = new Random();
        for(int i=0; i<dogsQuantity; i++){
            inflatedView = layoutInflater.inflate(R.layout.owners_dog_item, linlayoutInScroollDogsList,  false);
            String kindOfDogElem = kindOfDog[r.nextInt(10)];
            String dogNameElem = dogName[r.nextInt(10)];

            TextView textViewKindDog = inflatedView.findViewById(R.id.kind_of_dog);
            textViewKindDog.setText(kindOfDogElem);

            TextView textViewDogName = inflatedView.findViewById(R.id.dog_name);
            textViewDogName.setText(dogNameElem);

            linlayoutInScroollDogsList.addView(inflatedView);

            inflatedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String kindDog = ((TextView)view.findViewById(R.id.kind_of_dog)).getText().toString();
                    String dogNm = ((TextView)view.findViewById(R.id.dog_name)).getText().toString();

                    Intent i = new Intent (getApplicationContext(), ActDogsInfo.class);
                    i.putExtra(ActDogsInfo.EXTRA_KIND_DOG, kindDog);
                    i.putExtra(ActDogsInfo.EXTRA_DOG_NAME, dogNm);
                    startActivity(i);
                }
            });

        }
        // TEST
        Toast.makeText(getApplicationContext(), "dogQuant = "+dogsQuantity, Toast.LENGTH_SHORT).show();


    }
}
