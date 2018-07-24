package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.Owner;

import java.util.List;

public interface IOwnersDataSource {
    List<Owner> getOwners();
}
