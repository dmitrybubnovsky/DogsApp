package com.andersen.dogsapp.dogs.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Owner implements Parcelable {
    public static final Parcelable.Creator<Owner> CREATOR = new Parcelable.Creator<Owner>() {
        @Override
        public Owner createFromParcel(Parcel source) {
            return new Owner(source);
        }

        @Override
        public Owner[] newArray(int size) {
            return new Owner[size];
        }
    };
    private int ownerId;
    private String ownerName;
    private String ownerSurname;
    private String preferedDogsKind;
    private List<Dog> dogs = new ArrayList<>();

    public Owner() {
    }

    private Owner(Parcel parcelInstance) {
        ownerId = parcelInstance.readInt();
        ownerName = parcelInstance.readString();
        ownerSurname = parcelInstance.readString();
        preferedDogsKind = parcelInstance.readString();
        dogs = new ArrayList<>();
        parcelInstance.readList(dogs, Dog.class.getClassLoader());
    }

    public Owner(String ownerName, String ownerSurname, String preferedDogsKind) {
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.preferedDogsKind = preferedDogsKind;
        dogs = new ArrayList<>(0);
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
        for (Dog dog : dogs) {
            dog.setOwner(this);
        }
    }

    public void addDog(Dog dog) {
        dogs.add(dog);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerFullName() {
        return ownerName + " " + ownerSurname;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
    }

    public String getPreferedDogsKind() {
        return preferedDogsKind;
    }

    public void setPreferedDogsKind(String preferedDogsKind) {
        this.preferedDogsKind = preferedDogsKind;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcelInstance, int flags) {
        parcelInstance.writeInt(ownerId);
        parcelInstance.writeString(ownerName);
        parcelInstance.writeString(ownerSurname);
        parcelInstance.writeString(preferedDogsKind);
        parcelInstance.writeList(dogs);
    }
}

