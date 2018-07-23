package com.andersen.dogsapp.dogs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.os.Parcel;

public class Dog implements Parcelable {
    private int dogId;
    private String dogImageString;
    private int dogAge;
    private int dogTall;
    private int dogWeight;

    private String dogName;
    private String dogKind;

    public Dog() {
    }

    public int getDogId() {
        return dogId;
    }

    public String getDogName() {
        return dogName;
    }

    public String getDogKind() {
        return dogKind;
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

    public String getDogImageString() {
        return dogImageString;
    }

    public int getDogImageId(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(getDogImageString(), "drawable", context.getPackageName());
        return resourceId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dogId);
        dest.writeString(dogImageString);
        dest.writeInt(dogAge);
        dest.writeInt(dogTall);
        dest.writeInt(dogWeight);
        dest.writeString(dogName);
        dest.writeString(dogKind);
    }

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

    public void setDogId(int dogId) {
        this.dogId = dogId;
    }

    public void setDogImageString(String dogImg) {
        dogImageString = dogImg;
    }

    public void setDogAge(int dogAge) {
        this.dogAge = dogAge;
    }

    public void setDogTall(int dogTall) {
        this.dogTall = dogTall;
    }

    public void setDogWeight(int dogWeight) {
        this.dogWeight = dogWeight;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public void setDogKind(String dogKind) {
        this.dogKind = dogKind;
    }



    private Dog(Parcel parcelInstance) {
        dogId = parcelInstance.readInt();
        dogImageString = parcelInstance.readString();
        dogAge = parcelInstance.readInt();
        dogTall = parcelInstance.readInt();
        dogWeight = parcelInstance.readInt();
        dogName = parcelInstance.readString();
        dogKind = parcelInstance.readString();
    }
}


