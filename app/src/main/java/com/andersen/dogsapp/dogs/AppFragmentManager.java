package com.andersen.dogsapp.dogs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.andersen.dogsapp.R;

public class AppFragmentManager {
    private static AppFragmentManager instance;
    public static final String BACK_STACK_ROOT_TAG = "root_fragment";

    public static AppFragmentManager getInstance() {
        if (instance == null) {
            instance = new AppFragmentManager();
        }
        return instance;
    }

    public void replaceAddToBackStack(FragmentManager fragmentManager, Context context,
                                      String fragmentName, String fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName);
            replaceAndAddToBackStack(fragmentManager, fragment, fragmentTag)
                    .commit();
        }
    }

    public void replaceAddToBackStack(FragmentManager fragmentManager, Context context,
                                      String fragmentName, String fragmentTag, Bundle bundle) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, fragmentName, bundle);
            replaceAndAddToBackStack(fragmentManager, fragment, fragmentTag)
                    .commit();
        }
    }

    private FragmentTransaction replaceAndAddToBackStack(FragmentManager fragmentManager,
                                                         Fragment fragment, String fragmentTag) {
        return fragmentManager.beginTransaction()
                .replace(R.id.host_fragment_container, fragment, fragmentTag)
                .addToBackStack(BACK_STACK_ROOT_TAG);
    }

    public void clearBackStack(FragmentManager fragmentManager) {
        fragmentManager.popBackStack(BACK_STACK_ROOT_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void deleteFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    public void deleteFragmentByTag(FragmentManager fragmentManager, String fragmentTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTag);
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }
}
