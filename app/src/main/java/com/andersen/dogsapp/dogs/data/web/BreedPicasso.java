package com.andersen.dogsapp.dogs.data.web;

import android.content.Context;

import com.squareup.picasso.Picasso;

public class BreedPicasso {
    private static BreedPicasso instance;
    Picasso picasso;

    public static BreedPicasso get(Context context) {
        if (instance == null) {
            instance = new BreedPicasso(context);
        }
        return instance;
    }

    public BreedPicasso(Context context) {
        Picasso.Builder picassoBuilder = new Picasso.Builder(context);
        Picasso picasso = picassoBuilder.build();
        Picasso.setSingletonInstance(picasso);
    }
}

