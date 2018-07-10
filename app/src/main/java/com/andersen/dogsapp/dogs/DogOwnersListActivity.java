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

public class DogOwnersListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "#";

    private LayoutInflater layoutInflater;
    private LinearLayout scrollView;
    private View inflatedView;
    private Toolbar toolbar;

    private String owner[];
    private String dogKinds[];
    private int quantitiesDogs[];

    private String ownerName;
    private String kindDog;
    private int quantityDog;

    private TextView ownerNameTextView;
    private TextView preffereDogTextView;
    private TextView dogQuantTextView;

    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);

        toolbar = DogToolBar.init(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        // init owner[] and dogKinds[] from Resources
        initResources(R.array.owners, R.array.kind_of_dogs);
        quantitiesDogs = new int[]{1,2,3,4,6,8,5,3,7,9};

        scrollView = findViewById(R.id.owners_container);
        layoutInflater = getLayoutInflater();

        for(int i = 0; i< owner.length; i++){
            ownerName = owner[i];
            kindDog = dogKinds[i];
            quantityDog = quantitiesDogs[i];
            Log.d(TAG, dogKinds[i]);

            // instantiate view-reference with inflated view
            // and set tag for that view
            inflatedView = layoutInflater.inflate(R.layout.owners_item, scrollView, false);

            ownerNameTextView = new AppTextView.Builder().idTextView(inflatedView, R.id.owner_name_textview).text(ownerName)
                                .style(this,R.style.TextViewTitleItem).build();

            preffereDogTextView = new AppTextView.Builder().idTextView(inflatedView, R.id.preffered_dog_textview)
                                  .text(kindDog).style(this,R.style.TextViewSubTitle).build();

            dogQuantTextView  = new AppTextView.Builder().idTextView(inflatedView, R.id.quantity_textview)
                                .text(""+ quantityDog).build();

            inflatedView.setOnClickListener(this);
            scrollView.addView(inflatedView);
        }
    }

    public void onClick(View view) {
        TextView ownerNameTextView = view.findViewById(R.id.owner_name_textview);
        String ownNm = ownerNameTextView.getText().toString();

        TextView quantityTextView = view.findViewById(R.id.quantity_textview);
        Integer dogQuant = Integer.parseInt(quantityTextView.getText().toString());

        Intent i = new Intent (getApplicationContext(), OwnerDogsActivity.class);
        i.putExtra(OwnerDogsActivity.EXTRA_OWNER_NAME, ownNm);
        i.putExtra(OwnerDogsActivity.EXTRA_DOGS_QUANTITY, dogQuant);
        startActivity(i);
    }

    protected void initResources(int owners, int kindDogArray){
        resources = getResources();
        owner = resources.getStringArray(owners);
        dogKinds = resources.getStringArray(kindDogArray);
    }
}