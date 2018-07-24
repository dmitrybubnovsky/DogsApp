package com.andersen.dogsapp.dogs;

import android.content.Context;

import com.andersen.dogsapp.dogs.data.DogsData;
import com.andersen.dogsapp.dogs.data.IDogsDataSource;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonDogsDataSource implements IDogsDataSource {
    private static JsonDogsDataSource dogsDataSource;

    @SerializedName("dogs")
    @Expose
    private List<Dog> dogs;

    private JsonDogsDataSource(Context context) {
        loadDogs(context);
    }

    public static JsonDogsDataSource getInstance(Context context) {
        if (dogsDataSource == null) {
            dogsDataSource = new JsonDogsDataSource(context);
        }
        return dogsDataSource;
    }

    @Override
    public List<Dog> getOwnerDogs(Owner owner) {
        List<Dog> ownerDogs = new ArrayList<>();
        int[] dogsIds = owner.getDogsIds(); // get array of dogs' ids on single owner
        int dogsQuantity = owner.getDogsQuantity();
        for (int i = 0; i < dogsQuantity; i++) {
            ownerDogs.add(getDogById(dogsIds[i]));
        }
        return ownerDogs;
    }

    private Dog getDogById(int dogId) {
        for (Dog dog : dogs) {
            if (dog.getDogId() == dogId) {
                return dog;
            }
        }
        throw new IndexOutOfBoundsException("Class DataRepository. Method getDogById. Not acceptable Id");
    }

    private void loadDogs(Context context){
        try {
            JsonParser jsonParser = JsonParser.newInstance();
            InputStream inputStream = context.getAssets().open("dogs.json");
            DogsData dogsData = jsonParser.parseInputStream(inputStream, DogsData.class);
            dogs = dogsData.getDogs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
