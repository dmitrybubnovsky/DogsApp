package com.andersen.dogsapp.dogs.ui.dogs;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.camera.PictureUtils;
import com.andersen.dogsapp.dogs.data.repositories.DataRepository;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.dogskinds.DogsKindsListActivity;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;
import com.andersen.dogsapp.dogs.utils.NetworkManager;

import java.io.File;

import static com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity.EXTRA_OWNER;
import static com.andersen.dogsapp.dogs.ui.dogskinds.DogsKindsListActivity.EXTRA_SELECTED_KIND;

public class NewDogFormActivity extends AppCompatActivity {
    private static final String TAG = "#";
    private static final int PERMISSIONS_REQUEST = 115;
    private static final int STORAGE_REQUEST_PERMISSION = 114;
    private static final int CAMERA_REQUEST_PERMISSION = 116;
    private static final int SNACKBAR_DURATION = 3000;
    private static final int HANDLER_DELAY = 3000;
    private static final String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA};
    private static final String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String EXTRA_NEW_OWNER = "new owner dog";
    public static final String EXTRA_FILE_PATH = "extra_file_path";
    public static final int REQUEST_CAMERA = 201;
    public static final int REQUEST_CODE_DOG_KIND = 202;
    public static final int REQUEST_CODE_PREVIEW = 203;
    private final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private EditText dogNameEditText;
    private EditText dogKindEditText;
    private EditText dogAgeEditText;
    private EditText dogTallEditText;
    private EditText dogWeightEditText;
    private Button addDogButton;
    private Owner owner;
    private Dog dog;
    private DogKind dogKind;
    private ImageView photoDogImageView;
    private File photoFile;
    private String photoFilePathString;
    private boolean hasPhoto;
    private View rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog_form);

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_dog);
        setSupportActionBar(toolbar);

        hasPhoto = false;
        owner = getIntent().getParcelableExtra(EXTRA_NEW_OWNER);

        initViews();
        testingFillEditText();
        createDogModelWithInputDatas();

        dogKindEditText.setFocusable(false);
        dogKindEditText.setClickable(true);
        dogKindEditText.setOnClickListener(view -> startDogsKindsListActivity());

        addDogButton.setOnClickListener(view -> {
            // если порода собаки еще не установлена, то переход в список пород
            if (dog.getDogKind() == null) {
                startDogsKindsListActivity();
            } else {
                // добавляем собачку в БД и возвращаем её уже с сгенерированным dogId в модель dog
                dog = DataRepository.get().addDog(dog);
                owner.addDog(dog);
                backToDogListActivity();
            }
        });

        photoDogImageView.setOnClickListener(view -> checkPermissions());
    }

    private void checkPermissions() {
        // если все ок запускаем камеру
        if (hasBothPermissions()) {
            startCameraOrPreview(photoFilePathString);
        } // если отсутствуют оба разрешения
        else if (hasNoAnyPermission()) {
            requestPermission(new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST);
        } else if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            requestPermissionWithRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    R.string.need_storage_access_snackbar,
                    STORAGE_PERMISSIONS, STORAGE_REQUEST_PERMISSION);
        } else if (!hasPermission(Manifest.permission.CAMERA)) {
            requestPermissionWithRationale(Manifest.permission.CAMERA,
                    R.string.need_camera_access_snackbar,
                    CAMERA_PERMISSIONS, CAMERA_REQUEST_PERMISSION);
        }
    }

    private boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String[] permissions, int permission_request_int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, permission_request_int);
        }
    }

    private void showNoPermissionSnackbarSettings(int snackBarStringResId, int settingPermissionRequest) {
        Snackbar.make(rootLayout, snackBarStringResId, Snackbar.LENGTH_LONG)
                .setDuration(SNACKBAR_DURATION)
                .setAction(R.string.settings_snackbar, view -> openSettings(settingPermissionRequest))
                .show();
    }

    private void openSettings(int permissionRequest) {
        startActivityForResult(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName())), permissionRequest);
    }

    private void showSnackbarAndRequestPermission(int snackBarStringResId, String[] permissions,
                                                  int permission_request_int) {
        Snackbar.make(rootLayout, snackBarStringResId, Snackbar.LENGTH_SHORT)
                .setDuration(SNACKBAR_DURATION)
                .setAction(R.string.grant_permission_snackbar,
                        view -> requestPermission(permissions, permission_request_int))
                .show();
    }

    private void requestPermissionWithRationale(String stringPermission, int stringResIdSnackbar,
                                                String[] arrayPermissions, int requestPermission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, stringPermission)) {
            showSnackbarAndRequestPermission(stringResIdSnackbar, arrayPermissions, requestPermission);
        } else {
            requestPermission(arrayPermissions, requestPermission);
        }
    }

    private boolean hasBothPermissions() {
        return hasPermission(Manifest.permission.CAMERA)
                && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private boolean hasNoAnyPermission() {
        return !(hasPermission(Manifest.permission.CAMERA)
                || hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (hasBothPermissions()) {
            startCameraOrPreview(photoFilePathString);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (hasNoAnyPermission()
                        && !(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))) {
                    showNoPermissionSnackbarSettings(R.string.storage_not_granted_snackbar,
                            STORAGE_REQUEST_PERMISSION);
                    new Handler().postDelayed(() ->
                            showNoPermissionSnackbarSettings(R.string.camera_not_granted_snackbar,
                                    CAMERA_REQUEST_PERMISSION), HANDLER_DELAY);
                } else if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        showNoPermissionSnackbarSettings(R.string.storage_not_granted_snackbar,
                                STORAGE_REQUEST_PERMISSION);
                    }
                } else if (!hasPermission(Manifest.permission.CAMERA)) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        showNoPermissionSnackbarSettings(R.string.camera_not_granted_snackbar,
                                CAMERA_REQUEST_PERMISSION);
                    }
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_DOG_KIND:
                    dogKind = intent.getParcelableExtra(EXTRA_SELECTED_KIND);
                    setDogKindTitleAndImage();
                    break;
                case REQUEST_CAMERA:
                    setFilePathString();
                    dog.setDogImageString(photoFilePathString);
                    updatePhotoView();
                    hasPhoto = true;
                    break;
                case REQUEST_CODE_PREVIEW:
                    photoFilePathString = intent.getStringExtra(EXTRA_FILE_PATH);
                    break;
                case PERMISSIONS_REQUEST:
                case STORAGE_REQUEST_PERMISSION:
                case CAMERA_REQUEST_PERMISSION:
                    if (hasPermission(Manifest.permission.CAMERA)
                            && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        startCameraOrPreview(photoFilePathString);
                    }
                    break;
                default:
                    updatePhotoView();
                    dog.setDogImageString(photoFilePathString);
                    break;
            }
        } else {
            hasPhoto = false;
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

    private void startDogsKindsListActivity() {
        // Если сети нет, то список пород НЕ открываем
        if (!NetworkManager.hasNetWorkAccess(this)) {
            Toast.makeText(this, R.string.no_network_toast, Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(getApplicationContext(), DogsKindsListActivity.class);
            startActivityForResult(intent, REQUEST_CODE_DOG_KIND);
            Toast.makeText(getApplicationContext(), R.string.specify_kind_please_toast, Toast.LENGTH_SHORT).show();
        }
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
        rootLayout = findViewById(R.id.new_dog_form_root);
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
            dog.setDogImageString(dogKind.getUriImageString());
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
