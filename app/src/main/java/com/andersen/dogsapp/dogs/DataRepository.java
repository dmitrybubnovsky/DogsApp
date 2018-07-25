package com.andersen.dogsapp.dogs;

import java.util.List;

import com.andersen.dogsapp.dogs.data.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.IOwnersDataSource;
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

    public List<Dog> getDogs(Owner owner) {
        return dogsDataSource.getOwnerDogs(owner);
    }

    public List<Owner> getOwners() {
        return ownersDataSource.getOwners();
    }
}
