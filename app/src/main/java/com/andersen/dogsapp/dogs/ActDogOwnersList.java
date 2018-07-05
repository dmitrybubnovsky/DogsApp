package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.andersen.dogsapp.R;


public class ActDogOwnersList extends AppCompatActivity {
    String owner[];
    String kindOfDog[];
    int dogsQuantity[];

    String ownerName;
    String kindOfDogElement;
    int dogsQuantElem;

    LayoutInflater layoutInflater;
    private LinearLayout scrollinlayout;
    private static final String TAG = "#";
    private View inflatedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dogs_app);

        owner = getResources().getStringArray(R.array.owners);
        kindOfDog = getResources().getStringArray(R.array.kind_of_dogs);
        dogsQuantity = new int[]{1,2,3,4,6,8,5,3,7,9};

        scrollinlayout = findViewById(R.id.scroll_child_linlayout);
        layoutInflater = getLayoutInflater();

        for(int i = 0; i< owner.length; i++){
            ownerName = owner[i];
            kindOfDogElement = kindOfDog[i];
            dogsQuantElem = dogsQuantity[i];

            // instantiate view-reference with inflated view
            // and set tag for that view
            inflatedView = layoutInflater.inflate(R.layout.owners_item, scrollinlayout, false);

            TextView textViewName = inflatedView.findViewById(R.id.owner_name);
            textViewName.setText(ownerName);

            TextView textViewPreffereDog = inflatedView.findViewById(R.id.preffered_dog);
            textViewPreffereDog.setText(kindOfDogElement);

            TextView textViewDogQuant = inflatedView.findViewById(R.id.dogs_quantity);
            textViewDogQuant.setText(""+dogsQuantElem); //
            inflatedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  TextView textVName = view.findViewById(R.id.owner_name);
                  String ownNm = textVName.getText().toString();

                  TextView textViewQuant = view.findViewById(R.id.dogs_quantity);
                  Integer dogQuant = Integer.parseInt(textViewQuant.getText().toString());

                  Intent i = new Intent (getApplicationContext(), ActOwnersDog.class);
                  i.putExtra(ActOwnersDog.EXTRA_OWNER_NAME, ownNm);
                  i.putExtra(ActOwnersDog.EXTRA_DOGS_QUANTITY, dogQuant);
                  startActivity(i);
                }
            });
            scrollinlayout.addView(inflatedView);
        }
    }
}




//