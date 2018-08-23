package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;

import java.util.List;

public class OwnersDataRepository implements IOwnersDataSource {
    private static final String TAG = "#";
    private static OwnersDataRepository instance;

    public OwnersDataRepository() {
    }

    public static OwnersDataRepository getInstance() {
        if (instance == null) {
            instance = new OwnersDataRepository();
        }
        return instance;
    }

    public static OwnersDataRepository get() {
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
