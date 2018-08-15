package com.andersen.dogsapp.dogs.ui.dogs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.camera.PictureUtils;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.dogskinds.DogsKindsListActivity;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;

import java.io.File;

import static com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity.EXTRA_OWNER;
import static com.andersen.dogsapp.dogs.ui.dogskinds.DogsKindsListActivity.EXTRA_SELECTED_KIND;

public class NewDogFormActivity extends AppCompatActivity {
    public static final String TAG = "#";
    private static final int PERMISSIONS_REQUEST = 115;
    private static final int PERMISSION_STORAGE_REQUEST = 114;
    private static final int PERMISSION_CAMERA_REQUEST = 116;
    private static final String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA};
    private static final String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    public static final String EXTRA_NEW_OWNER = "new owner dog";
    public static final String EXTRA_DOG_FOR_KIND = "EXTRA_DOG_FOR_KIND";
    public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
    public static final int REQUEST_CAMERA = 201;
    public final int REQUEST_CODE_DOG_KIND = 202;
    public final int REQUEST_CODE_PREVIEW = 203;
    private EditText dogNameEditText;
    private EditText dogKindEditText;
    private EditText dogAgeEditText;
    private EditText dogTallEditText;
    private EditText dogWeightEditText;
    private Button addDogButton;
    private Owner owner;
    private Dog dog;
    private ImageView photoDogImageView;
    private File photoFile;
    private DogKind dogKind;
    private String photoFilePathString;
    private boolean hasPhoto;
    private View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog_form);
        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_dog);
        setSupportActionBar(toolbar);
        rootLayout = findViewById(R.id.new_dog_form_root);

        hasPhoto = false;
        owner = getIntent().getParcelableExtra(EXTRA_NEW_OWNER);

        initViews();
        testingFillEditText();
        createDogModelWithInputDatas();

        dogKindEditText.setFocusable(false);
        dogKindEditText.setClickable(true);
        dogKindEditText.setOnClickListener(view -> startDogsKindsListActivity(dog));

        addDogButton.setOnClickListener(view -> {
            // если порода собаки еще не установлена, то переход в список пород
            if (dog.getDogKind() == null) {
                startDogsKindsListActivity(dog);
            } else {
                // добавляем собачку в БД и возвращаем её уже с сгенерированным dogId в модель dog
                dog = DataRepository.get().addDog(dog);
                owner.addDog(dog);
                backToDogListActivity();
            }
        });

        photoDogImageView.setOnClickListener(view -> {
            if (hasPermission(Manifest.permission.CAMERA) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                startCameraOrPreview(photoFilePathString);
            } else if (!(hasPermission(Manifest.permission.CAMERA) || hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
                requestPermissions();
            } else if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissionWithRationale(Manifest.permission.READ_EXTERNAL_STORAGE,
                        R.string.need_storage_access_snackbar,
                        STORAGE_PERMISSIONS, PERMISSION_STORAGE_REQUEST);
            } else if (!hasPermission(Manifest.permission.CAMERA)) {
                requestPermissionWithRationale(Manifest.permission.CAMERA,
                        R.string.need_camera_access_snackbar,
                        CAMERA_PERMISSIONS, PERMISSION_CAMERA_REQUEST);
            }
        });
    }

    public boolean hasPermission(String permission) {
        if (ContextCompat.checkSelfPermission(this,
                permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    private void requestPermissions() {
        String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSIONS_REQUEST);
        }
    }

    private void requestPermission(String[] permissions, int permission_request_int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, permission_request_int);
        }
    }

    private void showNoPermissionSnackbarSettings(int snackBarStringResId, int toastStringResId, int settingPermissionRequest) {
        Snackbar.make(rootLayout, snackBarStringResId, Snackbar.LENGTH_LONG)
                .setDuration(5000)
                .setAction(R.string.settings_snackbar, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openSettings(settingPermissionRequest);
                        Toast.makeText(getApplicationContext(),
                                toastStringResId,
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .show();
    }

    public void openSettings(int permissionRequest) {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, permissionRequest);
    }

    private void showSnackbarAndRequestPermission(int snackBarStringResId, String[] permissions, int permission_request_int) {
        Snackbar.make(rootLayout, snackBarStringResId, Snackbar.LENGTH_LONG)
                .setAction(R.string.grant_permission_snackbar, new View.OnClickListener() { // R.string.grant_permission_snackbar
                    @Override
                    public void onClick(View v) {
                        requestPermission(permissions, permission_request_int);
                    }
                })
                .show();
    }

    public void requestPermissionWithRationale(String stringPermission, int stringResIdSnackbar,
                                               String[] arrayPermissions, int requestPermission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, stringPermission)) {
            showSnackbarAndRequestPermission(stringResIdSnackbar, arrayPermissions, requestPermission);
        } else {
            requestPermission(arrayPermissions, requestPermission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (hasPermission(Manifest.permission.CAMERA) && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            startCameraOrPreview(photoFilePathString);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


                // если оба false
                if (!(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)))) {
                    Toast.makeText(this, "Доступ к камере и хранилищу отклонён", Toast.LENGTH_LONG).show();
                } else {showNoPermissionSnackbarSettings(R.string.need_both_access_snackbar, // "Программе нужен доступ "
                        R.string.open_settings_grant_access_toast, //"открываем настройки"
                        PERMISSIONS_REQUEST);
                }


                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Доступ к ХРАНИЛИЩУ отклонён", Toast.LENGTH_SHORT).show(); // R.string.storage_permission_denied_toast
                } else {
                    showNoPermissionSnackbarSettings(R.string.storage_not_granted_snackbar,
                            R.string.open_settings_grant_storage_toast,
                            PERMISSION_STORAGE_REQUEST);
                }

                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "Камера была отклоненнннна", Toast.LENGTH_SHORT).show(); // R.string.camera_permission_denied_toast
                } else {
                    showNoPermissionSnackbarSettings(R.string.storage_not_granted_snackbar,
                            R.string.open_settings_grant_camera_toast,
                            PERMISSION_CAMERA_REQUEST);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_DOG_KIND) {
            if (resultCode == RESULT_OK) {
                dogKind = intent.getParcelableExtra(EXTRA_SELECTED_KIND);
                setDogKindTitleAndImage();
            }
        } else if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                Uri uri = FileProvider.getUriForFile(this,
                        "com.andersen.dogsapp.fileprovider", photoFile);
                setFilePathString();
                dog.setDogImageString(photoFilePathString);
                updatePhotoView();
                hasPhoto = true;
            } else {
                hasPhoto = false;
                Log.d(TAG, "REQUEST_CAMERA RESULT WAS NOT OK");
            }
        } else if (requestCode == REQUEST_CODE_PREVIEW) {
            if (resultCode == RESULT_OK) {
                photoFilePathString = intent.getStringExtra(EXTRA_FILE_PATH);
            } else {
                Log.d(TAG, "REQUEST_CODE_PREVIEW RESULT WAS NOT OK");
            }
            updatePhotoView();
            dog.setDogImageString(photoFilePathString);
        } else if (requestCode == PERMISSION_STORAGE_REQUEST) {
            Log.d(TAG, "PERMISSION_STORAGE_REQUEST");
            Log.d(TAG, " 333");
            startCameraOrPreview(photoFilePathString);
        }
    }

    private void startCameraOrPreview(String photoFilePathString) {
        if (hasPhoto) {
            startPhotoPreviewActivity(photoFilePathString);
        } else {
            startCamera();
        }
    }

    private void startCamera() {
        photoFile = getPhotoFile(this);
        Uri uri = FileProvider.getUriForFile(this,
                "com.andersen.dogsapp.fileprovider", photoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(captureImage, REQUEST_CAMERA);
    }

    private File getPhotoFile(Context context) {
        File filesDir = context.getFilesDir();
        String timeStamp = String.valueOf("dog_" + System.currentTimeMillis()) + ".jpg";
        return new File(filesDir, timeStamp);
    }

    private void startPhotoPreviewActivity(String photoFilePathString) {
        Intent intent = new Intent(getApplicationContext(), DogPhotoPreviewActivity.class);
        intent.putExtra(EXTRA_FILE_PATH, photoFilePathString);
        startActivityForResult(intent, REQUEST_CODE_PREVIEW);
    }

    private void startDogsKindsListActivity(Dog dog) {
        Intent intent = new Intent(getApplicationContext(), DogsKindsListActivity.class);
        intent.putExtra(EXTRA_DOG_FOR_KIND, dog);
        startActivityForResult(intent, REQUEST_CODE_DOG_KIND);
        Toast.makeText(getApplicationContext(), R.string.specify_kind_please_toast, Toast.LENGTH_SHORT).show();
    }

    private void backToDogListActivity() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_OWNER, owner);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setFilePathString() {
        if (photoFile != null || photoFile.exists()) {
            photoFilePathString = photoFile.getPath();
        }
    }

    private void updatePhotoView() {
        Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, this);
        photoDogImageView.setImageBitmap(bitmap);
    }

    private void initViews() {
        addDogButton = findViewById(R.id.add_dog_button);
        photoDogImageView = findViewById(R.id.dog_photo_imageview);
        dogNameEditText = findViewById(R.id.dog_name_edittext);
        dogKindEditText = findViewById(R.id.dog_kind_edittext);
        dogAgeEditText = findViewById(R.id.dog_age_edittext);
        dogTallEditText = findViewById(R.id.dog_tall_edittext);
        dogWeightEditText = findViewById(R.id.dog_weight_edittext);
    }

    private void createDogModelWithInputDatas() {
        String dogName = dogNameEditText.getText().toString();
        int dogAge = Integer.parseInt(dogAgeEditText.getText().toString());
        int dogTall = Integer.parseInt(dogTallEditText.getText().toString());
        int dogWeight = Integer.parseInt(dogWeightEditText.getText().toString());
        // вытащили owner'a из EXTRA и добавляем его в модель Dog
        dog = new Dog(dogName, owner, dogAge, dogTall, dogWeight);
    }

    public void setDogKindTitleAndImage() {
        String dogKindString = dogKind.getKind();
        dog.setDogKind(dogKindString);
        dogKindEditText.setText(dogKindString);
        if (!hasPhoto) {
            dog.setDogImageString(dogKind.getImageString());
        }
    }

    // Заполнение всех полей
    private void testingFillEditText() {
        dogNameEditText.setText(SomeDog.get().name());
        dogAgeEditText.setText("" + SomeDog.get().age());
        dogWeightEditText.setText("" + SomeDog.get().weight());
        dogTallEditText.setText("" + SomeDog.get().tall());
    }
}
