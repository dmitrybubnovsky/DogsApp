package com.andersen.dogsapp.dogs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OwnersStorage {
    private static OwnersStorage ownersStorage;

    @SerializedName("owners")
    @Expose
    public List<Owner> owners;

    public List<Owner> getOwners(){
        return owners;
    }

    public int getCount(){
        return owners.size();
    }

    public void setOwners(List<Owner> owners){
        if (owners == null){
            this.owners.addAll(owners);
        }
    }
}
