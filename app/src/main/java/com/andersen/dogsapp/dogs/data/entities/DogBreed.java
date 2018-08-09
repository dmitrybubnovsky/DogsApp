package com.andersen.dogsapp.dogs.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DogBreed{ //  implements Parcelable
    private String kind;
    private List<String> imageString;

    public DogBreed() {
    }

    public DogBreed(String kind) {
        this.kind = kind;
        this.imageString = new ArrayList<>();
    }

    public DogBreed(String kind, List<String> imageString) {
        this.kind = kind;
        this.imageString = imageString;
    }

    private DogBreed(Parcel parcelInstance) {
        kind = parcelInstance.readString();
        // TODO finish this constructor
//        imageString = parcelInstance.readString();
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<String> getImageString() {
        return imageString;
    }

    public void setImageString(List<String> imageString) {
        this.imageString = imageString;
    }
}
