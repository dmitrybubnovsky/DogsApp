package com.andersen.dogsapp.dogs.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.DataRepository;
import com.andersen.dogsapp.dogs.DogToolBar;
import com.andersen.dogsapp.dogs.Owner;
import java.util.ArrayList;
import java.util.List;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class DogOwnersListActivity extends AppCompatActivity{
    private List<Owner> owners;
    private ArrayList<String> ownersNames;
    private ArrayList<String> preferedDogKinds;
    private int[] quantitiesDogs;

    private Owner owner;
    private DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);

        dataRepository = DataRepository.get(this);

        LayoutInflater layoutInflater = getLayoutInflater();

        LinearLayout containerLinLayout = findViewById(R.id.owners_container);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        loadData(dataRepository);

        for (int i = 0; i < ownersNames.size(); i++) {
            View itemView = initItemView(layoutInflater, containerLinLayout, i);
            itemView.setTag(owners.get(i).getOwnerId());
            itemView.setOnClickListener(view -> {openOwnerDogs(view);});
            containerLinLayout.addView(itemView);
        }
    }

    private void openOwnerDogs(View view) {
        Integer ownerId = (Integer)view.getTag();
        owner = dataRepository.getOwnerById(ownerId);

        Intent intent = new Intent(getApplicationContext(), OwnerDogsActivity.class);
        intent.putExtra(OwnerDogsActivity.EXTRA_OWNER, owner);
        startActivity(intent);
    }

    private View initItemView(LayoutInflater layoutInflater, LinearLayout root, int i) {
        View itemView = layoutInflater.inflate(R.layout.owners_item, root, false);

        String ownerName = ownersNames.get(i);
        String prefferedDogKind = preferedDogKinds.get(i);
        int quantityDog = quantitiesDogs[i];

        AppTextView.newInstance(itemView, R.id.owner_name_textview)
                    .text(ownerName)
                    .style(this, R.style.TextViewTitleItem)
                    .build();

        AppTextView.newInstance(itemView, R.id.preffered_dog_textview)
                    .text(prefferedDogKind)
                    .build();

        AppTextView.newInstance(itemView, R.id.quantity_textview)
                    .text("" + quantityDog)
                    .build();
        return itemView;
    }

    private void loadData(DataRepository dataRepository) {
        owners = dataRepository.getOwners();
        ownersNames = dataRepository.getOwnersNames();
        preferedDogKinds = dataRepository.getPrefereDogsKinds();
        quantitiesDogs = dataRepository.getDogsCountsEachOwnerArray();
    }
}