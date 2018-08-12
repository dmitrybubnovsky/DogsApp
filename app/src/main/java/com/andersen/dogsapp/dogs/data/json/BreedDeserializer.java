package com.andersen.dogsapp.dogs.data.json;

import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BreedDeserializer implements JsonDeserializer<List<DogKind>> {
    public static final String TAG = "#";

    @Override
    public List<DogKind> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = (JsonObject) json.getAsJsonObject().get("message");

        List<String> breedsListString = new ArrayList<>(jsonObject.keySet());

        List<DogKind> dogKinds = new ArrayList<>();
        for (String breedString : breedsListString) {
            dogKinds.add(new DogKind(breedString));
        }
        Log.d(TAG, "deserialize returns dogKinds with size " + dogKinds.size() + " elements");
        return dogKinds;
    }
}
