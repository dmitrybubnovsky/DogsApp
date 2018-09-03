package com.andersen.dogsapp.dogs.ui.dogs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.camera.PictureUtils;

import java.io.File;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormActivity.EXTRA_FILE_PATH;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormActivity.REQUEST_CAMERA;

public class DogPhotoPreviewFragment extends Fragment {
    public static final String TAG = "#";
    public static final String PREVIEW_TAG = "preview_tag";

    private final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File photoFile;
    private Button cancelButton;
    private Button newPhotoButton;
    private Button savePhotoButton;
    private ImageView dogPhotoPreImageview;
    private String photoFilePathString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        readArguments();

//        photoFilePathString = intent.getStringExtra(EXTRA_FILE_PATH);
        initViews();

        Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, this);
        dogPhotoPreImageview.setImageBitmap(bitmap);
    }

    public void readArguments() {
        final Bundle bundleArguments = getArguments();
        if (bundleArguments == null || !bundleArguments.containsKey(EXTRA_FILE_PATH)) {
        } else {
            photoFilePathString = bundleArguments.getString(EXTRA_FILE_PATH);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dog_photo_preview, container, false);
        Log.d(TAG, "Preview onCreateView");

        initViews(View view);

        cancelButton.setOnClickListener(view -> {
            // присвоить старое значение, которое пришло в интенте
            photoFilePathString = intent.getStringExtra(EXTRA_FILE_PATH);
            backToNewDogFormActivity();
        });

        newPhotoButton.setOnClickListener(view -> {
            photoFile = getPhotoFile(this);
            startCamera();
        });

        savePhotoButton.setOnClickListener(view -> {
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
                Uri uri = FileProvider.getUriForFile(this,
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
        dogPhotoPreImageview = view.findViewById(R.id.dog_photo_pre_imageview);
        cancelButton = view.findViewById(R.id.cancel_button);
        newPhotoButton = view.findViewById(R.id.new_photo_button);
        savePhotoButton = view.findViewById(R.id.save_photo_button);
        savePhotoButton.setEnabled(false);
    }

    private void backToNewDogFormActivity() {
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_FILE_PATH, photoFilePathString);
//        setResult(RESULT_OK, intent);
//        finish();
    }

    private void startCamera() {
        Uri uri = FileProvider.getUriForFile(this, "com.andersen.dogsapp.fileprovider", photoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        List<ResolveInfo> cameraActivities = getActivity().getPackageManager()
                .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : cameraActivities) {
            getActivity().grantUriPermission(activity.activityInfo.packageName,
                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        startActivityForResult(captureImage, REQUEST_CAMERA);
    }

    private File getPhotoFile(Context context) {
        File filesDir = context.getFilesDir();
        String timeStamp = String.valueOf("dog_" + System.currentTimeMillis()) + ".jpg";
        return new File(filesDir, timeStamp);
    }

    private void updatePhotoView() {
        Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, this);
        dogPhotoPreImageview.setImageBitmap(bitmap);
    }

    private void setFilePathString() {
        if (photoFile != null || photoFile.exists()) {
            photoFilePathString = photoFile.getPath();
        }
    }
}
