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
    public static final String NEW_OWNER_TAG = "new_onwer_tag";
    private static final String TAG = "#";
    private IAddedOwnerFragmentListener addedOwnerListener;
    private EditText ownerNameEditText;
    private EditText ownerSurnameEditText;
    private EditText preferredKindEditText;
    private Button addOwnerButton;

    public interface IAddedOwnerFragmentListener {
        void onAddedOwnerListener();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addedOwnerListener = (MainAppDescriptionActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_new_owner_form, container, false);

        initViews(view);
        testingFillEditText();

        addOwnerButton.setOnClickListener(v -> {
            addOwner();
            addedOwnerListener.onAddedOwnerListener();
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        addedOwnerListener = null;
    }

    private void addOwner() {
        String ownerName = ownerNameEditText.getText().toString();
        String ownerSurname = ownerSurnameEditText.getText().toString();
        String preferredDogKind = preferredKindEditText.getText().toString();
        OwnersRepository.get().addOwner(new Owner(ownerName, ownerSurname, preferredDogKind));
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
}
