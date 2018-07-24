package com.andersen.dogsapp.dogs;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;
import android.os.Parcel;

public class Dog implements Parcelable {
    private int dogId;
    private int dogAge;
    private int dogTall;
    private int dogWeight;

    private String dogImageString;
    private String dogName;
    private String dogKind;

    public int getDogId() {
        return dogId;
    }

    public String getDogName() {
        return dogName;
    }

    public String getDogKind() {
        return dogKind;
    }


    public int getDogImageId(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(dogImageString, "drawable", context.getPackageName());
        return resourceId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dogId);
        dest.writeInt(dogAge);
        dest.writeInt(dogTall);
        dest.writeInt(dogWeight);
        dest.writeString(dogImageString);
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

    private Dog(Parcel parcelInstance) {
        dogId = parcelInstance.readInt();
        dogAge = parcelInstance.readInt();
        dogTall = parcelInstance.readInt();
        dogWeight = parcelInstance.readInt();
        dogImageString = parcelInstance.readString();
        dogName = parcelInstance.readString();
        dogKind = parcelInstance.readString();
    }
}


