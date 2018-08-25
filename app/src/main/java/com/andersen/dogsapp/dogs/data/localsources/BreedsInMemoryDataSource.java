package com.andersen.dogsapp.dogs.data.localsources;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.ICallback;

import java.util.ArrayList;
import java.util.List;

public class BreedsInMemoryDataSource {
    public static final String TAG = "#";

    private static BreedsInMemoryDataSource instance;
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

    private BreedsInMemoryDataSource() {
        dogKindsList = new ArrayList<>();
        for (int i = 0; i < TITLES.length; i++) {
            DogKind dogKind = new DogKind(TITLES[i], "");
            dogKindsList.add(dogKind);
        }
    }

    public static BreedsInMemoryDataSource getInstance() {
        if (instance == null) {
            instance = new BreedsInMemoryDataSource();
        }
        return instance;
    }

    public void getDogsKinds(ICallback<List<DogKind>> callback) {
        callback.onResult(dogKindsList);
    }

    public void getBreedsImage(String breedString, ICallback<String> webCallback) {
        webCallback.onResult(breedString);
    }
}