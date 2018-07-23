package com.andersen.dogsapp.dogs.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppTextView;
import com.andersen.dogsapp.dogs.DataRepository;
import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.DogToolBar;
import java.util.List;
import com.andersen.dogsapp.dogs.Owner;
import com.andersen.dogsapp.dogs.RecyclerViewAdapter;

import android.widget.Toast;
import android.util.Log;

public class OwnerDogsActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {
    public static final String TAG = "#";
    public static final String EXTRA_OWNER = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner";

    private DataRepository dataRepository;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_dogs_list_recyclerview);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        Owner owner = getIntent().getParcelableExtra(EXTRA_OWNER);
        dataRepository = DataRepository.get();

        List<Dog> dogs = dataRepository.getOwnerDogs(owner);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, dogs, this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        layoutManager.setSpanSizeLookup( new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // 2 column size for first row
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
    public void onItemClick(Dog dog) {
 //       Integer dogId = (Integer)view.getTag();
        Intent intent = new Intent(getApplicationContext(), DogsInfoActivity.class);
        intent.putExtra(DogsInfoActivity.EXTRA_DOG, dog);
        Log.d(TAG, ""+ dog.getDogKind());

        startActivity(intent);
    }
}
