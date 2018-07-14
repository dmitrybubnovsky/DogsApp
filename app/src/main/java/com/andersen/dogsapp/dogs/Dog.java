package com.andersen.dogsapp.dogs;

import java.io.Serializable;
import java.util.UUID;

public class Dog implements Serializable{
    private int dogId;
    private int ownerId;
    private int dogImageId;
    private int dogAge;
    private int dogTall;
    private int dogWeight;

    private String dogName;
    private String dogKind;

    public int getDogId() {
        return dogId;
    }

    public int getOwnerId() {
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

    public int getDogTall() {
        return dogTall;
    }

    public int getDogWeight() {
        return dogWeight;
    }
}
