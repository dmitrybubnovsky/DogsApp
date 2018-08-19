package com.andersen.dogsapp.dogs.data;

import android.database.sqlite.SQLiteDatabase;

import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DatabaseManager;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.web.ICallback;
import com.andersen.dogsapp.dogs.data.web.WebBreedsDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DogKindSource implements IBreedsDataSource {
    private static DogKindSource instance;
    private List<DogKind> dogKinds;

    public DogKindSource (DBHelper dbHelper){
        DatabaseManager.initInstance(dbHelper);
        dogKinds = new ArrayList<>();
    }

    public static DogKindSource getInstance(DBHelper dbHelper) {
        if (instance == null) {
            instance = new DogKindSource(dbHelper);
        }
        return instance;
    }

    public static DogKindSource get() {
        return instance;
    }

    @Override
    public void getDogsKinds(ICallback<List<DogKind>> callback){

        WebBreedsDataSource.getInstance().getDogsKinds(callback);
//
//        WebBreedsDataSource.getInstance().getDogsKinds(new ICallback<List<DogKind>>() {
//            @Override
//            public void onResponseICallback(List<DogKind> dogBreeds) {
//                dogKinds = dogBreeds;
//            }
//        });
    }

    @Override
    public void getBreedsImage(String breedString, ICallback<String> callback){
        WebBreedsDataSource.getInstance().getBreedsImage(breedString, callback);
    }
}
