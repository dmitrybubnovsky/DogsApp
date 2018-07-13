package com.andersen.dogsapp.dogs;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Owner implements Serializable {
    private int ownerId;
    private String ownerName;
    private String ownerSurname;
    private String preferedDogsKind;
    public ArrayList<Integer> dogsIds;

    public Owner(int ownerId, String ownerName, String ownerSurname, String preferDogKind, ArrayList<Integer> dogsIds) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.preferedDogsKind = preferDogKind;
        this.dogsIds = new ArrayList<>();
        this.dogsIds.addAll(dogsIds);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public String getPreferedDogsKind() {
        return preferedDogsKind;
    }


    public ArrayList<Integer> getDogsIds() {
        return dogsIds;
    }

    public Integer getDogsQuantity() {
        return Integer.valueOf(getDogsIds().size());
    }


}
