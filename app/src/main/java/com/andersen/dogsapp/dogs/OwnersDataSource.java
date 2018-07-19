package com.andersen.dogsapp.dogs;
import android.content.Context;

import com.andersen.dogsapp.dogs.data.DogsData;
import com.andersen.dogsapp.dogs.data.OwnersData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OwnersDataSource {
    private static OwnersDataSource ownersDataSource;

    private OwnersData ownersData;

    @SerializedName("owners")
    @Expose
    private List<Owner> owners;

    private OwnersDataSource(){
    }

    public static OwnersDataSource getInstance (){
        if(ownersDataSource == null){
            ownersDataSource = new OwnersDataSource();
        }
        return ownersDataSource;
    }

    public List<Owner> getOwners(Context context){
        try {
            InputStream inputStream = context.getAssets().open("owners.json");
            // TODO: do i really need the field 'jsonParser'
            JsonParser jsonParser = JsonParser.newInstance();
            ownersData = jsonParser.parseInputStream(inputStream, OwnersData.class);
            return ownersData.getOwners();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
