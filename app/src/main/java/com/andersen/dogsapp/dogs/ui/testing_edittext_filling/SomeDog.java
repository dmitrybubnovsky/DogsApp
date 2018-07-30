package com.andersen.dogsapp.dogs.ui.testing_edittext_filling;
import java.util.Random;

public class SomeDog {
    private static SomeDog instance;

    private final String[] NAME = {"Juchka", "Palkan", "Kashtanka", "Belka", "Strelka", "Plooto"};
    private final String[] KIND = {"Afghan hound","American bulldog","American foxhound",
            "Australian cattle","Belgian tevruren","Berger pickard","Bolognese","American bulldog",
            "Bull terrier","Chesapeake","Chinook","Argentino","English coonhound","German shepherd",
            "Icelandic sheepdog","Komondor","Mudi","Munsterlander","Pharaon hound","Pocket beagle",
            "Pug","Saint bernard","Scottish terrier","Shepherd","Siberian husky",
            "Staffordshire terrier","Standard schnauzer","Water spaniel"};

    public static SomeDog get() {
        if (instance == null) {
            instance = new SomeDog ();
        }
        return instance;
    }

    public String name(){
        Random r = new Random();
        return NAME[r.nextInt(6)];
    }

    public String kind(){
        Random r = new Random();
        return KIND[r.nextInt(28)];
    }

    public int weight(){
        Random r = new Random();
        return r.nextInt(80);
    }

    public int age(){
        Random r = new Random();
        return r.nextInt(180);
    }

    public int tall(){
        Random r = new Random();
        return r.nextInt(80);
    }

}
