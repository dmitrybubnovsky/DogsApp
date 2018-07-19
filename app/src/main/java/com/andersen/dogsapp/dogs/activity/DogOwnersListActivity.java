package com.andersen.dogsapp.dogs.activity;
import android.content.Intent;
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
import java.util.List;

import static com.andersen.dogsapp.R.color.colorCustomBlueGrey;

public class DogOwnersListActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);

        List<Owner> owners = DataRepository.get(this).getOwners();
        List<Dog> dogs = DataRepository.get(this).getDogs(this);
        // Toast.makeText(getApplicationContext(), ""+dogs.size(),Toast.LENGTH_SHORT).show();


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