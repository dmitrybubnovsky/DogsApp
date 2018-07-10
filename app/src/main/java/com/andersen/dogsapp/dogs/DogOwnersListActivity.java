package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.andersen.dogsapp.R;
import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class DogOwnersListActivity extends AppCompatActivity {
    private static final String TAG = "#";

    private LayoutInflater layoutInflater;
    private LinearLayout scrollView;
    private View inflatedView;
    private Toolbar toolbar;

    private String owner[];
    private String kindOfDog[];
    private int dogsQuantity[];

    private String ownerName;
    private String kindOfDogElement;
    private int dogsQuantElem;

    TextView ownerNameTextView;
    TextView preffereDogTextView;
    TextView dogQuantTextView;

    TextViewCreator textViewCreator;

    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owners_list_activity);

        toolbar = new DogToolBar().get(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        // init owner[] and kindOfDog[] from Resources
        resources = getResources();
        initResources(R.array.owners, R.array.kind_of_dogs);
        dogsQuantity = new int[]{1,2,3,4,6,8,5,3,7,9};

        scrollView = findViewById(R.id.owners_container);
        layoutInflater = getLayoutInflater();

        for(int i = 0; i< owner.length; i++){
            ownerName = owner[i];
            kindOfDogElement = kindOfDog[i];
            dogsQuantElem = dogsQuantity[i];
            Log.d(TAG,kindOfDog[i]);

            // instantiate view-reference with inflated view
            // and set tag for that view
            inflatedView = layoutInflater.inflate(R.layout.owners_item, scrollView, false);

            ownerNameTextView = new TextViewCreator().create(this, inflatedView, R.id.owner_name_textview, ownerName, R.style.TextViewTitleItem);
            preffereDogTextView = new TextViewCreator().create(this,inflatedView, R.id.preffered_dog_textview, kindOfDogElement, R.style.TextViewSubTitle);
            dogQuantTextView = new TextViewCreator().create(inflatedView, R.id.quantity_textview, ""+dogsQuantElem);

            inflatedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView ownNameTextV = view.findViewById(R.id.owner_name_textview);
                    String ownNm = ownNameTextV.getText().toString();

                    TextView quantityTextView = view.findViewById(R.id.quantity_textview);
                    Integer dogQuant = Integer.parseInt(quantityTextView.getText().toString());

                    Intent i = new Intent (getApplicationContext(), OwnerDogsActivity.class);
                    i.putExtra(OwnerDogsActivity.EXTRA_OWNER_NAME, ownNm);
                    i.putExtra(OwnerDogsActivity.EXTRA_DOGS_QUANTITY, dogQuant);
                    startActivity(i);
                }
            });
            scrollView.addView(inflatedView);
        }
    }

    protected void initResources(int owners, int kindDogArray){
        owner = resources.getStringArray(owners);
        kindOfDog = resources.getStringArray(kindDogArray);
    }
}