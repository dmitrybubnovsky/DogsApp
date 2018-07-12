package com.andersen.dogsapp.dogs;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Owner implements Serializable {
    private Long ownerId;
    private String ownerName;
    private String ownerSurName;
    private List<UUID> dogId;

    public Owner(Long ownerId, String ownerName, String ownerSurName, List<UUID> dogId) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerSurName = ownerSurName;
        this.dogId = dogId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getOwnerSurName() {
        return ownerSurName;
    }

    public List<UUID> getDogId() {
        return dogId;
    }
}
