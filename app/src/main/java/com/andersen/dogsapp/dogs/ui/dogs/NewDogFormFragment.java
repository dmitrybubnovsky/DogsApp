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
import com.andersen.dogsapp.dogs.data.interfaces.IChangeFragmentListener;
import com.andersen.dogsapp.dogs.data.repositories.DogsRepository;
import com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity;
import com.andersen.dogsapp.dogs.ui.breeds.BreedsListFragment;
import com.andersen.dogsapp.dogs.ui.testing_edittext_filling.SomeDog;
import com.andersen.dogsapp.dogs.utils.NetworkManager;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity.BACK_STACK_ROOT_TAG;
import static com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity.BREEDS_TAG;
import static com.andersen.dogsapp.dogs.ui.breeds.BreedsListFragment.CALLED_FOR_SELECT_KEY;
import static com.andersen.dogsapp.dogs.ui.breeds.BreedsListFragment.EXTRA_SELECTED_KIND;
import static com.andersen.dogsapp.dogs.ui.dogs.DogPhotoPreviewFragment.PREVIEW_TAG;

public class NewDogFormFragment extends Fragment {
    public static final String NEW_DOG_ARG = "new_dog_arg";
    public static final String BREED_ARG = "breed_arg";
    public static final String NEW_DOG_TAG = "new_dog_tag";
    private static final String TAG = "#";
    private static final String EXTRA_FILE_PATH = "extra_file_path";
    private static final int REQUEST_CAMERA = 201;
    private static final int REQUEST_CODE_DOG_KIND = 202;
    private static final int REQUEST_CODE_PREVIEW = 203;
    private static final int PERMISSIONS_REQUEST = 115;
    private static final int STORAGE_REQUEST_PERMISSION = 114;
    private static final int CAMERA_REQUEST_PERMISSION = 116;
    private static final int SNACKBAR_DURATION = 3000;
    private static final int HANDLER_DELAY = 3000;
    private static final String[] CAMERA_PERMISSIONS = {Manifest.permission.CAMERA};
    private static final String[] STORAGE_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    private IDogFinishedFragmentListener finishedDogListener;
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
    private IChangeFragmentListener fragmentNameListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        finishedDogListener = (MainAppDescriptionActivity) context;
        fragmentNameListener = (MainAppDescriptionActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        final Bundle bundleArguments = getArguments();
        if (bundleArguments != null) {
            readBundle(bundleArguments);
        }
    }

    private void readBundle(final Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey(NEW_DOG_ARG)) {
                owner = bundle.getParcelable(NEW_DOG_ARG);
            }
            if (bundle.containsKey(BREED_ARG)) {
                dogKind = bundle.getParcelable(BREED_ARG);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_new_dog_form, container, false);

        hasPhoto = false;

        initViews(view);
        testingFillEditText();

        createDogModelWithInputDatas();

        dogKindEditText.setFocusable(false);
        dogKindEditText.setClickable(true);
        dogKindEditText.setOnClickListener(view1 -> startDogsKindsListActivity());

        addDogButton.setOnClickListener(view1 -> {
            // если порода собаки еще не установлена, то переход в список пород
            if (dog.getDogKind() == null) {
                startDogsKindsListActivity();
            } else {
                // добавляем собачку в БД и возвращаем её уже с сгенерированным dogId в модель dog
                dog = DogsRepository.get().addDog(dog);
                owner.addDog(dog);
                // переход в DogsListFragment
                finishedDogListener.onDogFinishedListener(owner);
            }
        });
        photoDogImageView.setOnClickListener(view_ -> checkPermissions());
        return view;
    }

    private void checkPermissions() {
        // если есть все разрешения запускаем камеру
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

    @Override
    public void onResume() {
        super.onResume();
        fragmentNameListener.onFragmentChangeListener(R.string.title_add_dog);
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
            startPhotoPreviewActivity(photoFilePathString);
        } else {
            startCamera();
        }
    }

    private void startCamera() {
        Log.d(TAG, "CAMERA startActivityForResult(captureImage, REQUEST_CAMERA);");
        photoFile = getPhotoFile(getActivity());
        Uri uri = FileProvider.getUriForFile(getActivity(),
                "com.andersen.dogsapp.fileprovider", photoFile);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(captureImage, REQUEST_CAMERA);
    }

    private void startPhotoPreviewActivity(String photoFilePathString) {
        DogPhotoPreviewFragment dialogFragment = DogPhotoPreviewFragment.newInstance(photoFilePathString);

        // назначаем текущий фрагмент целевым для DogPhotoPreviewFragment'a
        dialogFragment.setTargetFragment(NewDogFormFragment.this, REQUEST_CODE_PREVIEW);
        dialogFragment.show(((MainAppDescriptionActivity) getActivity()).fragManager, PREVIEW_TAG);
    }

    private File getPhotoFile(Context context) {
        File filesDir = context.getFilesDir();
        String timeStamp = String.valueOf("dog_" + System.currentTimeMillis()) + ".jpg";
        return new File(filesDir, timeStamp);
    }

    private void startDogsKindsListActivity() {
        // Если сети нет, то список пород НЕ открываем
        if (!NetworkManager.hasNetWorkAccess(getActivity())) {
            Toast.makeText(getActivity(), R.string.no_network_toast, Toast.LENGTH_SHORT).show();
        } else {
            Bundle bundleArg = new Bundle();
            bundleArg.putBoolean(CALLED_FOR_SELECT_KEY, true);
            Fragment fragment = Fragment.instantiate(getActivity(), BreedsListFragment.class.getName(), bundleArg);
            fragment.setTargetFragment(NewDogFormFragment.this, REQUEST_CODE_DOG_KIND);
            ((MainAppDescriptionActivity) getActivity()).fragManager.beginTransaction()
                    .add(R.id.host_fragment_container, fragment, BREEDS_TAG)
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit();
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
    public void onDetach() {
        super.onDetach();
        fragmentNameListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_DOG_KIND:
                    dogKind = data.getParcelableExtra(EXTRA_SELECTED_KIND);
                    setDogKindTitleAndImage();
                    break;
                case REQUEST_CAMERA:
                    setFilePathString();
                    dog.setDogImageString(photoFilePathString);
                    updatePhotoView();
                    hasPhoto = true;
                    break;
                case REQUEST_CODE_PREVIEW:
                    photoFilePathString = data.getStringExtra(EXTRA_FILE_PATH);
                    dog.setDogImageString(photoFilePathString);
                    updatePhotoView();
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

    public interface IDogFinishedFragmentListener<T> {
        void onDogFinishedListener(T t);
    }
}
