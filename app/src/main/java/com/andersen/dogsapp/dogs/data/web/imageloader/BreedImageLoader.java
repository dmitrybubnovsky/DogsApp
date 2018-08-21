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

public class BreedImageLoader {
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

//    public void downloadImage(String uriBreedString, String fileBreedName, ImageView dogKindImageView){
//        try{
//            Picasso.get()
//                    .load(uriBreedString)
//                    .placeholder(placeholder)
//                    .error(error)
//                    .into(getTarget(fileBreedName, filesDir));
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

//    private static Target getTarget(final String fileBreedName, File filesDir){
//
//        Target target = new Target(){
//
//            @Override
//            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        File file = new File(filesDir + "/" + fileBreedName);
//                        try {
//                            file.createNewFile();
//                            FileOutputStream outputStream = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//                            outputStream.flush();
//                            outputStream.close();
//                        } catch (IOException e) {
//                            Log.e("IOException", e.getLocalizedMessage());
//                        }
//                    }
//                }).start();
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        };
//        return target;
//    }
}
