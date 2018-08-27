package com.andersen.dogsapp.dogs.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.andersen.dogsapp.dogs.camera.PictureUtils;
import com.andersen.dogsapp.dogs.data.entities.Dog;

public class DogImageUtils {

    public static Drawable getDogImage(Context context, String dogImageString) {
        Resources resources = context.getResources();
        Bitmap bitmap = PictureUtils.getScaledBitmap(dogImageString, (AppCompatActivity) context);
        return new BitmapDrawable(resources, bitmap);
    }

    public static boolean hasNetworkImage(Dog dog){
        return dog.getDogImageString().contains("_doggy_dogg.jpg");
    }

    public static Uri getUriFromDrawable(@NonNull Context context, String dogImageString)
            throws Resources.NotFoundException {
        Resources resources = context.getResources();
        int resId = resources.getIdentifier(dogImageString, "drawable",
                context.getPackageName());
        Uri resUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + resources.getResourcePackageName(resId)
                + '/' + resources.getResourceTypeName(resId)
                + '/' + resources.getResourceEntryName(resId));
        return resUri;
    }
}
