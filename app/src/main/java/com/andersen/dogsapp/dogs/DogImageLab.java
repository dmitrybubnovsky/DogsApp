package com.andersen.dogsapp.dogs;
import android.content.Context;
import com.andersen.dogsapp.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DogImageLab {
    private static DogImageLab dogImageLab;
    private List<Integer> dogList;

    public static DogImageLab get(){
        if(dogImageLab == null){
            dogImageLab = new DogImageLab();
        } return dogImageLab;
    }

    public int someImage(){
        return dogList.get(new Random().nextInt(12));
    }

    private DogImageLab(){
        dogList = new ArrayList<>();
        dogList.add(R.drawable.english_coonhound);
        dogList.add(R.drawable.american_foxhound);
        dogList.add(R.drawable.water_spaniel);
        dogList.add(R.drawable.berger_picard);
        dogList.add(R.drawable.chesapeake_bay_retriever);
        dogList.add(R.drawable.chinook);
        dogList.add(R.drawable.munsterlander_pointer);
        dogList.add(R.drawable.pocket_beagle);
        dogList.add(R.drawable.saint_bernard);
        dogList.add(R.drawable.shepherd);
        dogList.add(R.drawable.siberian_husky);
        dogList.add(R.drawable.standard_schnauzer);
    }
}
