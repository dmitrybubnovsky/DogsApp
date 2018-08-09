package com.andersen.dogsapp.dogs.data.json;

import com.andersen.dogsapp.dogs.data.entities.DogBreed;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BreedDeserializer implements JsonDeserializer<Map<String, List<String>>> {
//public class BreedDeserializer implements JsonDeserializer<Map<DogBreed, List<DogBreed>>> {

    @Override
    public Map<String, List<String>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject();

//        Map<String, List<String>> dogBreeds = context.deserialize(jsonObject.get("message"), Map<String, List<String>>);
        Map<String, List<String>> dogBreeds = new HashMap<>();

        return dogBreeds;
    }
//
//    @Override
//    public Map<DogBreed, List<DogBreed>> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        final JsonObject jsonObject = json.getAsJsonObject();
//
//        Map<DogBreed, List<DogBreed>> dogBreeds = context.deserialize(jsonObject.get("message"), DogBreed.class);
//
//        return dogBreeds;
//    }





}
