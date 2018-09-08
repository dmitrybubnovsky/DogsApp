package com.andersen.dogsapp.dogs.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.ui.owners.OwnersListFragment;

import static com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity.BACK_STACK_ROOT_TAG;


public class BaseFragment extends Fragment {
    private static Fragment newInstance() {  // Bundle bundle
        Fragment fragment = new BaseFragment();
        return fragment;
    }

    public static void startFragment(FragmentManager fragmentManager, String fragTag) {
        Fragment fragment = fragmentManager.findFragmentByTag(fragTag);
        if (fragment == null) {
            fragment = new OwnersListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.host_fragment_container, fragment, fragTag)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
        }
    }
}
