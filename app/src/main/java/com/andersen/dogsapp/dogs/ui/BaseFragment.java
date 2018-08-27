package com.andersen.dogsapp.dogs.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;


public class BaseFragment extends Fragment {
    public static Fragment newInstance(){  // Bundle bundle
        Fragment fragment = new BaseFragment();
//        fragment.setArguments(bundle);
        return fragment;
    }
}
