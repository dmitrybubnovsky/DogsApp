package com.andersen.dogsapp.dogs.data.web;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.json.DogsKindsData;
import com.andersen.dogsapp.dogs.data.json.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WebDataSource implements IBreedsDataSource {
    private static final String TAG = "#";
    private static WebDataSource webDataSource;

    @SerializedName("dogKinds")
    @Expose
    private List<DogKind> dogKinds;


    private WebDataSource() {
        dogKinds = new ArrayList<>();
    }


    public static WebDataSource getInstance(){
        if (webDataSource == null) {
            webDataSource = new WebDataSource();
        }
        return webDataSource;
    }

    @Override
    public List<DogKind> getDogsKinds() {
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//            }
//        }, 4000);
        queryGETDogsKinds();

        return dogKinds;
    }

    private void queryGETDogsKinds() {
        // http://560057.youcanlearnit.net/services/json/itemsfeed.php
        final String DOG_API_URL = "https://dog.ceo/api";

        InputStream inputStream;
        try {
            URL url = new URL(DOG_API_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Got response code " + responseCode);
            }
            // делаем GET запрос на собачий сайт
            conn.connect();

            // получаем ответ в inputStream
            inputStream = conn.getInputStream();

            JsonParser jsonParser = JsonParser.newInstance();
            DogsKindsData dogsKindsData = jsonParser.parseInputStream(inputStream, DogsKindsData.class);
            dogKinds = dogsKindsData.getDogsKinds();
            Log.d(TAG, "WebDatasourcedogKinds "+dogKinds.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
