package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.andersen.dogsapp.R;

public class ActDogOwnersList extends AppCompatActivity {
    private String owner[];
    private String kindOfDog[];
    private int dogsQuantity[];
    private String ownerName;
    private String kindOfDogElement;
    private int dogsQuantElem;
    private LayoutInflater layoutInflater;
    private LinearLayout scrollinlayout;
    private static final String TAG = "#";
    private View inflatedView;
    private Toolbar toolbar;
    private Typeface typeFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dogs_app);

        toolbar = findViewById(R.id.toolbar_dogs_app);
        toolbar.setTitle(R.string.toolbar_title_owners_list);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorCustomBlueGrey));
        setSupportActionBar(toolbar);

        owner = getResources().getStringArray(R.array.owners);
        kindOfDog = getResources().getStringArray(R.array.kind_of_dogs);
        dogsQuantity = new int[]{1,2,3,4,6,8,5,3,7,9};

        scrollinlayout = findViewById(R.id.scroll_child_linlayout);
        layoutInflater = getLayoutInflater();

        typeFace = Typeface.createFromAsset(getAssets(), "fonts/DroidSerif-Italic.ttf");


        for(int i = 0; i< owner.length; i++){
            ownerName = owner[i];
            kindOfDogElement = kindOfDog[i];
            dogsQuantElem = dogsQuantity[i];
            Log.d(TAG,kindOfDog[i]);

            // instantiate view-reference with inflated view
            // and set tag for that view
            inflatedView = layoutInflater.inflate(R.layout.owners_item, scrollinlayout, false);

            TextView textViewName = inflatedView.findViewById(R.id.owner_name);
            textViewName.setText(ownerName);
            textViewName.setTextAppearance(this, R.style.TextViewTitleItem);

            TextView textViewPreffereDog = inflatedView.findViewById(R.id.preffered_dog);
            textViewPreffereDog.setTextAppearance(this, R.style.TextViewSubTitle);
            textViewPreffereDog.setTypeface(typeFace);
            textViewPreffereDog.setText(""+kindOfDogElement);

            TextView textViewDogQuant = inflatedView.findViewById(R.id.dogs_quantity);
            textViewDogQuant.setText(""+dogsQuantElem);

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




