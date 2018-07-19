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

    private JsonParser jsonParser;
    //private Context context;
    private OwnersData ownersData;

    @SerializedName("owners")
    @Expose
    private List<Owner> owners;

    private OwnersDataSource(){//Context context
        //this.context = context;
        jsonParser = JsonParser.newInstance();
    }

    public static OwnersDataSource getInstance (){//Context context
        if(ownersDataSource == null){
            ownersDataSource = new OwnersDataSource();//context
        }
        return ownersDataSource;
    }

    public List<Owner> getOwners(Context context){
        try {
            InputStream inputStream = context.getAssets().open("owners.json");
            // TODO: do i really need the field 'jsonParser'
            ownersData = jsonParser.parseInputStream(inputStream, OwnersData.class);
            return ownersData.getOwners();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
