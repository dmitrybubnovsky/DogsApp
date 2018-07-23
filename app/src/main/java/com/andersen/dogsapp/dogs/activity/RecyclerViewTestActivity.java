package com.andersen.dogsapp.dogs.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.DataRepository;
import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.Owner;
import com.andersen.dogsapp.dogs.RecyclerViewAdapter;

import java.util.List;
public class RecyclerViewTestActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {
    List<Owner> owners;
    List<Dog> dogs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dogs_list_recyclerview);

        dogs = DataRepository.get().getDogs(this);
        owners = DataRepository.get().getOwners(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, dogs, this);

//        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if((position % 3) == 0){
                            return 2;
                        } else
                            return 1;
                    }
                });
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
//    public void onItemClick(Owner owner, List<Dog> dogs) {
    public void onItemClick(Dog dog) {
        Toast.makeText(getApplicationContext(), dog.getDogName() + " is clicked", Toast.LENGTH_SHORT).show();
    }


}
