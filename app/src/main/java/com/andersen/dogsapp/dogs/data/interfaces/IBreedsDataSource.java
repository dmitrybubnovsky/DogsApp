package com.andersen.dogsapp.dogs.data.interfaces;

import android.app.Activity;
import android.content.Context;

import com.andersen.dogsapp.dogs.data.entities.DogKind;

import java.util.List;

public interface IBreedsDataSource {
    List<DogKind> getDogsKinds();
}
