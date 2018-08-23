package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;

import java.util.List;

public class OwnersRepository {
    private static final String TAG = "#";
    private static OwnersRepository instance;
    private IOwnersDataSource iOwnersDataSource;

    public OwnersRepository(IOwnersDataSource iOwnersDataSource) {
        this.iOwnersDataSource = iOwnersDataSource;
    }

    public static void init(IOwnersDataSource iOwnersDataSource) {
        if (instance == null) {
            instance = new OwnersRepository(iOwnersDataSource);
        }
    }

    public static OwnersRepository get() {
        return instance;
    }

    public List<Owner> getOwners() {
        List<Owner> owners = iOwnersDataSource.getOwners();
        for (Owner owner : owners) {
            owner.setDogs(DogsRepository.get().getOwnerDogs(owner));
        }
        return owners;
    }

    public Owner addOwner(Owner owner) {
        return iOwnersDataSource.addOwner(owner);
    }
}
