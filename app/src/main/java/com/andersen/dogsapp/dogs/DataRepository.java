package com.andersen.dogsapp.dogs;
import android.content.Context;

import java.util.List;

import com.andersen.dogsapp.dogs.data.DogsHandler;
import com.andersen.dogsapp.dogs.data.OwnersHandler;

public class DataRepository {
    private static DataRepository dataRepository;

    private OwnersDataSource ownersDataSource;
    private DogsDataSource dogsDataSource;

    private DogsHandler dogsHandler;
    private OwnersHandler ownersHandler;

    private DataRepository(){
        dogsHandler = DogsHandler.getInstance();
        ownersHandler = OwnersHandler.getInstance();
    }

    public static DataRepository get(){
        if(dataRepository == null){
            dataRepository = new DataRepository();
        } return dataRepository;
    }

    public List<Owner> getOwners(Context context){
        ownersDataSource = OwnersDataSource.getInstance(context);
        dogsDataSource = DogsDataSource.getInstance(context);
        ownersHandler.setOwners(ownersDataSource.getOwners(context));
        return ownersHandler.getOwners();
    }

    public List<Dog> getDogs(Context context){
        dogsHandler.setDogs(dogsDataSource.getDogs(context));
        return dogsHandler.getDogs();
    }

    public List<Dog> getOwnerDogs(Owner owner){
        return dogsHandler.getOwnerDogs(owner);
    }
}
