package com.andersen.dogsapp.dogs.data;

import java.util.Arrays;
import java.util.List;

public class DogKindSource {
    private static DogKindSource instance;

    private final String[] TITLES = new String[]{"Afghan hound", "American bulldog", "American foxhound",
            "Australian cattle", "Belgian tevruren", "Berger pickard", "Bolognese", "American bulldog",
            "Bull terrier", "Chesapeake", "Chinook", "Argentino", "English coonhound", "German shepherd",
            "Icelandic sheepdog", "Komondor", "Mudi", "Munsterlander", "Pharaon hound", "Pocket beagle",
            "Pug", "Saint bernard", "Scottish terrier", "Shepherd", "Siberian husky",
            "Staffordshire terrier", "Standard schnauzer", "Water spaniel"};

    private final String[] IMAGE_FILES = new String[]{"afghan_hound", "american_bulldog", "american_foxhound",
            "australiancattle", "belgiantervuren", "berger_picard", "bolognese", "american_bulldog",
            "bullterrier", "chesapeake", "chinook", "dogoargentino", "english_coonhound", "german_shepherd",
            "icelandicsheepdog", "komondor", "mudi", "munsterlander_pointer", "pharaonhound", "pocket_beagle",
            "pug", "saint_bernard", "scottishterrier", "shepherd", "siberian_husky",
            "staffordshire", "standard_schnauzer", "water_spaniel"};

    private List<String> kindsList = Arrays.asList(TITLES);
    private List<String> kindImagesList = Arrays.asList(IMAGE_FILES);


    private DogKindSource(){
    }

    public static DogKindSource get() {
        if (instance == null) {
            instance = new DogKindSource();
        }
        return instance;
    }

    public List<String> kindsList(){
        return instance.kindsList;
    }

    public List<String> imagesList(){
        return instance.kindImagesList;
    }
}
