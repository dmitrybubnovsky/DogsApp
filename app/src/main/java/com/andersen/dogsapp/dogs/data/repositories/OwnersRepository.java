package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;

import java.util.List;

public class OwnersRepository {
    private static final String TAG = "#";
    private static OwnersRepository instance;
    private IOwnersDataSource ownersDataSource;

    private OwnersRepository(IOwnersDataSource ownersDataSource) {
        this.ownersDataSource = ownersDataSource;
    }

    public static void init(IOwnersDataSource ownersDataSource) {
        if (instance == null) {
            instance = new OwnersRepository(ownersDataSource);
        }
    }

    public static OwnersRepository getInstance() {
        return instance;
    }

    public List<Owner> getOwners() {
        List<Owner> owners = ownersDataSource.getOwners();
        for (Owner owner : owners) {
            owner.setDogs(DogsRepository.getInstance().getOwnerDogs(owner));
        }
        return owners;
    }

    public Owner addOwner(Owner owner) {
        return ownersDataSource.addOwner(owner);
    }
}
