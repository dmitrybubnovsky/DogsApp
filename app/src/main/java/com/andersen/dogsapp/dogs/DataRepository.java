package com.andersen.dogsapp.dogs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import com.andersen.dogsapp.dogs.data.DogKind;
import com.andersen.dogsapp.dogs.data.DogsHandler;
import com.andersen.dogsapp.dogs.data.OwnersHandler;
import com.andersen.dogsapp.dogs.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.database.OwnerDBHelper;
import com.andersen.dogsapp.dogs.database.OwnersSQLiteDataSource;

public class DataRepository {
    private static DataRepository dataRepository;

    private OwnersDataSource ownersDataSource;
    private DogsDataSource dogsDataSource;

    private OwnersSQLiteDataSource ownersSQLiteDataSource;
    private DogsSQLiteDataSource dogsSQLiteDataSource;

    private DogsHandler dogsHandler;
    private OwnersHandler ownersHandler;

    private DataRepository() {
        dogsHandler = DogsHandler.getInstance();
        ownersHandler = OwnersHandler.getInstance();
    }

    public static DataRepository get() {
        if (dataRepository == null) {
            dataRepository = new DataRepository();
        }
        return dataRepository;
    }

    public List<Owner> getOwners(Context context) {
        ownersDataSource = OwnersDataSource.getInstance(context);
        ownersHandler.setOwners(ownersDataSource.getOwners(context));
        return ownersHandler.getOwners();
    }

    public List<Owner> getOwners(OwnerDBHelper ownerDBHelper) {
        SQLiteDatabase db = ownerDBHelper.getWritableDatabase();
        ownersSQLiteDataSource = OwnersSQLiteDataSource.getInstance(ownerDBHelper);
        dogsSQLiteDataSource = DogsSQLiteDataSource.getInstance(ownerDBHelper);
        ownersHandler.setOwners(ownersSQLiteDataSource.getOwners(ownerDBHelper));
        return ownersHandler.getOwners();
    }

    public List<Dog> getDogs(Context context) {
        dogsDataSource = DogsDataSource.getInstance(context);
        dogsHandler.setDogs(dogsDataSource.getDogs(context));
        return dogsHandler.getDogs();
    }

    public List<Dog> getDogs(OwnerDBHelper ownerDBHelper) {
        SQLiteDatabase db = ownerDBHelper.getWritableDatabase();

        dogsHandler.setDogs(dogsSQLiteDataSource.getDogs(ownerDBHelper));
        return dogsHandler.getDogs();
    }

    public List<Dog> getOwnerDogs(Owner owner) {
        return dogsHandler.getOwnerDogs(owner);
    }
}
