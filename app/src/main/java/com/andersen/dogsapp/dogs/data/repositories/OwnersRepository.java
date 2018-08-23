package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;

import java.util.List;

public class OwnersRepository implements IOwnersDataSource {
    private static final String TAG = "#";
    private static OwnersRepository instance;

    public OwnersRepository() {
    }

    public static OwnersRepository getInstance() {
        if (instance == null) {
            instance = new OwnersRepository();
        }
        return instance;
    }

    public static OwnersRepository get() {
        return instance;
    }

    @Override
    public List<Owner> getOwners() {
        return OwnersSQLiteDataSource.getInstance().getOwners();
    }

    @Override
    public Owner addOwner(Owner owner) {
        return OwnersSQLiteDataSource.getInstance().addOwner(owner);
    }
}
