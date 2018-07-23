package com.andersen.dogsapp.dogs.data;

import android.util.Log;

import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.Owner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DogsData {
    @SerializedName("dogs")
    @Expose
    private List<Dog> dogs;

    public List<Dog> getDogs() {
        Log.d("# DogsData. getDogs", "" + dogs.size());
        return dogs;
    }
}
