package com.andersen.dogsapp.dogs.data.web.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.andersen.dogsapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BreedImageLoader{
    public static final String TAG = "#";

    private static BreedImageLoader instance;
    final int placeholder = R.drawable.smiled_dog_face;
    final int error = R.drawable.smiled_dog_face;
    final File filesDir;

    public BreedImageLoader(Context context) {
        filesDir = context.getFilesDir();
    }

    public static BreedImageLoader getInstance(Context context) {
        if(instance == null){
            instance = new BreedImageLoader(context);
        }
        return instance;
    }


}
