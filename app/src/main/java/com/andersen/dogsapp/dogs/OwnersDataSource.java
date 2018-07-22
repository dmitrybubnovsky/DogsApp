package com.andersen.dogsapp.dogs;
import android.content.Context;
import com.andersen.dogsapp.dogs.data.OwnersData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OwnersDataSource {
    private static OwnersDataSource ownersDataSource;

    private OwnersData ownersData;

    @SerializedName("owners")
    @Expose
    private List<Owner> owners;

    private OwnersDataSource(Context context){
        owners = getOwners(context);
    }

    public static OwnersDataSource getInstance (Context context){
        if(ownersDataSource == null){
            ownersDataSource = new OwnersDataSource(context);
        }
        return ownersDataSource;
    }

    public List<Owner> getOwners(Context context){
        if(owners != null){
            return owners;
        } else
            try {
                InputStream inputStream = context.getAssets().open("owners.json");
                JsonParser jsonParser = JsonParser.newInstance();
                ownersData = jsonParser.parseInputStream(inputStream, OwnersData.class);
                owners = ownersData.getOwners();
                return owners;
            }catch (IOException e){
                e.printStackTrace();
            }
        return null;
    }
}
