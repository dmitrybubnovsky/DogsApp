package com.andersen.dogsapp.dogs.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Dog implements Parcelable {
    public static final Parcelable.Creator<Dog> CREATOR = new Parcelable.Creator<Dog>() {
        @Override
        public Dog createFromParcel(Parcel source) {
            return new Dog(source);
        }

        @Override
        public Dog[] newArray(int size) {
            return new Dog[size];
        }
    };
    private int dogId;
    private int dogOwnerId;
    private int dogAge;
    private int dogTall;
    private int dogWeight;
    private Owner owner;
    private String dogImageString;
    private String dogName;
    private String breed;
    private Breed breedInfo;

    public Dog() {
    }

    private Dog(Parcel parcelInstance) {
        dogId = parcelInstance.readInt();
        dogOwnerId = parcelInstance.readInt();
        dogAge = parcelInstance.readInt();
        dogTall = parcelInstance.readInt();
        dogWeight = parcelInstance.readInt();
        dogImageString = parcelInstance.readString();
        dogName = parcelInstance.readString();
        breed = parcelInstance.readString();
        breedInfo = parcelInstance.readParcelable(Breed.class.getClassLoader());
    }

    public Dog(String dogName, Owner owner, int dogAge, int dogTall, int dogWeight) {
        this.dogName = dogName;
        this.owner = owner;
        this.dogOwnerId = owner.getOwnerId();
        this.dogAge = dogAge;
        this.dogTall = dogTall;
        this.dogWeight = dogWeight;
    }

    public Dog(String errorEmptyDog) {
        dogName = errorEmptyDog;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getOwnerId() {
        return dogOwnerId;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getDogImageString() {
        return dogImageString;
    }

    public void setDogImageString(String dogImageString) {
        this.dogImageString = dogImageString;
    }

    public int getDogAge() {
        return dogAge;
    }

    public void setDogAge(int dogAge) {
        this.dogAge = dogAge;
    }

    public int getDogTall() {
        return dogTall;
    }

    public void setDogTall(int dogTall) {
        this.dogTall = dogTall;
    }

    public int getDogWeight() {
        return dogWeight;
    }

    public void setDogWeight(int dogWeight) {
        this.dogWeight = dogWeight;
    }

    public void setDogOwnerId(int dogOwnerId) {
        this.dogOwnerId = dogOwnerId;
    }

    public int getDogId() {
        return dogId;
    }

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dogId);
        dest.writeInt(dogOwnerId);
        dest.writeInt(dogAge);
        dest.writeInt(dogTall);
        dest.writeInt(dogWeight);
        dest.writeString(dogImageString);
        dest.writeString(dogName);
        dest.writeString(breed);
        dest.writeParcelable(breedInfo, flags);
    }
}


