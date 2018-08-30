package com.andersen.dogsapp.dogs.ui.dogs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.data.repositories.DogsRepository;
import com.andersen.dogsapp.dogs.ui.HorizontalDividerItemDecoration;
import com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity;

import java.util.List;

public class DogsListFragment extends Fragment implements IRecyclerItemListener<Dog>  {
    private static final String TAG = "#";
    public static final String DOGS_ARG = "dogs_arg";
    public static final String OWNER_ARG = "owner_arg";
    public static final String DOGS_TAG = "dogs_tag";

    public final int REQUEST_CODE_NEW_DOG = 2;
    private RecyclerView dogsRecyclerView;
    private Owner owner;
    private DogsAdapter dogsAdapter;
    private List<Dog> ownerDogs;

    IAddDogFragmentListener addDogListener;

    public interface IAddDogFragmentListener<T> {
        void onAddDogFragmentListener(T t);
    }

    public DogsListFragment() {}

    public static Fragment newInstance(Owner owner) {
        final DogsListFragment ownersListFragment = new DogsListFragment();
        final Bundle bundleArgs = new Bundle();
        bundleArgs.putParcelable(OWNER_ARG, owner);
        ownersListFragment.setArguments(bundleArgs);
        return ownersListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        addDogListener = (MainAppDescriptionActivity)context;
        Log.d(TAG, "DogsList onAttach"); // TODO Delete
    }

    @Override
    public void onDetach() {
        super.onDetach();
        addDogListener = null;
        Log.d(TAG, "DogsList onDetach");  // TODO Delete
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        final Bundle bundleArguments = getArguments();

        readArguments(bundleArguments);

        if (bundleArguments == null || !bundleArguments.containsKey(DOGS_TAG)) {
            Log.d("", "OwnersListFragment: bundleArguments == null || !bundleArguments.containsKey(OWNERS_ARG)");
        } else {
            Toast.makeText(getActivity(), "Owner's name "+owner.getOwnerName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_owner_dogs_list, container, false);

        dogsAdapter = new DogsAdapter(getActivity(), this);

        initRecyclerView(view);

        return view;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_menu_item:
                 addDogListener.onAddDogFragmentListener(owner);
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onResume() {
        super.onResume();
        ownerDogs = DogsRepository.get().getOwnerDogs(owner);
        if (ownerDogs.isEmpty()) {
            Toast.makeText(getActivity(), "DogListFragment: NO dogs", Toast.LENGTH_SHORT).show();
            // TODO: OPEN "ADD NEW DOG FRAGMENT"
        } else {
            Log.d(TAG, "DogsListFragment: onResume: owners not empty");
            updateUI();
        }
    }

    private void readArguments(Bundle bundle) {

        if (bundle != null) {
            Log.d(TAG, "OwnersListFragment: readArguments: bundle != null");
            owner = getArguments().getParcelable(OWNER_ARG);
        }
    }

    private void initRecyclerView(View view) {
        Drawable divider = getResources().getDrawable(R.drawable.dogs_divider);
        dogsRecyclerView = view.findViewById(R.id.owner_dogs_recycler_view);
        dogsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dogsRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));
        dogsRecyclerView.setAdapter(dogsAdapter);
    }

    private void updateUI() {
        dogsAdapter.setList(ownerDogs);
        dogsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_entity, menu);
    }

    @Override
    public void onRecyclerItemClick(Dog dog) {
        // должна реагировать на касание элемента списка
    }
}
