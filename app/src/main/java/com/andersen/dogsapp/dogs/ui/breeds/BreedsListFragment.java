package com.andersen.dogsapp.dogs.ui.breeds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IRecyclerItemListener;
import com.andersen.dogsapp.dogs.data.repositories.BreedsRepository;
import com.andersen.dogsapp.dogs.data.web.imageloader.BreedPicasso;
import com.andersen.dogsapp.dogs.data.web.retrofitapi.IResponseImageCallback;
import com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BreedsListFragment extends Fragment
        implements IRecyclerItemListener<DogKind>, IResponseImageCallback {
    public static final String TAG = "#";
    public static final String OWNER_ARG = "BreedsListFragment_owner_arg";
    public static final String EXTRA_SELECTED_KIND = "extra_kind";
    private static final String BREEDS_ON_SAVE_INSTANCE_STATE_KEY = "breeds_bundle_key";
    private List<DogKind> dogKinds;
    private ProgressBar progressBar;
    private DogsKindAdapter adapter;
    private RecyclerView recyclerView;
    private Owner owner;
    private boolean calledFromDrawer;

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(BREEDS_ON_SAVE_INSTANCE_STATE_KEY, (ArrayList<DogKind>) dogKinds);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calledFromDrawer = false;

        final Bundle bundleArguments = getArguments();
        if (bundleArguments == null) {
            calledFromDrawer = true;
        } else {
            readBundle(bundleArguments);
        }

        if (savedInstanceState != null) {
            dogKinds = savedInstanceState.getParcelableArrayList(BREEDS_ON_SAVE_INSTANCE_STATE_KEY);
            Log.d(TAG, "dogKinds " + dogKinds.size());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_breeds_list, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "BREEDS onResume: getBackStackEntryCount " + ((MainAppDescriptionActivity) getActivity()).fragManager.getBackStackEntryCount());
        BreedsRepository.getInstance().getDogsKinds(dogBreeds -> {
            dogKinds = dogBreeds;
            getActivity().runOnUiThread(this::updateUI);
        });
    }

    private void updateUI() {
        if (dogKinds != null) {
            adapter.setBreeds(dogKinds);
            adapter.setResponseBreedCallbackListener(this);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            if (progressBar != null && dogKinds != null) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onResponseImageListener(String dogKindString, ImageView dogKindImageView, DogKind dogKindInstance, ProgressBar itemProgressBar) {

        if (dogKindInstance.getUriImageString().isEmpty()) {
            BreedsRepository.getInstance().getBreedsImage(dogKindString, uriBreedString -> {
                final File breedImageFile = getImageBreedFile(getActivity(), dogKindString);
                dogKindInstance.setImageString(breedImageFile.getAbsolutePath());
                // обновить поле imageString в БД
                BreedsRepository.getInstance().updateBreedDBWithUriImage(dogKindInstance);

                Target target = BreedPicasso.get(getActivity())
                        .getTarget(itemProgressBar, dogKindImageView, breedImageFile);
                dogKindImageView.setTag(target);
                BreedPicasso.get(getActivity())
                        .intoTarget(uriBreedString, target);
            });
        } else {
            BreedPicasso.get(getActivity())
                    .intoImageView(dogKindInstance.getUriImageString(), dogKindImageView);
        }
    }

    private void readBundle(final Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(OWNER_ARG)) {
                owner = bundle.getParcelable(OWNER_ARG);
            }
        }
    }

    private File getImageBreedFile(Context context, String breedFileNameString) {
        File filesDir = context.getFilesDir();
        String timeStamp = String.valueOf(breedFileNameString + ".jpeg");
        return new File(filesDir, timeStamp);
    }

    @Override
    public void onRecyclerItemClick(DogKind dogKind) {
        sendResultBreed(Activity.RESULT_OK, dogKind);
        ((MainAppDescriptionActivity) getActivity()).deleteFragment(BreedsListFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "breeds onDestroy");
    }

    private void sendResultBreed(int resultCode, DogKind dogkind) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_KIND, dogkind);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void initViews(View view) {
        progressBar = view.findViewById(R.id.network_breeds_progress_bar_frag);
        if (dogKinds == null) {
            progressBar.setVisibility(View.VISIBLE);
        }
        adapter = new DogsKindAdapter(getActivity(), this);
        recyclerView = view.findViewById(R.id.breeds_recycler_view_frag);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}

