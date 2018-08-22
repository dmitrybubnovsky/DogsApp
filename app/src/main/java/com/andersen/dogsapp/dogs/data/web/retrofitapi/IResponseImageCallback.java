package com.andersen.dogsapp.dogs.data.web.retrofitapi;

import android.widget.ImageView;
import android.widget.ProgressBar;

import com.andersen.dogsapp.dogs.data.entities.DogKind;

public interface IResponseImageCallback {

    void onResponseImageListener(String breed, ImageView imageView, DogKind dogKind, ProgressBar itemProgressBar);
}
