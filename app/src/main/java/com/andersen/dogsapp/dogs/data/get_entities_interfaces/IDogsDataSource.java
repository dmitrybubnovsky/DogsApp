package com.andersen.dogsapp.dogs.data.get_entities_interfaces;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import java.util.List;

public interface IDogsDataSource {
    List<Dog> getOwnerDogs(Owner owner);
}
