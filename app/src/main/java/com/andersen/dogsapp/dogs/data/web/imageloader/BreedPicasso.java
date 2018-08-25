package com.andersen.dogsapp.dogs.data.web.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.andersen.dogsapp.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BreedPicasso {
    private static BreedPicasso instance;
    final int placeholder = R.drawable.smiled_dog_face;
    Picasso picasso;

    public BreedPicasso(Context context) {
        Picasso.Builder picassoBuilder = new Picasso.Builder(context);
        Picasso picasso = picassoBuilder.build();
        Picasso.setSingletonInstance(picasso);
    }

    public static BreedPicasso getInstance(Context context) {
        if (instance == null) {
            instance = new BreedPicasso(context);
        }
        return instance;
    }

    public void intoTarget(String uriBreedString, Target target) {
        picasso.get()
                .load(uriBreedString)
                .placeholder(placeholder)
                .resize(500,0)
                .into(target);
    }

    public void intoImageView(String uriBreedString, ImageView breedImageView) {
        picasso.get()
                .load(uriBreedString)
                .placeholder(placeholder)
                .resize(500,0)
                .into(breedImageView);
    }

    public Target getTarget(ProgressBar itemProgressBar, ImageView breedImageView, File breedImageFile) {
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                itemProgressBar.setVisibility(View.GONE);
                breedImageView.setImageBitmap(bitmap);
                new Thread(() -> {
                    try (FileOutputStream fos = new FileOutputStream(breedImageFile)){
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }
        };
    }
}

