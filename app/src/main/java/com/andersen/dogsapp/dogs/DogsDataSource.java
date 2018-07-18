package com.andersen.dogsapp.dogs;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.Log;

import com.andersen.dogsapp.dogs.activity.DogsInfoActivity;
import com.andersen.dogsapp.dogs.data.DogsData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DogsDataSource {
    private static DogsDataSource dogsDataSource;

    private JsonParser jsonParser;
    private Context context;
    private DogsData dogsData;

    private DogsDataSource(Context context){
        jsonParser = JsonParser.newInstance();
        this.context = context;
    }

    public static DogsDataSource getInstance(Context context){
        if(dogsDataSource == null){
           dogsDataSource = new DogsDataSource(context);
        }
        return dogsDataSource;
    }

    public List<Dog> getDogs(){
        try {
            InputStream inputStream = context.getAssets().open("dogs.json");
            dogsData = jsonParser.parseInputStream(inputStream, DogsData.class);
            return dogsData.getDogs();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private Dog getDogById(int dogId){
        Log.d("#  getDogById" , ""+dogId);
        List<Dog> dogs = dogsData.getDogs();
        for(Dog dog : dogs){
            if (dog.getDogId() == (dogId)){
                return dog;
            }
        }
        throw new IndexOutOfBoundsException("Class DataRepository. Method getDogById. Not acceptable Id");
    }

    public List<Dog> getOwnerDogs(Owner owner){
        List<Dog> dogs = new ArrayList<>();
        int[] dogsIds = owner.getDogsIds(); // get array of dogs' ids on single owner
        int dogsQuantity = owner.getDogsQuantity();
        for(int i = 0; i<dogsQuantity; i++){
            dogs.add(getDogById(dogsIds[i]));
        }
        return dogs;
    }


}
