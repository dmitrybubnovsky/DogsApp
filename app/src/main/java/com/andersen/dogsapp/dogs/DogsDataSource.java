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

    private DogsDataSource(DogsDataSource copy){
        this(copy.dogs);
    }

    private DogsDataSource(List<Dog> dogs){
        this.dogs = new ArrayList<>();
        this.dogs.addAll(dogs);
    }

    public static DogsDataSource init (DogsDataSource instance){
        if(dogsDataSource == null){
            dogsDataSource = new DogsDataSource(instance);
        }
        return dogsDataSource;
    }

    public static DogsDataSource getInstance (Context context){
        GsonBuilder builder = new GsonBuilder();
        final Gson GSON = builder.create();
        String json;
        json = getAssetsJSON("dogs.json", context);

        if(dogsDataSource == null){
            dogsDataSource = new DogsDataSource(GSON.fromJson(json, DogsDataSource.class));
        }
        return dogsDataSource;
    }

    public List<Dog> getDogs(){
        return dogs;
    }

    private static String getAssetsJSON(String fileName, Context context){
        String json = null;
        try{
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String (buffer, "UTF-8");
        } catch (IOException e ){
            e.printStackTrace();
        }
        return json;
    }
}
