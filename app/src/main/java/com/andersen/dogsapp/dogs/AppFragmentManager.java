package com.andersen.dogsapp.dogs;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andersen.dogsapp.R;

public class AppFragmentManager {
    public static AppFragmentManager instance;
    FragmentManager fragmentManager;

    private AppFragmentManager (Context context){
        fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
    }

    public static AppFragmentManager getInstance(Context context){
        if(instance == null){
            instance = new AppFragmentManager(context);
        }
        return instance;
    }

    public  <T> void replaceAddToBackStack(Context context, String fragmentName, String fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName);

            replaceAddToBackStack(fragment, fragmentTag);
        }
    }

    private void replaceAddToBackStack(Fragment fragment, String fragmentTag){
        fragmentManager.beginTransaction()
                .replace(R.id.host_fragment_container, fragment, fragmentTag)
                .addToBackStack(null)
                .commit();
    }

    public <T> void replaceFragmentWithEntity(Context context, String fragmentName, String fragmentTag, String keyArgs, T t ) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            Bundle bundleArgs = new Bundle();
            bundleArgs.putParcelable(keyArgs, (Parcelable) t);
            fragment = Fragment.instantiate(context, fragmentName, bundleArgs);

            replaceAddToBackStack(fragment, fragmentTag);
        }
    }


    public <T> void replaceFragmentWithBundle(Context context, String fragmentName, String fragmentTag, Bundle bundle) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName, bundle);
            replaceAddToBackStack(fragment, fragmentTag);
        }
    }

    public void clearBackStack() {
        Log.d("#","clearBackStack");
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


    public <T> void replaceBreedFragment(Context context, String fragmentName, String fragmentTag, String keyArgs, T t ) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            Bundle bundleArgs = new Bundle();
            bundleArgs.putParcelable(keyArgs, (Parcelable) t);
            fragment = Fragment.instantiate(context, fragmentName, bundleArgs);

            fragmentManager.beginTransaction()
                    .add(R.id.host_fragment_container, fragment, fragmentTag)
                    .addToBackStack(null)
                    .commit();
        }
    }

}
