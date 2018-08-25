package com.andersen.dogsapp.dogs.data.web.retrofitapi;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.andersen.dogsapp.dogs.data.entities.Breed;

public interface IResponseImageCallback {

    void onResponseImageListener(String breed, ImageView imageView, Breed breedEntity, ProgressBar itemProgressBar);
}
