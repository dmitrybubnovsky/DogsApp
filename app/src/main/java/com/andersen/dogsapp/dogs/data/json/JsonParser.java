package com.andersen.dogsapp.dogs.data.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;

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
        byte[] buffer = new byte[1024];
        inputStream.read(buffer);
        inputStream.close();
        return gson.fromJson(new String(buffer, "UTF-8"), classType);
    }
}
