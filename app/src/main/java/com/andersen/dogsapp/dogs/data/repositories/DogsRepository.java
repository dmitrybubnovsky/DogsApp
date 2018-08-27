package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;

import java.util.List;

public class DogsRepository {
    private static final String TAG = "#";
    private static DogsRepository instance;
    private IDogsDataSource dogsDataSource;

    private DogsRepository(IDogsDataSource dogsDataSource) {
        this.dogsDataSource = dogsDataSource;
    }

    public static void init(IDogsDataSource dogsDataSource) {
        if (instance == null) {
            instance = new DogsRepository(dogsDataSource);
        }
    }

    public static DogsRepository getInstance() {
        return instance;
    }

    public List<Dog> getOwnerDogs(Owner owner) {
        return dogsDataSource.getOwnerDogs(owner);
    }

    public List<Dog> getDogs() {
        return dogsDataSource.getDogs();
    }

    public Dog addDog(Dog dog) {
        return dogsDataSource.addDog(dog);
    }
}
