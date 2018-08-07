package com.andersen.dogsapp.dogs.data.json;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DogsKindsData {
    @SerializedName("dogsKinds")
    @Expose
    private List<DogKind> dogsKinds;

    public List<DogKind> getDogsKinds() {
        return dogsKinds;
    }

}
