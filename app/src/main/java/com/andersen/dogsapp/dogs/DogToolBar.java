package com.andersen.dogsapp.dogs;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;

import com.andersen.dogsapp.R;

public class DogToolBar {
    private Toolbar dogToolbar;

    public DogToolBar(){
    }

    private DogToolBar(Activity activity, int str){
        dogToolbar = activity.findViewById(R.id.toolbar_dogs_app);
        dogToolbar.setTitle(str);
    }

    private DogToolBar(Activity activity, int str, int color){
        dogToolbar = activity.findViewById(R.id.toolbar_dogs_app);
        dogToolbar.setTitleTextColor(color);
        dogToolbar.setTitle(str);
    }

    public Toolbar get(Activity activity, int str){
        DogToolBar dtb = new DogToolBar(activity, str);
        return dtb.dogToolbar;
    }

    public Toolbar get(Activity activity, int str, int color){
        DogToolBar dtb = new DogToolBar(activity, str, color);
        return dtb.dogToolbar;
    }
}
