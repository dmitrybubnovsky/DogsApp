package com.andersen.dogsapp.dogs.data;

import android.provider.ContactsContract;

import java.util.List;

import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;

public class DataRepository {
    private static DataRepository instance;

    private IOwnersDataSource ownersDataSource;
    private IDogsDataSource dogsDataSource;

    private DataRepository(IOwnersDataSource ownersDataSource, IDogsDataSource dogsDataSource) {
        this.ownersDataSource = ownersDataSource;
        this.dogsDataSource = dogsDataSource;
    }

    public static void init(IOwnersDataSource ownersDataSource, IDogsDataSource dogsDataSource) {
        if (instance == null){
            instance = new DataRepository(ownersDataSource, dogsDataSource);
        }
    }

    public static DataRepository get() {
        return instance;
    }

    public List<Dog> getOwnerDogs(Owner owner) {
        return dogsDataSource.getOwnerDogs(owner);
    }

    public List<Dog> getDogs() {
        return dogsDataSource.getDogs();
    }

    public List<Owner> getOwners() {
        List<Owner> owners = ownersDataSource.getOwners();
        for (Owner owner : owners){
            owner.setDogs(getOwnerDogs(owner));
        }
        return owners;
    }
}
