package com.andersen.dogsapp.dogs.data;

import android.content.Context;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.database.DogKindsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.web.IWebCallback;
import com.andersen.dogsapp.dogs.data.interfaces.IDatabaseCallback;
import com.andersen.dogsapp.dogs.data.web.WebBreedsDataSource;

import java.util.ArrayList;
import java.util.List;

public class DogKindSourceCoordinator implements IBreedsDataSource {
    private static final String TAG = "#";
    private static DogKindSourceCoordinator instance;
    private List<DogKind> dogKinds;

    public DogKindSourceCoordinator() {
        dogKinds = new ArrayList<>();
    }

    public static DogKindSourceCoordinator getInstance() {
        if (instance == null) {
            instance = new DogKindSourceCoordinator();
        }
        return instance;
    }

    public static DogKindSourceCoordinator get() {
        return instance;
    }

    @Override
    public void getDogsKinds(IWebCallback<List<DogKind>> responseCallback, IDatabaseCallback<List<DogKind>> dbCallback) {
        if (DogKindsSQLiteDataSource.getInstance().isBreedDatabaseEmpty()) {
            Log.d(TAG, "DogKindCoordinator: DB Breeds is empty"); // TODO: Delete this testing Log.d
            WebBreedsDataSource.getInstance().getDogsKinds(responseCallback);
        } else {
            Log.d(TAG, "DogKindCoordinator: DB Breeds is already exists"); // TODO: Delete this testing Log.d
            DogKindsSQLiteDataSource.getInstance().getDogKinds(dbCallback);                                                                         Log.d(TAG, "DB Breeds is NOT empty");     // TODO Delete this Log.d
        }
    }

    @Override
//    public void getBreedsImage(Context context, String breedString, IWebCallback<String> callback) {
//        DogKindsLocalDataSource.getInstance().getBreedsImage(context, breedString, callback);
    public void getBreedsImage(String breedString, IWebCallback<String> callback) {
       WebBreedsDataSource.getInstance().getBreedsImage(breedString, callback);
    }

    @Override
    public int updateBreedDBWithUriImage (DogKind dogKind){
        return DogKindsSQLiteDataSource.getInstance().updateBreedDBWithUriImage(dogKind);
    }
}
