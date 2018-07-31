package com.andersen.dogsapp.dogs.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class DogKind implements Parcelable {
    private String kind;
    private String imageString;

    public String getImageString() {
        return imageString;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public static final Parcelable.Creator<DogKind> CREATOR = new Parcelable.Creator<DogKind>() {
        @Override
        public DogKind createFromParcel(Parcel source) {
            return new DogKind(source);
        }

        @Override
        public DogKind[] newArray(int size) {
            return new DogKind[size];
        }
    };

    public DogKind() {
    }

    private DogKind(Parcel parcelInstance) {
        kind = parcelInstance.readString();
        imageString = parcelInstance.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kind);
        dest.writeString(imageString);
    }
}
