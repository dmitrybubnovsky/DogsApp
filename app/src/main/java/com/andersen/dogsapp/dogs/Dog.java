package com.andersen.dogsapp.dogs;

import java.io.Serializable;
import java.util.UUID;

public class Dog implements Serializable{
    private Long dogId;
    private Long ownerId;
    private String dogName;
    private String dogKind;
    private int dogImageId;
    private int dogAge;

    public Dog() {
    }

    public Dog(Long dogId, Long ownerId, String dogName, String dogKind, int dogImageId, int dogAge) {
        this.dogId = dogId;
        this.ownerId = ownerId;
        this.dogName = dogName;
        this.dogKind = dogKind;
        this.dogImageId = dogImageId;
        this.dogAge = dogAge;
    }

    public Long getDogId() {
        return dogId;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getDogName() {
        return dogName;
    }

    public String getDogKind() {
        return dogKind;
    }

    public int getDogImageId() {
        return dogImageId;
    }

    public int getDogAge() {
        return dogAge;
    }
}
