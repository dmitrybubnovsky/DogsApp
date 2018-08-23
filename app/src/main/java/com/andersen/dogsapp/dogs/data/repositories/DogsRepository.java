package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;

import java.util.List;

public class DogsRepository {
    private static final String TAG = "#";
    private static DogsRepository instance;
    private IDogsDataSource iDogsDataSource;

    public DogsRepository(IDogsDataSource iDogsDataSource) {
        this.iDogsDataSource = iDogsDataSource;
    }

    public static void init(IDogsDataSource iDogsDataSource) {
        if (instance == null) {
            instance = new DogsRepository(iDogsDataSource);
        }
    }

    public static DogsRepository get() {
        return instance;
    }

    public List<Dog> getOwnerDogs(Owner owner) {
        return iDogsDataSource.getOwnerDogs(owner);
    }

    public List<Dog> getDogs() {
        return iDogsDataSource.getDogs();
    }

    public Dog addDog(Dog dog) {
        return iDogsDataSource.addDog(dog);
    }
}
