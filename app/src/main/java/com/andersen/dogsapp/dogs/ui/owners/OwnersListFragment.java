package com.andersen.dogsapp.dogs.ui.owners;

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
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.data.repositories.OwnersRepository;
import com.andersen.dogsapp.dogs.ui.HorizontalDividerItemDecoration;
import com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity;

import java.util.ArrayList;
import java.util.List;

public class OwnersListFragment extends Fragment implements IRecyclerItemListener<Owner> {
    private static final String TAG = "#";
    private static final String OWNERS_ARG = "name";
    public static final String OWNERS_TAG = "owners_tag";

    private RecyclerView ownersRecyclerView;
    private OwnersAdapter ownersAdapter;

    IFragmentOwnerListener fragmentListener;
    public interface IFragmentOwnerListener<T> {
        void onFragmentOwnerListener(T t);
    }

    private List<Owner> owners;

    public OwnersListFragment() {
    }

    public static Fragment newInstance() {
        final OwnersListFragment ownersListFragment = new OwnersListFragment();
        final Bundle bundleArgs = new Bundle();
        return ownersListFragment;
    }

    public static Fragment newInstance(List<Owner> owners) { // ? extends Parcelable
        final OwnersListFragment ownersListFragment = new OwnersListFragment();
        final Bundle bundleArgs = new Bundle();
        bundleArgs.putParcelableArrayList(OWNERS_ARG, (ArrayList<Owner>) owners);
        ownersListFragment.setArguments(bundleArgs);
        return ownersListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentListener = (MainAppDescriptionActivity)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        final Bundle bundleArguments = getArguments();
        if (bundleArguments == null || !bundleArguments.containsKey(OWNERS_ARG)) {
            Log.d("", "OwnersListFragment: bundleArguments == null || !bundleArguments.containsKey(OWNERS_ARG)");
        } else {
            Toast.makeText(getActivity().getApplicationContext(),
                    "bundleArgument is " + bundleArguments.getString(OWNERS_ARG),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_owners_list, container, false);
//        setHasOptionsMenu(true);
        Log.d(TAG, "OwnersList onCreate");
        readBundle(bundle);

        ownersAdapter = new OwnersAdapter(getActivity(), this);

        initRecyclerView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        owners = OwnersRepository.get().getOwners();
        if (owners.isEmpty()) {
            Toast.makeText(getActivity(), "Owners is empty", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        } else {
            updateUI();
        }
        Log.d(TAG, "OwnersListFragment onResume");
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            Log.d(TAG, "OwnersListFragment: readBundle: bundle != null");
            owners = bundle.getParcelableArrayList(OWNERS_ARG);
        }
    }

    private void initRecyclerView(View view) {
        Drawable divider = getResources().getDrawable(R.drawable.owners_divider);
        ownersRecyclerView = view.findViewById(R.id.owners_recycler_view);
        ownersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ownersRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration(divider));
        ownersRecyclerView.setAdapter(ownersAdapter);
    }

    private void updateUI() {
        ownersAdapter.setOwners(owners);
        ownersAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_entity, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new_menu_item:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecyclerItemClick(Owner owner) {
        fragmentListener.onFragmentOwnerListener(owner); // TODO fix this warning
    }


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "OwnersListFragment onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "OwnersListFragment onDetach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "OwnersListFragment onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "OwnersListFragment onDestroy");
    }
}
