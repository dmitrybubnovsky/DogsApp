package com.andersen.dogsapp.dogs.data.interfaces;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.ICallback;
import com.andersen.dogsapp.dogs.data.web.IDatabaseCallback;

import java.util.List;

public interface IBreedsDataSource {
    void getDogsKinds(ICallback<List<DogKind>> responseCallback, IDatabaseCallback<List<DogKind>> dbCallback);

    void getBreedsImage(String breedString, ICallback<String> responseCallback);
}
