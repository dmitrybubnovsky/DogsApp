package com.andersen.dogsapp.dogs.data;

import android.content.Context;

import com.andersen.dogsapp.dogs.Owner;

import java.util.List;

public interface IOwnersDataSource {
    List<Owner> getOwners();
}
