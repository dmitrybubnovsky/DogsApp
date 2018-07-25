package com.andersen.dogsapp.dogs.data.get_entities_interfaces;

import com.andersen.dogsapp.dogs.data.entities.Owner;

import java.util.List;

public interface IOwnersDataSource {
    List<Owner> getOwners();
}
