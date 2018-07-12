package com.andersen.dogsapp.dogs;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Owner implements Serializable {
    private int ownerId;
    private String ownerName;
    private String ownerSurName;
    private List<Integer> dogId;

    public Owner(int ownerId, String ownerName, String ownerSurName, List<Integer> dogId) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerSurName = ownerSurName;
        this.dogId = dogId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerSurName() {
        return ownerSurName;
    }


}
