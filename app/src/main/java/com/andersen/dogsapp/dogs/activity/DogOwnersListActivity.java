package com.andersen.dogsapp.dogs.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.DogToolBar;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class DogOwnersListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "#";

    private String owners[];
    private String dogKinds[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        // init owners[] and dogKinds[] from Resources
        initResources(R.array.owners, R.array.dogs_kinds);
        int[] quantitiesDogs = new int[]{1, 2, 3, 4, 6, 8, 5, 3, 7, 9};

        LinearLayout scrollView = findViewById(R.id.owners_container);
        LayoutInflater layoutInflater = getLayoutInflater();

        for (int i = 0; i < owners.length; i++) {
            String ownerName = owners[i];
            String dogKind = dogKinds[i];
            int quantityDog = quantitiesDogs[i];

            // instantiate view-reference with inflated view
            // and set tag for that view
            View inflatedView = layoutInflater.inflate(R.layout.owners_item, scrollView, false);

            AppTextView.newInstance(inflatedView, R.id.owner_name_textview)
                    .text(ownerName)
                    .style(this, R.style.TextViewTitleItem)
                    .build();

            AppTextView.newInstance(inflatedView, R.id.preffered_dog_textview)
                    .text(dogKind)
                    .style(this, R.style.TextViewSubTitle)
                    .build();

            AppTextView.newInstance(inflatedView, R.id.quantity_textview)
                    .text("" + quantityDog)
                    .build();

            inflatedView.setOnClickListener(this);
            scrollView.addView(inflatedView);
        }
    }

    @Override
    public void onClick(View view) {
        openOwnerDogs(view);
    }

    private void initResources(int ownersArrayRes, int dogKindArray) {
        Resources resources = getResources();
        this.owners = resources.getStringArray(ownersArrayRes);
        dogKinds = resources.getStringArray(dogKindArray);
    }

    private void openOwnerDogs(View view) {
        TextView ownerNameTextView = view.findViewById(R.id.owner_name_textview);
        TextView quantityTextView = view.findViewById(R.id.quantity_textview);

        Intent i = new Intent(getApplicationContext(), OwnerDogsActivity.class);
        i.putExtra(OwnerDogsActivity.EXTRA_OWNER_NAME, ownerNameTextView.getText().toString());
        i.putExtra(OwnerDogsActivity.EXTRA_DOGS_QUANTITY, Integer.parseInt(quantityTextView.getText().toString()));
        startActivity(i);
    }
}