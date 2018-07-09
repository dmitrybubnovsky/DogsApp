package com.andersen.dogsapp.dogs;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.andersen.dogsapp.R;

import java.util.ArrayList;
import java.util.UUID;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class ActDogOwnersList extends AppCompatActivity  {
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
    private ArrayList<UUID> listOfAddedViewId;
    private UUID uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dogs_app);

        toolbar = findViewById(R.id.toolbar_dogs_app);
        toolbar.setTitle(R.string.toolbar_title_owners_list);
        toolbar.setTitleTextColor(getResources().getColor(colorCustomBlueGrey));

        setSupportActionBar(toolbar);

        owner = getResources().getStringArray(R.array.owners);
        kindOfDog = getResources().getStringArray(R.array.kind_of_dogs);
        dogsQuantity = new int[]{1,2,3,4,6,8,5,3,7,9};
        listOfAddedViewId = new ArrayList<>(owner.length);

        scrollinlayout = findViewById(R.id.scroll_child_linlayout);
        layoutInflater = getLayoutInflater();

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
            if (i % 2 == 0){
                textViewName.setEnabled(false);
            }
            textViewName.setTextAppearance(this, R.style.TextViewTitleItem);

            TextView textViewPreffereDog = inflatedView.findViewById(R.id.preffered_dog);
            textViewPreffereDog.setText(""+kindOfDogElement);
            textViewPreffereDog.setTextAppearance(this, R.style.TextViewSubTitle);

            TextView textViewDogQuant = inflatedView.findViewById(R.id.dogs_quantity);
            textViewDogQuant.setText(""+dogsQuantElem);

            uuid = UUID.randomUUID();
            listOfAddedViewId.add(uuid);
            inflatedView.setTag(uuid);

            scrollinlayout.addView(inflatedView);
            if (i != (owner.length-1)){
                inflatedView.setBackground(getResources().getDrawable(R.drawable.list_colors));

                inflatedView.setOnClickListener(new View.OnClickListener(){
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
            }
        }

        // set onTouchListener to all items except last one
//        for (int i = 0 ; i< listOfAddedViewId.size(); i++){
//            if (i != (owner.length-1)){
//            }
//        }
        //scrollinlayout.findViewWithTag(listOfAddedViewId.get(listOfAddedViewId.size()-1)).getBackground().setAlpha(128);
    }
}




