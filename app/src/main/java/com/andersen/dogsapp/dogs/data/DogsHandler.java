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

    public static DogsHandler getInstance (){
        if(dogsHandler == null){
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

    private Dog getDogById(int dogId){
        for(Dog dog : dogs){
            if (dog.getDogId() == (dogId)){
                return dog;
            }
        }
        throw new IndexOutOfBoundsException("Class DataRepository. Method getDogById. Not acceptable Id");
    }

    public List<Dog> getOwnerDogs(Owner owner){
        List<Dog> ownerDogs = new ArrayList<>();
        int[] dogsIds = owner.getDogsIds(); // get array of dogs' ids on single owner
        int dogsQuantity = owner.getDogsQuantity();
        for(int i = 0; i<dogsQuantity; i++){
            ownerDogs.add(getDogById(dogsIds[i]));
        }
        return ownerDogs ;
    }
}
