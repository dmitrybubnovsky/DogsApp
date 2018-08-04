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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.camera.PictureUtils;

import java.io.File;
import java.util.List;

import static com.andersen.dogsapp.dogs.ui.dogs.NewDogFormActivity.REQUEST_CAMERA;

public class DogPhotoPreviewActivity extends AppCompatActivity {
    private final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    public final int REQUEST_CODE_PREVIEW = 204;

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

        dogPhotoPreImageview = findViewById(R.id.dog_photo_pre_imageview);
        cancelButton = findViewById(R.id.cancel_button);
        savePhotoButton = findViewById(R.id.save_photo_button);

        photoFile = getPhotoFile(this);
        newPphotoButton = findViewById(R.id.new_photo_button);
        newPphotoButton.setOnClickListener(view -> {
            startCamera();
        });

        Intent intent = getIntent();
        photoFilePathString = intent.getStringExtra(NewDogFormActivity.EXTRA_FILE_PATH);

        Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, this);
        dogPhotoPreImageview.setImageBitmap(bitmap);


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            Uri uri = FileProvider.getUriForFile(this,
                    "com.andersen.dogsapp.fileprovider", photoFile);
            this.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
//            Log.d(TAG, "hasPhoto = "+hasPhoto);
        } // else { Log.d(TAG, "REQUEST_CAMERA. RESULT was NOT"); }
    }

    private void updatePhotoView() {
        if (photoFile != null || photoFile.exists()) {
            photoFilePathString = photoFile.getPath();
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, this);
            dogPhotoPreImageview.setImageBitmap(bitmap);
        }
    }
}
