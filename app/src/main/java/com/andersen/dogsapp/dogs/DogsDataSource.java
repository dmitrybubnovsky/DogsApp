package com.andersen.dogsapp.dogs;
import android.content.Context;
import android.util.Log;
import com.andersen.dogsapp.dogs.data.DogsData;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DogsDataSource {
    private static DogsDataSource dogsDataSource;

    private DogsData dogsData;

    private DogsDataSource(){
    }

    public static DogsDataSource getInstance(){
        if(dogsDataSource == null){
           dogsDataSource = new DogsDataSource();
        }
        return dogsDataSource;
    }

    public List<Dog> getDogs(Context context){
        try {
            JsonParser jsonParser = JsonParser.newInstance();
            InputStream inputStream = context.getAssets().open("dogs.json");
            dogsData = jsonParser.parseInputStream(inputStream, DogsData.class);
            return dogsData.getDogs();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private Dog getDogById(int dogId){
        String str = (dogsData.equals(null)) ? "dogsData == null " : "ok " ;
        Log.d("#  getDogById ", str);
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
