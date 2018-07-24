package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.Owner;

import java.util.ArrayList;
import java.util.List;

public class DogsHandler {
    private static DogsHandler dogsHandler;
    private List<Dog> dogs;

    public DogsHandler() {
        dogs = new ArrayList<>();
    }

    public static DogsHandler getInstance() {
        if (dogsHandler == null) {
            dogsHandler = new DogsHandler();
        }
        return dogsHandler;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }

    public List<Dog> getDogs() {
        return dogs;
    }




}
