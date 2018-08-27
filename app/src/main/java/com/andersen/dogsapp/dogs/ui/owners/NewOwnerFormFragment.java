package com.andersen.dogsapp.dogs.ui.owners;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import java.util.List;

public class NewOwnerFormFragment extends Fragment {
    private static final String TAG = "#";
    private static final String NAME_ARG = "name";
    private String mName;
    private EditText ownerNameEditText;
    private EditText ownerSurnameEditText;
    private EditText preferredKindEditText;

    private List<Owner> owners;

    public NewOwnerFormFragment(){}

    public static Fragment newInstance(){
        final NewOwnerFormFragment newOwnerFragment = new NewOwnerFormFragment();
        final Bundle bundleArgs = new Bundle();
        bundleArgs.putString(NAME_ARG,"OwnersListFragment says HELLO!");
        newOwnerFragment.setArguments(bundleArgs);
        return newOwnerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundleArguments = getArguments();
        if(bundleArguments == null || !bundleArguments.containsKey(NAME_ARG)){
            Log.d("", "NewOwnerFormFragment: bundleArguments == null || !bundleArguments.containsKey(NAME_ARG)");
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    "bundleArgument is "+bundleArguments.getString(NAME_ARG),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_new_owner_form, container, false);
        readBundle(bundle);

        ownerNameEditText = view.findViewById(R.id.owner_name_edittext_frag);
        ownerSurnameEditText = view.findViewById(R.id.surname_edittext_frag);
        preferredKindEditText = view.findViewById(R.id.preferred_breed_edittext_frag);

        return view;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
//            name = bundle.getString("name");
//            age = bundle.getInt("age");
        }
    }
}
