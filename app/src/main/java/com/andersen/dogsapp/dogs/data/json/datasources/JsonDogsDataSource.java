package com.andersen.dogsapp.dogs.data.json.datasources;

import android.content.Context;

import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.json.DogsData;

import com.andersen.dogsapp.dogs.data.json.JsonParser;
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

    private void loadDogs(Context context) {
        try {
            JsonParser jsonParser = JsonParser.newInstance();
            InputStream inputStream = context.getAssets().open("dogs.json");
            DogsData dogsData = jsonParser.parseInputStream(inputStream, DogsData.class);
            dogs = dogsData.getDogs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Этот метод ничего не делает
    @Override
    public Dog addDog(Dog dog) {
        return new Dog();
    }

    public static JsonDogsDataSource getInstance(Context context) {
        if (dogsDataSource == null) {
            dogsDataSource = new JsonDogsDataSource(context);
        }
        return dogsDataSource;
    }

    // Этот метод ничего не делает
    @Override
    public List<Dog> getDogs() {
        return null;
    }

    // Этот метод ничего не делает
    @Override
    public List<Dog> getOwnerDogs(Owner owner) {
        return null;
    }
}
