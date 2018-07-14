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
    public static final String EXTRA_OWNER_NAME = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner_name";
    public static final String EXTRA_OWNER_ID = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.owner_id";
    public static final String EXTRA_DOGS_QUANTITY = "com.andersen.dogsapp.dogs.activity.OwnerDogsActivity.quantity";

    private LinearLayout dogsLinearLayout;
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick();
                }
            });
            dogsLinearLayout.addView(itemView);
        }
    }

    private void initResources(int ownerId) {
        dogsNamesArrayList = dataRepository.getDogsNamesByOwnerId(ownerId);
        dogsKinds = dataRepository.getDogsKindsByOwnerId(ownerId);
    }

    private View initItemView(LayoutInflater layoutInflater, int i) {
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
}
