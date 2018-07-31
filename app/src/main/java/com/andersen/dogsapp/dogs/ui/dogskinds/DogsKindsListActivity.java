package com.andersen.dogsapp.dogs.ui.dogskinds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.ui.dogs.DogsInfoActivity;

public class DogsKindsListActivity extends AppCompatActivity implements IRecyclerItemListener<Dog> {
    public static final String TAG = "#";
    public static final String EXTRA_DOG_KIND = "extra_dog_kind";
    private Dog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_kind_selector);

        dog = getIntent().getParcelableExtra(EXTRA_DOG_KIND);


        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_kinds_list);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.dogs_kinds_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DogsKindAdapter adapter = new DogsKindAdapter(this, dog, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onRecyclerItemClick(Dog dog) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DOG_KIND, dog);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }
}
