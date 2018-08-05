package com.andersen.dogsapp.dogs.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
//import android.widget.imageView;

import com.andersen.dogsapp.dogs.camera.PictureUtils;
import com.andersen.dogsapp.dogs.data.entities.Dog;

public class DogImageView {

    public static ImageView setDogImage(Context context, ImageView imageView, Dog dog) {
        int dogImageId = dog.getDogImageId(context);
        if (dogImageId != 0) {
            imageView.setImageResource(dogImageId);
        } else {
            try {
                Bitmap bitmap = PictureUtils.getScaledBitmap(dog.getDogImageString(), (AppCompatActivity) context);
                imageView.setImageBitmap(bitmap);
            } catch (NullPointerException e) {
                Log.d("#", "Houston! setData() method has a problem");
            }
        }
        return imageView;
    }
}
