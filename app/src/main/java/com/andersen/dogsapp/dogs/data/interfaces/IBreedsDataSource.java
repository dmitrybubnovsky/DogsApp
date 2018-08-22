package com.andersen.dogsapp.dogs.data.interfaces;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.IWebCallback;

import java.util.List;

public interface IBreedsDataSource {
    void getDogsKinds(IWebCallback<List<DogKind>> responseCallback, IDatabaseCallback<List<DogKind>> dbCallback);

    void getBreedsImage(String breedString, IWebCallback<String> responseCallback);

    int updateBreedDBWithUriImage(DogKind dogKind);
}
