package com.andersen.dogsapp.dogs.ui.dogs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.AppFragmentManager;
import com.andersen.dogsapp.dogs.camera.PictureUtils;
import com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormActivity.EXTRA_FILE_PATH;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormActivity.REQUEST_CAMERA;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormActivity.REQUEST_CODE_PREVIEW;

public class DogPhotoPreviewFragment extends DialogFragment {
    public static final String PREVIEW_TAG = "preview_tag";
    public static final String PREVIEW_ARG = "preview_bundle_arg";
    private final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File photoFile;
    private Button cancelButton;
    private Button newPhotoButton;
    private Button savePhotoButton;
    private ImageView dogPhotoPreImageview;
    private String photoFilePathString;
    private static final String TAG = "#";

    static DogPhotoPreviewFragment newInstance(String photoFilePathString) {
        DogPhotoPreviewFragment fragDialog = new DogPhotoPreviewFragment();
        Bundle args = new Bundle();
        args.putString(PREVIEW_ARG, photoFilePathString);
        fragDialog.setArguments(args);
        return fragDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        readArguments();
    }

    public void readArguments() {
        final Bundle bundleArguments = getArguments();
        if (bundleArguments != null && bundleArguments.containsKey(PREVIEW_ARG)) {
            photoFilePathString = bundleArguments.getString(PREVIEW_ARG);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "PREVIEW onResume: getBackStackEntryCount " + ((MainAppDescriptionActivity) getActivity()).fragManager.getBackStackEntryCount());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dog_photo_preview, container, false);
        initViews(view);

        Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, getActivity());
        dogPhotoPreImageview.setImageBitmap(bitmap);

        cancelButton.setOnClickListener(view1 -> {
            // присвоить старое значение, которое пришло в интенте
            photoFilePathString = getArguments().getString(PREVIEW_ARG);
            backToNewDogFormActivity();
        });

        newPhotoButton.setOnClickListener(view1 -> {
            photoFile = getPhotoFile(getActivity());
            startCamera();
        });

        savePhotoButton.setOnClickListener(view1 -> {
            setFilePathString();
            updatePhotoView();
            backToNewDogFormActivity();
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                Uri uri = FileProvider.getUriForFile(getActivity(),
                        "com.andersen.dogsapp.fileprovider", photoFile);
                getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                setFilePathString();
                savePhotoButton.setEnabled(true);
            } else {
                Log.d(TAG, "DogPhotoPreview. REQUEST_CAMERA NOT OK");
            }
            updatePhotoView();
        }
    }

    private void initViews(View view) {
        dogPhotoPreImageview = view.findViewById(R.id.dog_photo_pre_imageview_frag);
        cancelButton = view.findViewById(R.id.cancel_button_frag);
        newPhotoButton = view.findViewById(R.id.new_photo_button_frag);
        savePhotoButton = view.findViewById(R.id.save_photo_button_frag);
        savePhotoButton.setEnabled(false);
    }

    private void backToNewDogFormActivity() {
        sendResult(RESULT_OK, photoFilePathString);
        AppFragmentManager.getInstance().deleteFragment(getActivity().getSupportFragmentManager(),
                DogPhotoPreviewFragment.this);
    }

    private void sendResult(int resultCode, String photoFilePathString) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_FILE_PATH, photoFilePathString);
        getTargetFragment().onActivityResult(REQUEST_CODE_PREVIEW, resultCode, intent);
    }

    private void startCamera() {
        Uri uri = FileProvider.getUriForFile(getActivity(),
                "com.andersen.dogsapp.fileprovider", photoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(captureImage, REQUEST_CAMERA);
    }

    private File getPhotoFile(Context context) {
        File filesDir = context.getFilesDir();
        String timeStamp = String.valueOf("dog_" + System.currentTimeMillis()) + ".jpg";
        return new File(filesDir, timeStamp);
    }

    private void updatePhotoView() {
        Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, getActivity());
        dogPhotoPreImageview.setImageBitmap(bitmap);
    }

    private void setFilePathString() {
        if (photoFile != null || photoFile.exists()) {
            photoFilePathString = photoFile.getPath();
        }
    }
}