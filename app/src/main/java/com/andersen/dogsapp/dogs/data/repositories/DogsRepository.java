package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;

import java.util.List;

public class DogsRepository implements IDogsDataSource {
    private static final String TAG = "#";
    private static DogsRepository instance;

    public DogsRepository() {
    }

    public static DogsRepository getInstance() {
        if (instance == null) {
            instance = new DogsRepository();
        }
        return instance;
    }

    public static DogsRepository get() {
        return instance;
    }

    @Override
    public List<Dog> getOwnerDogs(Owner owner){
        return DogsSQLiteDataSource.getInstance().getOwnerDogs(owner);
    }

    @Override
    public List<Dog> getDogs(){
        return DogsSQLiteDataSource.getInstance().getDogs();
    }

    @Override
    public Dog addDog(Dog dog){
        return DogsSQLiteDataSource.getInstance().addDog(dog);
    }
}
