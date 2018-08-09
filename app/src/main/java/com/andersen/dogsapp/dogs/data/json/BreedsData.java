package com.andersen.dogsapp.dogs.data.json;

import com.andersen.dogsapp.dogs.data.entities.DogBreed;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BreedsData {

    @SerializedName("message")
    @Expose
    private List<DogBreed> dogBreeds;
    
    public BreedsData() {
    }

    public BreedsData(List<DogBreed> dogBreeds) {
        this.dogBreeds = dogBreeds;
    }

    public List<DogBreed> getDogBreeds() {
        return dogBreeds;
    }

    public void setDogBreeds(List<DogBreed> dogBreeds) {
        this.dogBreeds = dogBreeds;
    }
}
