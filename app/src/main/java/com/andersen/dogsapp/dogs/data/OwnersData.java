package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.Owner;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OwnersData {
    @SerializedName("owners")
    @Expose
    private List<Owner> owners;

    public List<Owner> getOwners() {
        return this.owners;
    }
}
