package com.andersen.dogsapp.dogs;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.andersen.dogsapp.dogs.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.database.OwnersSQLiteDataSource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataRepository {
    private static DataRepository dataRepository;

    private OwnersDataSource ownersDataSource;
    private DogsDataSource dogsDataSource;

    private OwnersSQLiteDataSource ownersSQLiteDataSource;
    private DogsSQLiteDataSource dogsSQLiteDataSource;


    private DataRepository(){
        ownersDataSource = OwnersDataSource.getInstance();
        dogsDataSource = DogsDataSource.getInstance();
    }

    public static DataRepository get(){
        if(dataRepository == null){
            dataRepository = new DataRepository();
        } return dataRepository;
    }

    public List<Owner> getOwners(Context context){
        return ownersDataSource.getOwners(context);
    }

    public List<Dog> getDogs(Context context){
        return dogsDataSource.getDogs(context);
    }

    public List<Dog> getOwnerDogs(Owner owner){
        return dogsDataSource.getOwnerDogs(owner);
    }
}
