package com.andersen.dogsapp.dogs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OwnersDataSource {
    private static OwnersDataSource sOwnersDataSource;

    @SerializedName("owners")
    @Expose
    public List<Owner> owners;

    private OwnersDataSource(OwnersDataSource copy){
        this(copy.owners);
    }

    private OwnersDataSource(List<Owner> owners){
        this.owners = new ArrayList<>();
        this.owners.addAll(owners);
    }

    public static OwnersDataSource init (OwnersDataSource instance){
        if(sOwnersDataSource == null){
            sOwnersDataSource = new OwnersDataSource(instance);
        }
        return sOwnersDataSource;
    }

    public static OwnersDataSource get (){
            return sOwnersDataSource;
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
    public ArrayList<String> getOwnersNames(){
        ArrayList<String> fullNames = new ArrayList<>();
        for(Owner owner : owners){
            fullNames.add(owner.getOwnerName()+" "+owner.getOwnerSurname());
        }
        return fullNames;
    }

    public ArrayList<String> getPrefereDogsKinds(){
        ArrayList<String> dogsKinds = new ArrayList<>();
        for(Owner owner : owners){
            dogsKinds.add(owner.getPreferedDogsKind());
        }
        return dogsKinds;
    }
    public ArrayList<Integer> getQuantitiesDogs(){
        ArrayList<Integer> quantitiesDogs = new ArrayList<>();
        for(Owner owner : owners){
          //  quantitiesDogs.add(owner.getDogsQuantity());
            quantitiesDogs.add(owner.getDogsIds().size());
        }
        return quantitiesDogs;
    }
}
