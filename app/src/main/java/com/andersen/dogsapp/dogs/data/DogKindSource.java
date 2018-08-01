package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.data.entities.DogKind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DogKindSource {

    private final String[] TITLES = new String[]{"Afghan hound", "American bulldog", "American foxhound",
            "Australian cattle", "Belgian tevruren", "Berger pickard", "Bolognese", "American bulldog",
            "Bull terrier", "Chesapeake", "Chinook", "Argentino", "English coonhound", "German shepherd",
            "Icelandic sheepdog", "Komondor", "Mudi", "Munsterlander", "Pharaon hound", "Pocket beagle",
            "Pug", "Saint bernard", "Scottish terrier", "Shepherd", "Siberian husky",
            "Staffordshire terrier", "Standard schnauzer", "Water spaniel"};

    private final String[] IMAGE_FILES = new String[]{"afghan_hound", "american_bulldog", "american_foxhound",
            "australiancattle", "belgiantervuren", "berger_picard", "bolognese", "american_bulldog",
            "bullterrier", "chesapeake", "chinook", "dogoargentino", "english_coonhound", "german_shepherd",
            "icelandicsheepdog", "komondor", "mudi", "munsterlander_pointer", "pharaohhound", "pocket_beagle",
            "pug", "saint_bernard", "scottishterrier", "shepherd", "siberian_husky",
            "staffordshire", "standard_schnauzer", "water_spaniel"};

    private final List<DogKind> dogKindsList;


    private DogKindSource() {
        dogKindsList = new ArrayList<>();
        for (int i=0;i<TITLES.length;i++){
            DogKind dogKind = new DogKind(TITLES[i], IMAGE_FILES[i]);
            dogKindsList.add(dogKind);
        }
    }

    public static List<DogKind> getDogKinds() {
        DogKindSource dogKindSource = new DogKindSource();
        return dogKindSource.dogKindsList;
    }
}
