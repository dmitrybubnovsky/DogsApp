package com.andersen.dogsapp.dogs;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

public class JsonParser{
    private static JsonParser jsonParser;
    private Gson gson;

    private JsonParser(){
        gson = new GsonBuilder().create();
    }

    public static JsonParser newInstance(){
        if(jsonParser == null){
            jsonParser = new JsonParser();
        }
        return jsonParser;
    }

    public <T> T parseInputStream(InputStream inputStream, Class<T> classType) throws IOException{
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return gson.fromJson(new String (buffer, "UTF-8"), classType);
    }
}
