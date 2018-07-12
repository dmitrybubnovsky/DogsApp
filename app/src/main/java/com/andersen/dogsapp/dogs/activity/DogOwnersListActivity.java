package com.andersen.dogsapp.dogs.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.DogToolBar;
import com.andersen.dogsapp.dogs.Owner;
import com.andersen.dogsapp.dogs.OwnersStorage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class DogOwnersListActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "#";

    List<Owner> owners;

    private LinearLayout containerLinLayout;

    private String ownersStringArray[];
    private String dogKindsStringArray[];
    private int quantitiesDogs[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        // init ownersStringArray[] and dogKindsStringArray[] from Resources
        initResources(R.array.owners, R.array.dogs_kinds);

        containerLinLayout = findViewById(R.id.owners_container);

        LayoutInflater layoutInflater = getLayoutInflater();

        for (int i = 0; i < ownersStringArray.length; i++) {
            View itemView = initItemView(layoutInflater, i);
            itemView.setOnClickListener(this);
            containerLinLayout.addView(itemView);
        }

        GsonBuilder builder = new GsonBuilder();
        final Gson GSON = builder.setPrettyPrinting().create();

        String json = getAssetsJSON("owners.json");

        //List<Owner> owners = GSON.fromJson(json, OwnersStorage.class).getOwners();

        // method 'init' initialize instance of his class OwnerStorage by copy
        // that has been taking as a parameter
        List<Owner> owners = OwnersStorage.init(GSON.fromJson(json, OwnersStorage.class)).getOwners();

//      Toast.makeText(getApplicationContext(), "Name : "+owners.get(0).getOwnerName(), Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(), "Name : "+owners.size(),Toast.LENGTH_LONG).show();
    }

    private View initItemView(LayoutInflater layoutInflater, int i) {
        View itemView = layoutInflater.inflate(R.layout.owners_item, containerLinLayout, false);

        String ownerName = ownersStringArray[i];
        String dogKind = dogKindsStringArray[i];
        int quantityDog = quantitiesDogs[i];

        // instantiate view-reference with inflated view
        AppTextView.newInstance(itemView, R.id.owner_name_textview)
                    .text(ownerName)
                    .style(this, R.style.TextViewTitleItem)
                    .build();

        AppTextView.newInstance(itemView, R.id.preffered_dog_textview)
                    .text(dogKind)
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

    private void initResources(int ownersArrayRes, int dogKindsArray) {
        quantitiesDogs = new int[]{3, 2, 2, 4, 5, 8, 5, 3, 7, 9};
        Resources resources = getResources();
        ownersStringArray = resources.getStringArray(ownersArrayRes);
        dogKindsStringArray = resources.getStringArray(dogKindsArray);
    }

    private void openOwnerDogs(View view) {
        TextView ownerNameTextView = view.findViewById(R.id.owner_name_textview);
        TextView quantityTextView = view.findViewById(R.id.quantity_textview);

        Intent i = new Intent(getApplicationContext(), OwnerDogsActivity.class);
        i.putExtra(OwnerDogsActivity.EXTRA_OWNER_NAME, ownerNameTextView.getText().toString());
        i.putExtra(OwnerDogsActivity.EXTRA_DOGS_QUANTITY, Integer.parseInt(quantityTextView.getText().toString()));
        startActivity(i);
    }

    private String getAssetsJSON(String fileName){
        String json = null;
        try{
            InputStream inputStream = this.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String (buffer, "UTF-8");
        } catch (IOException e ){
            e.printStackTrace();
        }
        return json;
    }
}