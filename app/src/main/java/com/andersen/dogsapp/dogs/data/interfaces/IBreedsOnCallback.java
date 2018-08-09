package com.andersen.dogsapp.dogs.data.interfaces;

import com.andersen.dogsapp.dogs.data.entities.DogKind;

import java.util.List;

public interface IBreedsOnCallback {
    void breedsCallBack(List<DogKind> dogKinds);
}
