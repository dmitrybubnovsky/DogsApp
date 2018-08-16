package com.andersen.dogsapp.dogs.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andersen.dogsapp.dogs.camera.PictureUtils;

public class DogImageUtils {
    final static int VALID_RESOURCE_ID = 0;
    public static Drawable getDogImage(Context context, String dogImageString) {

        Drawable dogImageDrawable;
        Resources resources = context.getResources();
        int dogImageResId = resources.getIdentifier(dogImageString, "drawable",
                context.getPackageName());
        if (dogImageResId != VALID_RESOURCE_ID) {
            dogImageDrawable = context.getResources().getDrawable(dogImageResId);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(dogImageString, (AppCompatActivity) context);
            dogImageDrawable = new BitmapDrawable(resources, bitmap);
        }
        return dogImageDrawable;
    }
}
