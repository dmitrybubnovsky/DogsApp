package com.andersen.dogsapp.dogs.ui.breeds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.WebDataSource;
import com.andersen.dogsapp.dogs.services.WebBreedService;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.utils.NetWorkHelper;

import java.util.List;

public class BreedsListActivity extends AppCompatActivity implements IRecyclerItemListener<DogKind> {

    public static final String TAG = "#";
    public static final String EXTRA_SELECTED_KIND = "extra_kind";
    private boolean networkOk;

    private List<DogKind> dogKinds;

    TextView textView;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String sentMessage = intent.getStringExtra(WebBreedService.BREEDS_SERVICE_PAYLOAD);
            textView.append("\n"+networkOk + " " + sentMessage);
        }
    };

    private static final String JSON_URL = "https://dog.ceo/api/breeds/list/all";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list); //

        networkOk = NetWorkHelper.hasNetWorkAccess(this);

//        dogKinds = DataRepository.get().getDogKinds();
// START TEST
        textView = findViewById(R.id.textview);
        textView.setOnClickListener(view -> {
            Intent intent = new Intent(this, WebBreedService.class);
            intent.setData(Uri.parse(JSON_URL));
            startService(intent);
            startService(intent);
            startService(intent);
        });
// END TEST

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_kinds_list);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.breeds_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BreedsAdapter adapter = new BreedsAdapter(this, this);
        recyclerView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), "connection " + networkOk, Toast.LENGTH_LONG).show();

        LocalBroadcastManager.getInstance(getApplicationContext())
                .registerReceiver(broadcastReceiver,
                        new IntentFilter(WebBreedService.BREEDS_SERVICE_MESSAGE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext())
                                .unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onRecyclerItemClick(DogKind dogKind) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_KIND, dogKind);
        setResult(RESULT_OK, intent);
        finish();
    }


}

