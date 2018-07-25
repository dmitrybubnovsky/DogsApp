package com.andersen.dogsapp.dogs.data.json;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DogsData {
    @SerializedName("dogs")
    @Expose
    private List<Dog> dogs;

    public List<Dog> getDogs() {
        return dogs;
    }
}
