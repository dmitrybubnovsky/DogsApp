package com.andersen.dogsapp.dogs.data.entities;

import android.os.Parcelable;
import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class Owner implements Parcelable {
    private int ownerId;
    private String ownerName;
    private String ownerSurname;
    private String preferedDogsKind;
    private List<Dog> dogs;

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
        for (Dog dog:dogs) {
            dog.setOwner(this);
        }
    }

    public Owner() {
    }

    public Owner(String ownerName, String ownerSurname, String preferedDogsKind) {
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.preferedDogsKind = preferedDogsKind;
        dogs = new ArrayList<>(0);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getOwnerFullName() {
        return ownerName + " " + ownerSurname;
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

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
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
    }


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

    private Owner(Parcel parcelInstance) {
        ownerId = parcelInstance.readInt();
        ownerName = parcelInstance.readString();
        ownerSurname = parcelInstance.readString();
        preferedDogsKind = parcelInstance.readString();
    }
}

