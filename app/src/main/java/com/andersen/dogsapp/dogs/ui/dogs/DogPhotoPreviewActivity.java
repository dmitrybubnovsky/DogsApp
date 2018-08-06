package com.andersen.dogsapp.dogs.ui.dogs;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.camera.PictureUtils;

import java.io.File;
import java.util.List;

import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormActivity.EXTRA_FILE_PATH;
import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormActivity.REQUEST_CAMERA;

public class DogPhotoPreviewActivity extends AppCompatActivity {
    public static final String TAG = "#";
    private final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private File photoFile;
    private Button cancelButton;
    private Button newPphotoButton;
    private Button savePhotoButton;
    private ImageView dogPhotoPreImageview;
    private String photoFilePathString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_photo_preview);

        Intent intent = getIntent();
        photoFilePathString = intent.getStringExtra(EXTRA_FILE_PATH);
        initViews();

        cancelButton.setOnClickListener(view -> {
            // присвоить старое значение, которое пришло в интенте
            photoFilePathString = intent.getStringExtra(EXTRA_FILE_PATH);
            backToNewDogFormActivity();
        });

        newPphotoButton.setOnClickListener(view -> {
            photoFile = getPhotoFile(this);
            startCamera();
        });

        savePhotoButton.setOnClickListener(view -> {
            setFilePathString();
            updatePhotoView();
            backToNewDogFormActivity();
        });

        Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, this);
        dogPhotoPreImageview.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                Uri uri = FileProvider.getUriForFile(this,
                        "com.andersen.dogsapp.fileprovider", photoFile);
                this.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                setFilePathString();
                savePhotoButton.setEnabled(true);
            } else {
                Log.d(TAG, "DogPhotoPreview. REQUEST_CAMERA NOT OK");
            }
            updatePhotoView();
        }
    }

    private void initViews() {
        dogPhotoPreImageview = findViewById(R.id.dog_photo_pre_imageview);
        cancelButton = findViewById(R.id.cancel_button);
        newPphotoButton = findViewById(R.id.new_photo_button);
        savePhotoButton = findViewById(R.id.save_photo_button);
        savePhotoButton.setEnabled(false);
    }

    private void backToNewDogFormActivity() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_FILE_PATH, photoFilePathString);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void startCamera() {
        Uri uri = FileProvider.getUriForFile(this, "com.andersen.dogsapp.fileprovider", photoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        List<ResolveInfo> cameraActivities = this.getPackageManager()
                .queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo activity : cameraActivities) {
            this.grantUriPermission(activity.activityInfo.packageName,
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
