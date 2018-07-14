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
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.DataRepository;
import com.andersen.dogsapp.dogs.DogToolBar;

import com.andersen.dogsapp.dogs.DogsDataSource;
import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.Owner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class OwnerDogsActivity extends AppCompatActivity {
    public static final String EXTRA_OWNER_NAME = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner_name";
    public static final String EXTRA_OWNER_ID = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner_id";
    public static final String EXTRA_DOGS_QUANTITY = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.quantity";

    private LinearLayout dogsLinearLayout;
    private ArrayList<String> dogsKinds;  // 2
    private ArrayList<String> dogsNamesArrayList;  // 3
    private String ownerName;  //  1

    private List<Dog> dogs;
    private List<Owner> owners;
    private Owner owner;

    private TextView dogKindTextview;
    private TextView dogNameTextview;
    int ownerId;

    DataRepository dataRepository;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);

        dataRepository = DataRepository.get(this);


        dogs = new ArrayList<>();
        owners = new ArrayList<>();

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        ownerId = getIntent().getIntExtra(EXTRA_OWNER_ID, 0);
        ownerName = dataRepository.getOwnerById(ownerId).getOwnerFullName();

        initResources(R.array.dog_name, R.array.dogs_kinds);

        int dogsQuantity = getIntent().getIntExtra(EXTRA_DOGS_QUANTITY, 0);

        dogsLinearLayout = findViewById(R.id.dogs_container);

        AppTextView.newInstance(this, R.id.owner_name_textview)
                .text(ownerName)
                .build();

        Random random = new Random();
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < dogsQuantity; i++) {
            View itemView = initItemView(layoutInflater, random, i);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick();
                }
            });
            dogsLinearLayout.addView(itemView);
        }

    }

        private void initResources(int dogNameArrayRes, int dogKindsArrayRes) {
// this block will be deleted=====================================================
        GsonBuilder builder = new GsonBuilder();
        final Gson GSON = builder.setPrettyPrinting().create();
        String dogsJson = getAssetsJSON("dogs.json");
        DogsDataSource dogsDataSource = DogsDataSource.init(GSON.fromJson(dogsJson, DogsDataSource.class));
        dogs = dogsDataSource.getDogs();
//=================================================================================

        DataRepository dataRepository = DataRepository.get(this);
        dogs = dataRepository.getDogs();
        owners = dataRepository.getOwners();
        //owner = dataRepository.getOwnerById(3);
     //   dataRepository.getDogsNamesByOwnerId(ownerId);
        Toast.makeText(getApplicationContext(), "OwnerId: "
                +ownerId,Toast.LENGTH_LONG).show();

        Resources resources = getResources();
     //   dogsNamesArrayList = resources.getStringArray(dogNameArrayRes);
        dogsNamesArrayList = dataRepository.getDogsNamesByOwnerId(ownerId);
      //  dogsKinds = resources.getStringArray(dogKindsArrayRes);
        dogsKinds = dataRepository.getDogsKindsByOwnerId(ownerId);
    }

    private View initItemView(LayoutInflater layoutInflater, Random random, int i) {
        View itemView = layoutInflater.inflate(R.layout.dog_item, dogsLinearLayout, false);

        // initialize appropriate textview inside inflatedView
        String dogKindElem = dogsKinds.get(i);
        dogKindTextview = AppTextView.newInstance(itemView, R.id.dog_kind_textview)
                .text("" + dogKindElem)
                .build();

        String dogNameElem = dogsNamesArrayList.get(i);
        dogNameTextview = AppTextView.newInstance(itemView, R.id.dog_name_textview)
                .text("" + dogNameElem)
                .style(this, R.style.TextViewSubTitle)
                .build();
        return itemView;
    }

    private void onItemClick() {
        Intent i = new Intent(getApplicationContext(), DogsInfoActivity.class);
        i.putExtra(DogsInfoActivity.EXTRA_DOG_KIND, dogKindTextview.getText().toString());
        i.putExtra(DogsInfoActivity.EXTRA_DOG_NAME, dogNameTextview.getText().toString());
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
