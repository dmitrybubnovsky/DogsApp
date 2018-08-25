package com.andersen.dogsapp.dogs.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Breed implements Parcelable {
    public static final Parcelable.Creator<Breed> CREATOR = new Parcelable.Creator<Breed>() {
        @Override
        public Breed createFromParcel(Parcel source) {
            return new Breed(source);
        }

        @Override
        public Breed[] newArray(int size) {
            return new Breed[size];
        }
    };
    private int id;
    private String breedString;
    private String imageString;

    public Breed() {
        breedString = "";
        imageString = "";
    }

    public Breed(String breedString) {
        this.breedString = breedString;
        imageString = "";
    }

    public Breed(String breedString, String imageString) {
        this.breedString = breedString;
        this.imageString = imageString;
    }

    private Breed(Parcel parcelInstance) {
        id = parcelInstance.readInt();
        breedString = parcelInstance.readString();
        imageString = parcelInstance.readString();
    }

    public String getUriImageString() {
        return imageString;
    }

    public String getBreedString() {
        return breedString;
    }

    public void setBreedString(String breedString) {
        this.breedString = breedString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(breedString);
        dest.writeString(imageString);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
