package com.andersen.dogsapp.dogs.data.interfaces;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.ICallback;

import java.util.List;

public interface IBreedsDataSource {
    void getDogsKinds(ICallback<List<DogKind>> responseCallback);
}
