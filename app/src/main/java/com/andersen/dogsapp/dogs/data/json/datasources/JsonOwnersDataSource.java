package com.andersen.dogsapp.dogs.data.json.datasources;

import android.content.Context;

import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.json.OwnersData;
import com.andersen.dogsapp.dogs.data.json.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonOwnersDataSource implements IOwnersDataSource {
    private static JsonOwnersDataSource ownersDataSource;

    @SerializedName("owners")
    @Expose
    private List<Owner> owners;

    private JsonOwnersDataSource(Context context) {
        loadOwners(context);
    }

    public static JsonOwnersDataSource getInstance(Context context) {
        if (ownersDataSource == null) {
            ownersDataSource = new JsonOwnersDataSource(context);
        }
        return ownersDataSource;
    }

    private void loadOwners(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("owners.json");
            JsonParser jsonParser = JsonParser.newInstance();
            OwnersData ownersData = jsonParser.parseInputStream(inputStream, OwnersData.class);
            owners = ownersData.getOwners();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Owner> getOwners() {
        return owners;
    }

    // Этот метод ничего не делает
    @Override
    public Owner addOwner(Owner owner) {
        return new Owner();
    }
}
