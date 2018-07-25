package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import java.util.List;

public interface IDogsDataSource {
    List<Dog> getOwnerDogs(Owner owner);
}
