package com.andersen.dogsapp.dogs;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Owner {
    private int ownerId;
    private String ownerName;
    private String ownerSurname;
    private String preferedDogsKind;
    private int[] dogsIds;

    public int getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerFullName() {
        return ownerName + " " + ownerSurname;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public String getPreferedDogsKind() {
        return preferedDogsKind;
    }

    public int[] getDogsIds() {
        return dogsIds;
    }

    public int getDogsQuantity() {
        return dogsIds.length;
    }
}
