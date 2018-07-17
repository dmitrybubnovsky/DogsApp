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

public class OwnersDataSource {
    private static OwnersDataSource ownersDataSource;

    @SerializedName("owners")
    @Expose
    private List<Owner> owners;

    private OwnersDataSource(OwnersDataSource copy){
        this(copy.owners);
    }

    private OwnersDataSource(List<Owner> owners){
        this.owners = new ArrayList<>();
        this.owners.addAll(owners);
    }

    public static OwnersDataSource getInstance (Context context){
        GsonBuilder builder = new GsonBuilder();
        final Gson GSON = builder.create();
        String json;
        json = getAssetsJSON("owners.json", context);

        if(ownersDataSource == null){
            ownersDataSource = new OwnersDataSource(GSON.fromJson(json, OwnersDataSource.class));
        }
        return ownersDataSource;
    }



    public List<Owner> getOwners(){
        return owners;
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
