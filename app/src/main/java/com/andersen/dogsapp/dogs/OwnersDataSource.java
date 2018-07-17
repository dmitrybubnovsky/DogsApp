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

    private OwnersDataSource(List<Owner> owners){
        this.owners = new ArrayList<>();
        this.owners.addAll(owners);
    }

    public static OwnersDataSource getInstance (Context context){
        if(ownersDataSource == null){
            try{
                InputStream inputStream = context.getAssets().open("owners.json");
                ownersDataSource = JsonParser.newInstance().parseInputStream(inputStream, OwnersDataSource.class);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return ownersDataSource;
    }

    public List<Owner> getOwners(){
        return owners;
    }
}
