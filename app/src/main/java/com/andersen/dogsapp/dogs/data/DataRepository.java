package com.andersen.dogsapp.dogs.data;

import java.util.List;

import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;

public class DataRepository {
    private IOwnersDataSource ownersDataSource;
    private IDogsDataSource dogsDataSource;

    private DataRepository(IOwnersDataSource ownersDataSource, IDogsDataSource dogsDataSource) {
        this.ownersDataSource = ownersDataSource;
        this.dogsDataSource = dogsDataSource;
    }

    public static DataRepository get(IOwnersDataSource ownersDataSource, IDogsDataSource dogsDataSource) {
        return new DataRepository(ownersDataSource, dogsDataSource);
    }

    public List<Dog> getOwnerDogs(Owner owner) {
        return dogsDataSource.getOwnerDogs(owner);
    }

    public List<Dog> getDogs() {
        return dogsDataSource.getDogs();
    }

    public List<Owner> getOwners() {
        return ownersDataSource.getOwners();
    }
}
