package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;

import java.util.List;

public class OwnersRepository implements IOwnersDataSource {
    private static final String TAG = "#";
    private static OwnersRepository instance;
    private IOwnersDataSource ownersDataSource;

    public OwnersRepository(IOwnersDataSource ownersDataSource) {
        this.ownersDataSource = ownersDataSource;
    }

    public static void init (IOwnersDataSource ownersDataSource){
        if (instance == null) {
            instance = new OwnersRepository(ownersDataSource);
        }
    }

    public static OwnersRepository get() {
        return instance;
    }

    public List<Owner> getOwners() {
        return ownersDataSource.getOwners();
    }

    public Owner addOwner(Owner owner) {
        return ownersDataSource.addOwner(owner);
    }
}
