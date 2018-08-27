package com.andersen.dogsapp.dogs.ui.testing_edittext_filling;

import java.util.Random;

public class SomeOwner {
    private static SomeOwner instance;

    private final String[] NAME = {"Alex", "Robert", "Anjelina", "Bruce", "Phill", "Nick", "John",
            "Al", "Ron", "Bill", "Steve", "Greg", "Garry", "Snejana", "Serj", "Criss", "Anjela"};
    private final String[] SURNAME = {"Jackson", "De Niro", "Clooney", "Ford", "Harrison", "Oldman", "Johnson",
            "Ericsson", "Pitt", "Doggson", "Stevenson", "Soderberg"};

    public static SomeOwner getInstance() {
        if (instance == null) {
            instance = new SomeOwner();
        }
        return instance;
    }

    public String name() {
        Random r = new Random();
        return NAME[r.nextInt(17)];
    }

    public String surname() {
        Random r = new Random();
        return SURNAME[r.nextInt(12)];
    }
}

