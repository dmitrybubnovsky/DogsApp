package com.andersen.dogsapp.dogs;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.andersen.dogsapp.R;

public class AppFragmentManager {
    public static AppFragmentManager instance;
    public static final String BACK_STACK_ROOT_TAG = "root_fragment";
    private FragmentManager fragmentManager;

    private AppFragmentManager(Context context) {
        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
    }

    public static AppFragmentManager getInstance(Context context) {
        if (instance == null) {
            instance = new AppFragmentManager(context);
        }
        return instance;
    }
                    
    public <T> void replaceAddToBackStack(Context context, String fragmentName, String fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName);
            replaceAndAddToBackStack(fragment, fragmentTag)
                    .commit();
        }
    }

    public void deleteFragmentByTag(String fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    public void deleteFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    public <T> void addFragment(Context context, String fragmentName, String fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName);
                fragmentManager.beginTransaction()
                    .add(R.id.host_fragment_container, fragment, fragmentTag)
                    .commit(); //  .commitAllowingStateLoss();
        }
    }

    public <T> void replaceAddToBackStack(Context context, String fragmentName, String fragmentTag, Bundle bundle) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName, bundle);
            replaceAndAddToBackStack(fragment, fragmentTag).commit();
        }
    }

    private FragmentTransaction replaceAndAddToBackStack(Fragment fragment, String fragmentTag) {
        return fragmentManager.beginTransaction()
                .replace(R.id.host_fragment_container, fragment, fragmentTag)
                .addToBackStack(BACK_STACK_ROOT_TAG);
    }

    public void clearBackStack() {
//      fragmentManager.popBackStackImmediate(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public <T> void replaceBreedFragment(Context context, String fragmentName, String fragmentTag, String keyArgs, T t) {
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
}
