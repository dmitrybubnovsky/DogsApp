package com.andersen.dogsapp.dogs.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.DataRepository;
import com.andersen.dogsapp.dogs.DogToolBar;
import com.andersen.dogsapp.dogs.Owner;
import com.andersen.dogsapp.dogs.OwnersDataSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class DogOwnersListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "#";
    private List<Owner> owners;

    private LinearLayout containerLinLayout;

    private ArrayList<String> ownersNamesArrayList;
    private ArrayList<String> dogKindsArrayList;
    private int[] quantitiesDogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        initResources();

        containerLinLayout = findViewById(R.id.owners_container);

        LayoutInflater layoutInflater = getLayoutInflater();

        for (int i = 0; i < ownersNamesArrayList.size(); i++) {
            View itemView = initItemView(layoutInflater, i);
            itemView.setTag(owners.get(i).getOwnerId());
            itemView.setOnClickListener(this);
            containerLinLayout.addView(itemView);
        }
    }

    private View initItemView(LayoutInflater layoutInflater, int i) {
        View itemView = layoutInflater.inflate(R.layout.owners_item, containerLinLayout, false);

        String ownerName = ownersNamesArrayList.get(i);
        String prefferedDogKind = dogKindsArrayList.get(i);
        int quantityDog = quantitiesDogs[i];

        // instantiate view-reference with inflated view
        AppTextView.newInstance(itemView, R.id.owner_name_textview)
                    .text(ownerName)
                    .style(this, R.style.TextViewTitleItem)
                    .build();

        AppTextView.newInstance(itemView, R.id.preffered_dog_textview)
                    .text(prefferedDogKind)
                    .style(this, R.style.TextViewSubTitle)
                    .build();

        AppTextView.newInstance(itemView, R.id.quantity_textview)
                    .text("" + quantityDog)
                    .build();
        return itemView;
    }

    @Override
    public void onClick(View view) {
        openOwnerDogs(view);
    }

    private void initResources() {
        DataRepository dataRepository = DataRepository.get(this);
        owners = dataRepository.getOwners();

        ownersNamesArrayList = dataRepository.getOwnersNames();
        dogKindsArrayList = dataRepository.getPrefereDogsKinds();
        quantitiesDogs = dataRepository.getQuantitiesDogs();
    }

    private void openOwnerDogs(View view) {
        TextView ownerNameTextView = view.findViewById(R.id.owner_name_textview);
        TextView quantityTextView = view.findViewById(R.id.quantity_textview);

        Toast.makeText(getApplicationContext(), "getTag : "+view.getTag(),Toast.LENGTH_LONG).show();

        Intent i = new Intent(getApplicationContext(), OwnerDogsActivity.class);
        i.putExtra(OwnerDogsActivity.EXTRA_OWNER_NAME, ownerNameTextView.getText().toString());
        i.putExtra(OwnerDogsActivity.EXTRA_OWNER_ID, (Integer)view.getTag());
        i.putExtra(OwnerDogsActivity.EXTRA_DOGS_QUANTITY, Integer.parseInt(quantityTextView.getText().toString()));
        startActivity(i);
    }
}