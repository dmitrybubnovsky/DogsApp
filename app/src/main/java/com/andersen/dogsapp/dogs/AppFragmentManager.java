package com.andersen.dogsapp.dogs;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity;

public class AppFragmentManager {
    private static AppFragmentManager instance;
    private static final String BACK_STACK_ROOT_TAG = "root_fragment";

    public static AppFragmentManager getInstance() {
        if (instance == null) {
            instance = new AppFragmentManager();
        }
        return instance;
    }
                    
    public void replaceAddToBackStack(FragmentManager fragmentManager, Context context, String fragmentName, String fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName);
            replaceAndAddToBackStack(fragmentManager, fragment, fragmentTag)
                    .commit();
        }
    }

    private FragmentTransaction replaceAndAddToBackStack(FragmentManager fragmentManager, Fragment fragment, String fragmentTag) {
        return fragmentManager.beginTransaction()
                .replace(R.id.host_fragment_container, fragment, fragmentTag)
                .addToBackStack(BACK_STACK_ROOT_TAG);
    }

    public void deleteFragmentByTag(FragmentManager fragmentManager, String fragmentTag) {

        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    public void deleteFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    public <T> void addFragment(Context context, String fragmentName, String fragmentTag) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName);
                fragmentManager.beginTransaction()
                    .add(R.id.host_fragment_container, fragment, fragmentTag)
                    .commit(); //  .commitAllowingStateLoss();
        }
    }

    public void replaceAddToBackStack(FragmentManager fragmentManager, Context context, String fragmentName, String fragmentTag, Bundle bundle) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName, bundle);
            replaceAndAddToBackStack(fragmentManager, fragment, fragmentTag).commit();
        }
    }

    public void clearBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public <T> void replaceBreedFragment(Context context, String fragmentName, String fragmentTag, String keyArgs, T t) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            Bundle bundleArgs = new Bundle();
            bundleArgs.putParcelable(keyArgs, (Parcelable) t);
            fragment = Fragment.instantiate(context, fragmentName, bundleArgs);

            fragmentManager.beginTransaction()
                    .add(R.id.host_fragment_container, fragment, fragmentTag)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }
    }

//
//    private <T> void showFragment(String fragmentTag, Class<?> cls){ // , T data
//        Fragment fragm = fragManager.findFragmentByTag(fragmentTag);
//        if (fragm == null) {
//            try{
//                Constructor constructor = cls.getConstructor(); // data.getClass()
//                fragm = (Fragment) constructor.newInstance();  // data
//            } catch (Exception e) {
//            }
//            fragManager.beginTransaction()
//                    .replace(R.id.host_fragment_container, fragm, fragmentTag)
//                    .addToBackStack(BACK_STACK_ROOT_TAG)
//                    .commit();
//        }
//    }

}
