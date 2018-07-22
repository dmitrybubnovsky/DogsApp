package com.andersen.dogsapp.dogs.activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AutoFitGridLayoutManager;
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
        setContentView(R.layout.activity_recycler_view_test);

        dogs = DataRepository.get().getDogs(this);
        owners = DataRepository.get().getOwners(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, dogs, this);
        recyclerView.setAdapter(adapter);

//        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        // 2 column size for first row
                        if(position % 2 == 0){
                            return 1;
                        } else
                        return 2;
                    }
                });
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
//    public void onItemClick(Owner owner, List<Dog> dogs) {
    public void onItemClick(Dog dog) {
        Toast.makeText(getApplicationContext(), dog.getDogName() + " is clicked", Toast.LENGTH_SHORT).show();
    }
}
