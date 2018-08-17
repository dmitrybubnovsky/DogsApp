package com.andersen.dogsapp.dogs.data.json;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BreedDeserializer implements JsonDeserializer<List<String>> {
    private static final String TAG = "#";

    @Override
    public List<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Log.d(TAG, "deserialize ");
        List<String> breedsListString = new ArrayList<>();
        ;
        JsonObject jsonObject = json.getAsJsonObject();


        if (jsonObject.get("message").isJsonObject()) {
            jsonObject = (JsonObject) jsonObject.get("message");
            breedsListString = new ArrayList<>(jsonObject.keySet()); // TODO: refactror it
            Log.d(TAG, "deserialize: json.isJsonObject() = " + breedsListString + " is " + breedsListString.getClass());
        } else if (jsonObject.get("message").isJsonPrimitive()) {
            Log.d(TAG, "deserialize: json.isJsonPrimitive() = " + breedsListString + " is " + breedsListString.getClass());

            JsonPrimitive jsonPrimitive = jsonObject.get("message").getAsJsonPrimitive();
            String uriString = jsonPrimitive.getAsString();
            breedsListString = new ArrayList<>();
            breedsListString.add(uriString);
        }

        return breedsListString;
    }
}
