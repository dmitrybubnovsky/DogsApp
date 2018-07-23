package com.andersen.dogsapp.dogs.activity;

import android.content.Context;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.DataRepository;
import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.DogToolBar;
import com.andersen.dogsapp.dogs.Owner;
import com.andersen.dogsapp.dogs.database.OwnerDBHelper;

import java.util.List;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class DogOwnersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);

        DataRepository dataRepository = DataRepository.get();

        /*
         *  для работы с SQLite'ом. Аналогично в OwnerDogsAcitivity
         */
        OwnerDBHelper ownerDBHelper = new OwnerDBHelper(this);
//        SQLiteDatabase db = ownerDBHelper.getWritableDatabase();
//        ownerDBHelper.addSomeDB();
        List<Owner> owners = dataRepository.getOwners(ownerDBHelper);
        List<Dog> dogs = dataRepository.getDogs(ownerDBHelper);

        /*
         *   для работы с json'ом. Аналогично в OwnerDogsAcitivity
         */
//        List<Owner> owners = dataRepository.getOwners(this);

        LayoutInflater layoutInflater = getLayoutInflater();
        LinearLayout containerLinLayout = findViewById(R.id.owners_container);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_owners_list, colorCustomBlueGrey);
        setSupportActionBar(toolbar);

        for (int i = 0; i < owners.size(); i++) {
            Owner owner = owners.get(i);
            View itemView = initItemView(layoutInflater, containerLinLayout, owner);
            itemView.setTag(owner.getOwnerId());
            itemView.setOnClickListener(view -> openOwnerDogs(owner));
            containerLinLayout.addView(itemView);
        }

    }

    private void openOwnerDogs(Owner owner) {
        Intent intent = new Intent(getApplicationContext(), OwnerDogsActivity.class);
        intent.putExtra(OwnerDogsActivity.EXTRA_OWNER, owner);
        startActivity(intent);
    }

    private View initItemView(LayoutInflater layoutInflater, LinearLayout root, Owner owner) {
        View itemView = layoutInflater.inflate(R.layout.owners_item, root, false);

        AppTextView.newInstance(itemView, R.id.owner_name_textview)
                .text(owner.getOwnerFullName())
                .style(this, R.style.TextViewTitleItem)
                .build();

        AppTextView.newInstance(itemView, R.id.preffered_dog_textview)
                .text(owner.getPreferedDogsKind())
                .build();

        AppTextView.newInstance(itemView, R.id.quantity_textview)
                .text("" + owner.getDogsQuantity())
                .build();
        return itemView;
    }
}