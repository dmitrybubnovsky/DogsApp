package com.andersen.dogsapp.dogs.data;

import android.util.Log;

import com.andersen.dogsapp.dogs.data.database.DogKindsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.web.ICallback;
import com.andersen.dogsapp.dogs.data.web.IDatabaseCallback;
import com.andersen.dogsapp.dogs.data.web.WebBreedsDataSource;

import java.util.ArrayList;
import java.util.List;

public class DogKindSource implements IBreedsDataSource {
    private static final String TAG = "#";
    private static DogKindSource instance;
    private List<DogKind> dogKinds;

    public DogKindSource() {
        dogKinds = new ArrayList<>();
    }

    public static DogKindSource getInstance() {
        if (instance == null) {
            instance = new DogKindSource();
        }
        return instance;
    }

    public static DogKindSource get() {
        return instance;
    }

    @Override
    public void getDogsKinds(ICallback<List<DogKind>> responseCallback, IDatabaseCallback<List<DogKind>> dbCallback) {
        if (DogKindsSQLiteDataSource.getInstance().isBreedDatabaseEmpty()) {
            Log.d(TAG, "DB Breeds is empty");
            WebBreedsDataSource.getInstance().getDogsKinds(responseCallback);
        } else {
            Log.d(TAG, "DB Breeds is NOT empty");
            DogKindsSQLiteDataSource.getInstance().getDogKinds(dbCallback);
        }
//        WebBreedsDataSource.getInstance().getDogsKinds(callback);
    }

    @Override
    public void getBreedsImage(String breedString, ICallback<String> callback) {
        WebBreedsDataSource.getInstance().getBreedsImage(breedString, callback);
    }
}
