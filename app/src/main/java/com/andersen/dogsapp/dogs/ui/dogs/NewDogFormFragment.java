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
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.camera.PictureUtils;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.repositories.DogsRepository;
import com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity;
import com.andersen.dogsapp.dogs.ui.breeds.BreedsListFragment;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;
import com.andersen.dogsapp.dogs.utils.NetworkManager;

import java.io.File;

public class NewDogFormFragment extends Fragment {
    public static final String TAG = "#";
    public static final String NEW_DOG_ARG = "new_dog_arg";
    public static final String BREED_ARG = "breed_arg";
    public static final String NEW_DOG_TAG = "new_dog_tag";
    public static final String EXTRA_FILE_PATH = "extra_file_path";
    public static final int REQUEST_CAMERA = 201;
    public static final int REQUEST_CODE_DOG_KIND = 202;
    public static final int REQUEST_CODE_PREVIEW = 203;
    private static final int PERMISSIONS_REQUEST = 115;
    private static final int STORAGE_REQUEST_PERMISSION = 114;
    private static final int CAMERA_REQUEST_PERMISSION = 116;
    private static final int SNACKBAR_DURATION = 3000;
    private static final int HANDLER_DELAY = 3000;
    private static final String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA};
    private static final String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
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

    ISetBreedFragmentListener breedListener;
    public interface ISetBreedFragmentListener<T> {
        void onSetBreedListener(T t);
    }

    IDogFinishedFragmentListener finishedDogListener;
    public interface IDogFinishedFragmentListener<T> {
        void onDogFinishedListener(T t);
    }

    public static Fragment newInstance(Owner owner) {
        final NewDogFormFragment newDogFragment = new NewDogFormFragment();
        final Bundle bundleArgs = new Bundle();
        bundleArgs.putParcelable(NEW_DOG_ARG, owner);
        newDogFragment.setArguments(bundleArgs);
        return newDogFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        breedListener = (MainAppDescriptionActivity) context;
        finishedDogListener = (MainAppDescriptionActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        final Bundle bundleArguments = getArguments();
        if (bundleArguments == null) {
            Log.d("", "NewDogFormFragment: bundleArguments == null || !bundleArguments.containsKey(OWNERS_ARG)");
        } else {
            readBundle(bundleArguments);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "BreedsFragment onDestroy");

    }

    private void readBundle(final Bundle bundle) {
        if (bundle != null) {
            if(bundle.containsKey(NEW_DOG_ARG)){
                owner = bundle.getParcelable(NEW_DOG_ARG);
            }
            if (bundle.containsKey(BREED_ARG)) {
                dogKind = bundle.getParcelable(BREED_ARG);
            }
        } else { Log.d(TAG, "NewDogFragment bundle = null"); } // TODO delete this line
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_new_dog_form, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar_dogs_app);
        if (toolbar != null) {
            ((MainAppDescriptionActivity) getActivity()).setSupportActionBar(toolbar);
//            toolbar.setTitle(R.string.title_add_dog);
        }

        hasPhoto = false;

        initViews(view);
        testingFillEditText();

        createDogModelWithInputDatas();

        if((owner != null) && (dogKind != null)){
            setDogKindTitleAndImage();
        }

        dogKindEditText.setFocusable(false);
        dogKindEditText.setClickable(true);
        dogKindEditText.setOnClickListener(view1 -> startDogsKindsListActivity());

        addDogButton.setOnClickListener(view1 -> {
            // если порода собаки еще не установлена, то переход в список пород
            if (dog.getDogKind() == null) {
                startDogsKindsListActivity();
                Log.d(TAG, "------------- dog.getDogKind() starts breeedslist");

            } else {
                // добавляем собачку в БД и возвращаем её уже с сгенерированным dogId в модель dog
                dog = DogsRepository.get().addDog(dog);
                owner.addDog(dog);
                Log.d(TAG, "------------- dog kind "+dog.getDogKind()+" starts onDogFinishedListener");
//                getActivity().getSupportFragmentManager().popBackStack();
                finishedDogListener.onDogFinishedListener(owner);

//                backToDogListActivity();
            }
        });

        photoDogImageView.setOnClickListener(view_ -> checkPermissions());

        return view;
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
        return ContextCompat.checkSelfPermission(getActivity(), permission)
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
                Uri.parse("package:" + getActivity().getPackageName())), permissionRequest);
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
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), stringPermission)) {
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

    private void startCameraOrPreview(String photoFilePathString) {
        if (hasPhoto) {
//            startPhotoPreviewActivity(photoFilePathString);
        } else {
//            startCamera();
        }
    }

    private void startCamera() {
        photoFile = getPhotoFile(getActivity());
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

    private void startPhotoPreviewActivity(String photoFilePathString) {
//        Intent intent = new Intent(getApplicationContext(), DogPhotoPreviewActivity.class);
//        intent.putExtra(EXTRA_FILE_PATH, photoFilePathString);
//        startActivityForResult(intent, REQUEST_CODE_PREVIEW);
    }

    private void startDogsKindsListActivity() {
        // Если сети нет, то список пород НЕ открываем
        if (!NetworkManager.hasNetWorkAccess(getActivity())) {
            Toast.makeText(getActivity(), R.string.no_network_toast, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "no network");
        }
        else {

            Fragment frag = Fragment.instantiate(getActivity(), BreedsListFragment.class.getName());
            frag.setTargetFragment(NewDogFormFragment.this, REQUEST_CODE_DOG_KIND);
            ((MainAppDescriptionActivity)getActivity()).startBreedsFromTargetFragment(frag);

//            getActivity().getSupportFragmentManager().popBackStack();
//            breedListener.onSetBreedListener(owner);
//            startActivityForResult(new Intent(getApplicationContext(), BreedsListActivity.class), REQUEST_CODE_DOG_KIND);
        }
    }

    private void setFilePathString() {
        if (photoFile != null || photoFile.exists()) {
            photoFilePathString = photoFile.getPath();
        }
    }

    private void updatePhotoView() {
        Bitmap bitmap = PictureUtils.getScaledBitmap(photoFilePathString, getActivity());
        photoDogImageView.setImageBitmap(bitmap);
    }

    private void initViews(View view) {
        rootLayout = view.findViewById(R.id.new_dog_form_root_frag);
        addDogButton = view.findViewById(R.id.add_dog_button_frag);
        photoDogImageView = view.findViewById(R.id.dog_photo_imageview_frag);
        dogNameEditText = view.findViewById(R.id.dog_name_edittext_frag);
        dogKindEditText = view.findViewById(R.id.dog_kind_edittext_frag);
        dogAgeEditText = view.findViewById(R.id.dog_age_edittext_frag);
        dogTallEditText = view.findViewById(R.id.dog_tall_edittext_frag);
        dogWeightEditText = view.findViewById(R.id.dog_weight_edittext_frag);
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


    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "BreedsFragment onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "BreedsFragment  onDetach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "BreedsFragment  onPause");
    }

}






//    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
//
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_CODE_DOG_KIND:
//                    dogKind = intent.getParcelableExtra(EXTRA_SELECTED_KIND);
//                    setDogKindTitleAndImage();
//                    break;
//                case REQUEST_CAMERA:
//                    setFilePathString();
//                    dog.setDogImageString(photoFilePathString);
//                    updatePhotoView();
//                    hasPhoto = true;
//                    break;
//                case REQUEST_CODE_PREVIEW:
//                    photoFilePathString = intent.getStringExtra(EXTRA_FILE_PATH);
//                    break;
//                case PERMISSIONS_REQUEST:
//                case STORAGE_REQUEST_PERMISSION:
//                case CAMERA_REQUEST_PERMISSION:
//                    if (hasPermission(Manifest.permission.CAMERA)
//                            && hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                        startCameraOrPreview(photoFilePathString);
//                    }
//                    break;
//                default:
//                    updatePhotoView();
//                    dog.setDogImageString(photoFilePathString);
//                    break;
//            }
//        } else {
//            hasPhoto = false;
//        }
//    }






//    private void backToDogListActivity() {
//        Intent intent = new Intent();
//        intent.putExtra(EXTRA_OWNER, owner);
//        setResult(RESULT_OK, intent);
//        finish();
//    }