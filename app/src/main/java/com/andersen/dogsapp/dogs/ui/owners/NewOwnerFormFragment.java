package com.andersen.dogsapp.dogs.ui.owners;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.repositories.OwnersRepository;
import com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeOwner;


public class NewOwnerFormFragment extends Fragment {
    public static final String TAG = "#";
    public static final String NEW_OWNER_TAG = "new_onwer_tag";
    private static final String NAME_ARG = "name";
    IAddedOwnerFragmentListener addedOwnerListener;
    private EditText ownerNameEditText;
    private EditText ownerSurnameEditText;
    private EditText preferredKindEditText;
    private Button addOwnerButton;

    public NewOwnerFormFragment() {
    }

    public static Fragment newInstance() {
        final NewOwnerFormFragment fragment = new NewOwnerFormFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addedOwnerListener = (MainAppDescriptionActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle bundleArguments = getArguments();
        if (bundleArguments == null || !bundleArguments.containsKey(NAME_ARG)) {
            Log.d("", "NewOwnerFormFragment: bundleArguments == null || !bundleArguments.containsKey(NAME_ARG)");
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    "bundleArgument is not null", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "NewOnwer: onResume: getBackStackEntryCount " + ((MainAppDescriptionActivity) getActivity()).fragManager.getBackStackEntryCount());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_new_owner_form, container, false);
        Log.d(TAG, "NewOwner onCreate");

        Toolbar toolbar = view.findViewById(R.id.toolbar_dogs_app);
        if (toolbar != null) {
            ((MainAppDescriptionActivity) getActivity()).setSupportActionBar(toolbar);
//            toolbar.setTitle(R.string.title_add_dog);
        }

        readBundle(bundle);

        initViews(view);

        testingFillEditText();

        addOwnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOwner();
                addedOwnerListener.onAddedOwnerListener();
//                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private Owner addOwner() {
        String ownerName = ownerNameEditText.getText().toString();
        String ownerSurname = ownerSurnameEditText.getText().toString();
        String preferredDogKind = preferredKindEditText.getText().toString();
        Owner owner = new Owner(ownerName, ownerSurname, preferredDogKind);
        owner = OwnersRepository.get().addOwner(owner);
        return owner;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
//            TODO read Bundle
        }
    }

    private void initViews(View view) {
        ownerNameEditText = view.findViewById(R.id.owner_name_edittext_frag);
        ownerSurnameEditText = view.findViewById(R.id.surname_edittext_frag);
        preferredKindEditText = view.findViewById(R.id.preferred_breed_edittext_frag);
        addOwnerButton = view.findViewById(R.id.add_owner_button_frag);
    }

    private void testingFillEditText() {
        ownerNameEditText.setText(SomeOwner.get().name());
        ownerSurnameEditText.setText(SomeOwner.get().surname());
        preferredKindEditText.setText(SomeDog.get().kind());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "NewOwner: onDetach:");
    }

    public interface IAddedOwnerFragmentListener {
        void onAddedOwnerListener();
    }
}
