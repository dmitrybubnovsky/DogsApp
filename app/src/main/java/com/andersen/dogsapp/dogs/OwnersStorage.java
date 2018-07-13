package com.andersen.dogsapp.dogs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OwnersStorage {
    private static OwnersStorage ownersStorage;

    @SerializedName("owners")
    @Expose
    public List<Owner> owners;

    private OwnersStorage(OwnersStorage copy){
        this(copy.owners);
    }

    private OwnersStorage(List<Owner> owners){
        this.owners = new ArrayList<>();
        this.owners.addAll(owners);
    }

    public static OwnersStorage init (OwnersStorage instance){
        if(ownersStorage == null){
            ownersStorage = new OwnersStorage(instance);
        }
        return ownersStorage;
    }

    public static OwnersStorage get (){
            return ownersStorage;
    }

    public List<Owner> getOwners(){
        return owners;
    }

    public void setOwners(List<Owner> owners){
        if (owners == null){
            this.owners.addAll(owners);
        }
    }

    public Owner getOwner(int ownerId){
        for(Owner owner : owners){
            if (owner.getOwnerId() == (ownerId))
                return owner;
        }
        return null;
    }
    public List<String> getOwnerNames(){
        ArrayList<String> fullNames = new ArrayList<>();
        String fullName = null;
        for(Owner owner : owners){
            fullName = owner.getOwnerName();
            fullNames.add(fullName);
        }
        return fullNames;
    }
}
