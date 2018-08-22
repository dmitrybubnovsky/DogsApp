package com.andersen.dogsapp.dogs.data.web;

import android.content.Context;
import android.widget.ImageView;

import com.andersen.dogsapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class BreedPicasso {
    private static BreedPicasso instance;
    final int placeholder = R.drawable.smiled_dog_face;
    Picasso picasso;

    public BreedPicasso(Context context) {
        Picasso.Builder picassoBuilder = new Picasso.Builder(context);
        Picasso picasso = picassoBuilder.build();
        Picasso.setSingletonInstance(picasso);
    }

    public static BreedPicasso get(Context context) {
        if (instance == null) {
            instance = new BreedPicasso(context);
        }
        return instance;
    }

    public void initWithTarget(String uriBreedString, Target target){
        picasso.get()
               .load(uriBreedString)
               .placeholder(placeholder)
               .into(target);
    }

    public void intoImageView(String uriBreedString, ImageView dogKindImageView){
        picasso.get()
                .load(uriBreedString)
                .placeholder(placeholder)
                .error(R.drawable.smiled_dog_face)
                .into(dogKindImageView);
    }
}

