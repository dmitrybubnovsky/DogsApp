package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;

import java.util.List;

public class DogsRepository implements IDogsDataSource {
    private static final String TAG = "#";
    IDogsDataSource iDogsDataSource;
    private static DogsRepository instance;

    public DogsRepository(IDogsDataSource iDogsDataSource) {
        this.iDogsDataSource = iDogsDataSource;
    }

    public static void init (IDogsDataSource iDogsDataSource){
        if (instance == null) {
            instance = new DogsRepository(iDogsDataSource);
        }
    }

    public static DogsRepository get() {
        return instance;
    }

    @Override
    public List<Dog> getOwnerDogs(Owner owner){
        return iDogsDataSource.getOwnerDogs(owner);
    }

    @Override
    public List<Dog> getDogs(){
        return iDogsDataSource.getDogs();
    }

    @Override
    public Dog addDog(Dog dog){
        return iDogsDataSource.addDog(dog);
    }
}
