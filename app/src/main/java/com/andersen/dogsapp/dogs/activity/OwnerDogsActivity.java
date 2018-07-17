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
import com.andersen.dogsapp.dogs.DogToolBar;
import java.util.ArrayList;
import android.widget.Toast;


public class OwnerDogsActivity extends AppCompatActivity {
    public static final String EXTRA_OWNER_ID = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner_id";

    private LinearLayout dogsLinearLayout;

    private int[] dogsIds;
    private ArrayList<String> dogsKinds;
    private ArrayList<String> dogsNamesArrayList;

    private TextView dogKindTextview;
    private TextView dogNameTextview;

    private DataRepository dataRepository;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);

        dataRepository = DataRepository.get(this);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_dogs_list);
        setSupportActionBar(toolbar);

        int ownerId = getIntent().getIntExtra(EXTRA_OWNER_ID, 0);

        initResources(ownerId);

        dogsLinearLayout = findViewById(R.id.dogs_container);

        AppTextView.newInstance(this, R.id.owner_name_textview)
                .text(dataRepository.getOwnerById(ownerId).getOwnerFullName())
                .build();

        int dogsQuantity = dataRepository.getOwnerById(ownerId).getDogsQuantity();
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < dogsQuantity; i++) {
            View itemView = initItemView(layoutInflater, i);
            itemView.setOnClickListener(view -> {onItemClick(view);});
            dogsLinearLayout.addView(itemView);
        }
    }

    private void onItemClick(View view) {
        Integer dogId = (Integer)view.getTag();
        Intent i = new Intent(getApplicationContext(), DogsInfoActivity.class);
        i.putExtra(DogsInfoActivity.EXTRA_DOG_ID, dogId);
        startActivity(i);
    }

    private void initResources(int ownerId) {
        dogsNamesArrayList = dataRepository.getDogsNamesByOwnerId(ownerId);
        dogsKinds = dataRepository.getDogsKindsByOwnerId(ownerId);
        dogsIds = dataRepository.getDogsIdsByOwnerId(ownerId);
    }

    private View initItemView(LayoutInflater layoutInflater, int i) {
        View itemView = layoutInflater.inflate(R.layout.dog_item, dogsLinearLayout, false);

        // set ID of the current dog to this itemView
        itemView.setTag(dogsIds[i]);

        // initialize appropriate textview inside inflated itemView
        dogKindTextview = AppTextView.newInstance(itemView, R.id.dog_kind_textview)
                .text("" + dogsKinds.get(i))
                .build();

        // initialize this textview and put there dog's name
        dogNameTextview = AppTextView.newInstance(itemView, R.id.dog_name_textview)
                .text("" + dogsNamesArrayList.get(i))
                .style(this, R.style.BoldRobotoThin)
                .build();
        return itemView;
    }
}
