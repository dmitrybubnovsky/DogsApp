package com.andersen.dogsapp.dogs.ui;

import android.app.Activity;
import android.support.v7.widget.Toolbar;

import com.andersen.dogsapp.R;

public class DogToolBar {
    private Toolbar dogToolbar;

    private DogToolBar(Activity activity, int resId, String str) {
        dogToolbar = activity.findViewById(R.id.toolbar_dogs_app);
        dogToolbar.setTitle(activity.getResources().getString(resId) + " " + str);
    }

    private DogToolBar(Activity activity, int str) {
        dogToolbar = activity.findViewById(R.id.toolbar_dogs_app);
        dogToolbar.setTitle(str);
    }

    private DogToolBar(Activity activity, int titleRes, int color) {
        dogToolbar = activity.findViewById(R.id.toolbar_dogs_app);
        dogToolbar.setTitle(titleRes);
        dogToolbar.setTitleTextColor(color);
    }

    public static Toolbar init(Activity activity, int str) {
        DogToolBar dtb = new DogToolBar(activity, str);
        return dtb.dogToolbar;
    }

    public static Toolbar init(Activity activity, int resId, String str) {
        DogToolBar dtb = new DogToolBar(activity, resId, str);
        return dtb.dogToolbar;
    }

    public static Toolbar init(Activity activity, int str, int color) {
        DogToolBar dtb = new DogToolBar(activity, str, color);
        return dtb.dogToolbar;
    }
}
