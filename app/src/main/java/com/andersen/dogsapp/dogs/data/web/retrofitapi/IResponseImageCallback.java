package com.andersen.dogsapp.dogs.data.web.retrofitapi;

import android.widget.ProgressBar;

public interface IResponseImageCallback<String, ImageView, DogKind, ProgressBar> {

    void onResponseImageListener(String breed, ImageView imageView, DogKind dogKind, ProgressBar itemProgressBar);
}
