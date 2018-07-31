package com.andersen.dogsapp.dogs.data.interfaces;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import java.util.List;

public interface IDogsDataSource {
    List<Dog> getOwnerDogs(Owner owner);

    List<Dog> getDogs();

    Dog addDog(Dog dog);
}
