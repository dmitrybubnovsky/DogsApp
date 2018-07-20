package com.andersen.dogsapp.dogs.activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;
import android.util.Log;

public class OwnerDogsActivity extends AppCompatActivity {
    public static final String EXTRA_OWNER = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner";

    private DataRepository dataRepository;

    private LinearLayout dogsLinearLayout;

    private TextView dogKindTextview;
    private TextView dogNameTextview;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);


        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        dataRepository = DataRepository.get();
        Owner owner = getIntent().getParcelableExtra(EXTRA_OWNER);
        DataRepository.get().getDogs(this);
        List<Dog> dogs = dataRepository.getOwnerDogs(owner);

        dogsLinearLayout = findViewById(R.id.dogs_container);

        AppTextView.newInstance(this, R.id.owner_name_textview)
                .text(owner.getOwnerFullName())
                .build();

        int dogsQuantity = owner.getDogsQuantity();
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < dogsQuantity; i++) {
            Dog dog = dogs.get(i);
            View itemView = initItemView(layoutInflater, dog);
            itemView.setOnClickListener(view -> onItemClick(view, dog));
            dogsLinearLayout.addView(itemView);
        }
    }

    private void onItemClick(View view, Dog dog) {
        Integer dogId = (Integer)view.getTag();
        Intent intent = new Intent(getApplicationContext(), DogsInfoActivity.class);
        intent.putExtra(DogsInfoActivity.EXTRA_DOG_ID, dogId);
        intent.putExtra(DogsInfoActivity.EXTRA_DOG, dog);
        startActivity(intent);
    }

    private View initItemView(LayoutInflater layoutInflater, Dog dog) {
        View itemView = layoutInflater.inflate(R.layout.dog_item, dogsLinearLayout, false);

        // set ID of the current dog to this itemView
        itemView.setTag(dog.getDogId());

        // initialize appropriate textview inside inflated itemView
        dogKindTextview = AppTextView.newInstance(itemView, R.id.dog_kind_textview)
                .text("" + dog.getDogKind())
                .build();

        // initialize this textview and put there dog's name
        dogNameTextview = AppTextView.newInstance(itemView, R.id.dog_name_textview)
                .text("" + dog.getDogName())
                .style(this, R.style.BoldRobotoThin)
                .build();
        return itemView;
    }
}
