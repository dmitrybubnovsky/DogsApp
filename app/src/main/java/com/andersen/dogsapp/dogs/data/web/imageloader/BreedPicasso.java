package com.andersen.dogsapp.dogs.data.web.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.andersen.dogsapp.R;
import com.squareup.picasso.NetworkPolicy;
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

    public static BreedPicasso get(Context context) {
        if (instance == null) {
            instance = new BreedPicasso(context);
        }
        return instance;
    }

    public void intoTarget(String uriBreedString, Target target) {
        picasso.get()
                .load(uriBreedString)
                .placeholder(placeholder)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(500,0)
                .into(target);
    }

    public void intoImageView(String uriBreedString, ImageView dogKindImageView) {
        picasso.get()
                .load(uriBreedString)
                .placeholder(placeholder)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .resize(500,0)
                .into(dogKindImageView);
    }

    public Target getTarget(ProgressBar itemProgressBar, ImageView dogKindImageView, File breedImageFile) {
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                itemProgressBar.setVisibility(View.GONE);
                dogKindImageView.setImageBitmap(bitmap);
                new Thread(() -> {
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(breedImageFile);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

