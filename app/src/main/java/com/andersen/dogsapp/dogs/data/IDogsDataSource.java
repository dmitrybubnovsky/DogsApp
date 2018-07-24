package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.Owner;

import java.util.List;

public interface IDogsDataSource {
    List<Dog> getOwnerDogs(Owner owner);
}