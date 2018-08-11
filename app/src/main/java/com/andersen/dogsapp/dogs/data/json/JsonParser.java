package com.andersen.dogsapp.dogs.data.json;

import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class JsonParser {
    private static JsonParser jsonParser;
    private Gson gson;

    private JsonParser() {
        gson = new GsonBuilder().create();
    }

    public static JsonParser newInstance() {
        if (jsonParser == null) {
            jsonParser = new JsonParser();
        }
        return jsonParser;
    }

    public <T> T parseInputStream(InputStream inputStream, Class<T> classType) throws IOException {
//        int size = inputStream.available();
        byte[] buffer = new byte[1024];
        inputStream.read(buffer);
        inputStream.close();
        return gson.fromJson(new String(buffer, "UTF-8"), classType);
    }

    private List<DogKind> parseBreeds(String json) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        Type type = new TypeToken<List<DogKind>>() {}.getType();
        gsonBuilder.registerTypeAdapter(type, new BreedDeserializer());
        Gson gson = gsonBuilder.create();

        List<DogKind> dogKinds = gson.fromJson(json, type);
        return dogKinds;
    }

    public List<DogKind> readBreedsInputStream(InputStream inputStream) throws IOException {
        String json;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream out = null;
        try {
            int length = 0;
            out = new BufferedOutputStream(byteArrayOutputStream);
            while ((length = inputStream.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            out.flush();
            json = byteArrayOutputStream.toString();
            return parseBreeds(json);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
