package com.andersen.dogsapp.dogs.ui.dogs;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andersen.dogsapp.R;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.interfaces.IChangeFragmentListener;
import com.andersen.dogsapp.dogs.ui.DogImageUtils;
import com.andersen.dogsapp.dogs.ui.MainAppDescriptionActivity;

public class DogsInfoFragment extends Fragment {
    public static final String TAG = "#";
    public static final String DOG_INFO_ARG = "dog_info_arg";
    public static final String DOG_INFO_TAG = "dog_info_tag";
    private MediaPlayer mediaPlayer;
    private Dog dog;
    private IChangeFragmentListener fragmentNameListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.dog_sound);

        readArguments();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentNameListener = (MainAppDescriptionActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = inflater.inflate(R.layout.fragment_dogs_info, container, false);
        initViews(getActivity(), view, dog, mediaPlayer);

        return view;
    }

    private void readArguments() {
        final Bundle bundleArguments = getArguments();
        if (bundleArguments == null || !bundleArguments.containsKey(DOG_INFO_ARG)) {
        } else {
            dog = bundleArguments.getParcelable(DOG_INFO_ARG);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentNameListener.onFragmentChangeListener(R.string.title_dog_description);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentNameListener = null;
    }

    private void initViews(Context context, View view, Dog dog, MediaPlayer mediaPlayer) {
        ImageView dogsImageView = view.findViewById(R.id.dog_imageview_frag);
        String dogImageString = dog.getDogImageString();
        if (dogImageString.contains("_doggy_dogg.jpg")) {
            dogsImageView.setImageDrawable(DogImageUtils.getDogImage(context, dogImageString));
        } else {
            dogsImageView.setImageURI(Uri.parse(dogImageString));
        }

        TextView dogNameTextView = view.findViewById(R.id.dog_name_textview_frag);
        dogNameTextView.setText(dog.getDogName());

        TextView kindDogTextView = view.findViewById(R.id.kind_dog_textview_frag);
        kindDogTextView.setText(dog.getDogKind());

        TextView dogAgeTextView = view.findViewById(R.id.dog_age_textview_frag);
        dogAgeTextView.setText("" + dog.getDogAge() + " " + getResources().getString(R.string.age_months_measure));

        TextView dogTallTextView = view.findViewById(R.id.dog_tall_textview_frag);
        dogTallTextView.setText("" + dog.getDogTall() + " " + getResources().getString(R.string.tall_measure));

        TextView dogWeightTextView = view.findViewById(R.id.dog_weight_textview_frag);
        dogWeightTextView.setText("" + dog.getDogWeight() + " " + getResources().getString(R.string.weight_measure));

        Button btnVoice = view.findViewById(R.id.button_voice_frag);
        btnVoice.setOnClickListener(view1 -> playSound(mediaPlayer));
    }

    private void playSound(MediaPlayer mediaPlayer) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.start();
    }
}
