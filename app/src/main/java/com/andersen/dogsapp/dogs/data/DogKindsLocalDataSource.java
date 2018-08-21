package com.andersen.dogsapp.dogs.data;

import android.content.Context;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.IWebCallback;
import com.andersen.dogsapp.dogs.data.web.WebBreedsDataSource;
import com.andersen.dogsapp.dogs.ui.DogImageUtils;

import java.util.ArrayList;
import java.util.List;

public class DogKindsLocalDataSource {
    public static final String TAG = "#";

    private static DogKindsLocalDataSource instance;
    private final String[] TITLES = new String[]{"afghan_hound", "american_bulldog", "american_foxhound",
            "australiancattle", "belgiantervuren", "berger_picard", "bolognese", "bullterrier", "chesapeake", "chinook", "dogoargentino", "english_coonhound", "german_shepherd",
            "icelandicsheepdog", "komondor", "mudi", "munsterlander_pointer", "pharaohhound", "pocket_beagle",
            "pug", "saint_bernard", "scottishterrier", "shepherd", "siberian_husky",
            "staffordshire", "standard_schnauzer", "water_spaniel"};

    private final String[] IMAGE_FILES = new String[]{"afghan_hound", "american_bulldog", "american_foxhound",
            "australiancattle", "belgiantervuren", "berger_picard", "bolognese", "bullterrier", "chesapeake", "chinook", "dogoargentino", "english_coonhound", "german_shepherd",
            "icelandicsheepdog", "komondor", "mudi", "munsterlander_pointer", "pharaohhound", "pocket_beagle",
            "pug", "saint_bernard", "scottishterrier", "shepherd", "siberian_husky",
            "staffordshire", "standard_schnauzer", "water_spaniel"};

    private final List<DogKind> dogKindsList;

    private DogKindsLocalDataSource() {
        dogKindsList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            DogKind dogKind = new DogKind(TITLES[i], ""); // IMAGE_FILES[i]
            dogKindsList.add(dogKind);
        }
    }

    public static DogKindsLocalDataSource getInstance(){
        if(instance == null){
            instance = new DogKindsLocalDataSource();
        }
        return instance;
    }

    public void getDogsKinds(IWebCallback<List<DogKind>> callback) {
        callback.onWebCallback(dogKindsList);
    }

//    public void getBreedsImage (Context context, String breedString, IWebCallback<String> webCallback){
    public void getBreedsImage (String breedString, IWebCallback<String> webCallback){
//        Log.d(TAG, "DogKindsLocalDataSource: getBreedsImage: " + breedString);
//        String uriImageString = DogImageUtils.getUriFromDrawable(context, breedString).toString();
//        WebBreedsDataSource.getInstance().getBreedsImage(breedString,webCallback);
        webCallback.onWebCallback(breedString);
    }
}