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
import android.widget.EditText;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.camera.PictureUtils;
import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.entities.Owner;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.Toast;

import com.andersen.dogsapp.dogs.ui.DogToolBar;
import com.andersen.dogsapp.dogs.ui.dogskinds.DogsKindsListActivity;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;


import java.io.File;
import java.util.List;

import static com.andersen.dogsapp.dogs.ui.dogs.DogsListActivity.EXTRA_OWNER;
import static com.andersen.dogsapp.dogs.ui.dogskinds.DogsKindsListActivity.EXTRA_SELECTED_KIND;

public class NewDogFormActivity extends AppCompatActivity {
    public static final String TAG = "#";

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

    private Button takePhotoButton;
    private ImageView photoDogImageView;
    private File photoFile;
    private DogKind dogKind;
    private String photoFilePathString;
    private boolean hasPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dog_form);
        hasPhoto = false;

        Toolbar toolbar = DogToolBar.init(this, R.string.toolbar_title_add_dog);
        setSupportActionBar(toolbar);

        owner = getIntent().getParcelableExtra(EXTRA_NEW_OWNER);

        initViews();
        testingFillEditText();
//        createDogModelFromInputDatas();
        String dogName = dogNameEditText.getText().toString();
        int dogAge = Integer.parseInt(dogAgeEditText.getText().toString());
        int dogTall = Integer.parseInt(dogTallEditText.getText().toString());
        int dogWeight = Integer.parseInt(dogWeightEditText.getText().toString());
        // вытащили owner'a из EXTRA и добавляем его с остальными данными в модель
        dog = new Dog(dogName, owner, dogAge, dogTall, dogWeight);
        Log.d(TAG, "" + dog.getDogId());

        dogKindEditText.setFocusable(false);
        dogKindEditText.setClickable(true);
        dogKindEditText.setOnClickListener(view -> startDogsKindsListActivity(dog));

        photoDogImageView = findViewById(R.id.dog_photo_imageview);
        takePhotoButton = findViewById(R.id.take_photo_button);
        photoFile = getPhotoFile(this);
        photoDogImageView.setOnClickListener(view -> {
            if (hasPhoto) {
                startPhotoPreviewActivity(photoFilePathString);
            } else {
                startCamera();
            }
        });

        addDogButton.setOnClickListener(view -> {
            // если порода собаки еще не установлена, то отправляемся в DogsKindsListActivity
            if (dog.getDogKind() == null) {
                startDogsKindsListActivity(dog);
            } else {
                // добавляем собачку в БД
                // и возвращаем её уже с сгенерированным dogId в модель dog
                dog = DataRepository.get().addDog(dog);
                owner.addDog(dog);
                backToDogListActivity();
            }
        });
    }

    private void startCamera() {
        Uri uri = FileProvider.getUriForFile(this,
                "com.andersen.dogsapp.fileprovider", photoFile);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_DOG_KIND) {
            if (resultCode == RESULT_OK) {
                dogKind = data.getParcelableExtra(EXTRA_SELECTED_KIND);

                String dogKindString = dogKind.getKind();
                dog.setDogKind(dogKindString);
                dogKindEditText.setText(dogKindString);

                if (!hasPhoto) {
                    dog.setDogImageString(dogKind.getImageString());
                }
            } // else { Log.d(TAG, "REQUEST_CODE_DOG_KIND. RESULT was NOT"); }
        } else if (requestCode == REQUEST_CAMERA) {
            Uri uri = FileProvider.getUriForFile(this,
                    "com.andersen.dogsapp.fileprovider", photoFile);
            this.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            updatePhotoView();
            hasPhoto = true; // TODO handle in onSaveInstanceState !!!
//            Log.d(TAG, "hasPhoto = "+hasPhoto);
        } // else { Log.d(TAG, "REQUEST_CAMERA. RESULT was NOT"); }
    }

    private void updatePhotoView() {
        if (photoFile != null || photoFile.exists()) {
            photoFilePathString = photoFile.getPath();
            // назначить собачке фотку
            dog.setDogImageString(photoFilePathString);
//            Log.d(TAG, "dir#" + photoFilePathString);
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, this);
            photoDogImageView.setImageBitmap(bitmap);
        }
    }

    private void initViews() {
        addDogButton = findViewById(R.id.add_dog_button);
        dogNameEditText = findViewById(R.id.dog_name_edittext);
        dogKindEditText = findViewById(R.id.dog_kind_edittext);
        dogAgeEditText = findViewById(R.id.dog_age_edittext);
        dogTallEditText = findViewById(R.id.dog_tall_edittext);
        dogWeightEditText = findViewById(R.id.dog_weight_edittext);
    }

    // TEST EditTexts' FILLING
    private void testingFillEditText() {
        dogNameEditText.setText(SomeDog.get().name());
        dogAgeEditText.setText("" + SomeDog.get().age());
        dogWeightEditText.setText("" + SomeDog.get().weight());
        dogTallEditText.setText("" + SomeDog.get().tall());
    }

    private void createDogModelFromInputDatas() {
    }
}
