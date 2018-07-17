package com.andersen.dogsapp.dogs;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DogsDataSource {
    private static DogsDataSource dogsDataSource;

    @SerializedName("dogs")
    @Expose
    private List<Dog> dogs;

    private DogsDataSource(List<Dog> dogs){
        this.dogs = new ArrayList<>();
        this.dogs.addAll(dogs);
    }

    public static DogsDataSource getInstance (Context context){
        if(dogsDataSource == null){
            try{
                InputStream inputStream = context.getAssets().open("dogs.json");
                dogsDataSource = JsonParser.newInstance().parseInputStream(inputStream, DogsDataSource.class);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return dogsDataSource;
    }

    public List<Dog> getDogs(){
        return dogs;
    }
}
