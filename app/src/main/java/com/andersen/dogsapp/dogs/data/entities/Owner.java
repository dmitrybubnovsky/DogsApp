package com.andersen.dogsapp.dogs.data.entities;

import android.os.Parcelable;
import android.os.Parcel;

public class Owner implements Parcelable {
    private int ownerId;
    private String ownerName;
    private String ownerSurname;
    private String preferedDogsKind;
    private int[] dogsIds;

    public Owner() {
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

    public int[] getDogsIds() {
        return dogsIds;
    }

    public int getDogsQuantity() {
        return dogsIds.length;
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

    public void setDogsIds(int[] dogsIds) {
        this.dogsIds = dogsIds;
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
        parcelInstance.writeIntArray(dogsIds);
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
        dogsIds = parcelInstance.createIntArray();
    }
}

