package com.andersen.dogsapp.dogs.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class DogKind implements Parcelable {
    private int id;
    private String kind;
    private String imageString;

    public DogKind() {
        kind = "";
        imageString = "";
    }

    public DogKind(String kind) {
        this.kind = kind;
        imageString = "";
    }

    public DogKind(String kind, String imageString) {
        this.kind = kind;
        this.imageString = imageString;
    }

    private DogKind(Parcel parcelInstance) {
        id = parcelInstance.readInt();
        kind = parcelInstance.readString();
        imageString = parcelInstance.readString();
    }

    public String getUriImageString() {
        return imageString;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setId(int id) {
        this.id = id;
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
        dest.writeString(kind);
        dest.writeString(imageString);
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

    public int getId() {
        return id;
    }
}
