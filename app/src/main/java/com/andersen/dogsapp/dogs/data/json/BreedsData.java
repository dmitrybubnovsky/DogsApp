package com.andersen.dogsapp.dogs.data.json;

import com.andersen.dogsapp.dogs.data.entities.Breed;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BreedsData {
    @SerializedName("message")
    @Expose
    private List<Breed> breeds;

    public List<Breed> getBreeds() {
        return breeds;
    }
}
