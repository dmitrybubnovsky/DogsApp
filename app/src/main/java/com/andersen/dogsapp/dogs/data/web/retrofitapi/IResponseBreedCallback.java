package com.andersen.dogsapp.dogs.data.web.retrofitapi;

public interface IResponseBreedCallback<String, ImageView, DogKind> {

    void onResponseBreedCallbackListener(String breed, ImageView imageView, DogKind dogKind);
}
