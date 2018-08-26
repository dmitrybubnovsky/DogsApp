package com.andersen.dogsapp.dogs.ui.owners;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andersen.dogsapp.R;

public class OwnersListFragment extends Fragment {
    private static final String TAG = "#";
    private static final String NAME_ARG = "name";
    private String mName;


    public OwnersListFragment(){}

    public static Fragment newInstance(){
        final OwnersListFragment ownersListFragment = new OwnersListFragment();
        final Bundle bundleArgs = new Bundle();
        bundleArgs.putString(NAME_ARG,"OwnersListFragment says HELLO!");
        ownersListFragment.setArguments(bundleArgs);
        return ownersListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundleArguments = getArguments();
        if(bundleArguments == null || !bundleArguments.containsKey(NAME_ARG)){
            Log.d("", OwnersListFragment.class.getSimpleName()
                    + " bundleArguments == null || !bundleArguments.containsKey(NAME_ARG)");
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    "bundleArgument is "+bundleArguments.getString(NAME_ARG),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.activity_owners_list, container, false);
        readBundle(bundle);

        return view;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
//            name = bundle.getString("name");
//            age = bundle.getInt("age");
        }
    }
}
