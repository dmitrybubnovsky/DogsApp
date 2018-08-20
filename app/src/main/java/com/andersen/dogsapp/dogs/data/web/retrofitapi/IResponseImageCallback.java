package com.andersen.dogsapp.dogs.data.web.retrofitapi;

public interface IResponseImageCallback<String, ImageView, DogKind> {

    void onResponseImageListener(String breed, ImageView imageView, DogKind dogKind);
}
