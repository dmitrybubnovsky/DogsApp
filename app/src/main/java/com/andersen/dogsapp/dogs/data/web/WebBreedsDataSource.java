package com.andersen.dogsapp.dogs.data.web;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.json.DogsKindsData;
import com.andersen.dogsapp.dogs.data.json.JsonParser;
import com.andersen.dogsapp.dogs.ui.breeds.BreedsListActivity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WebBreedsDataSource implements IBreedsDataSource {

    private static final String TAG = "#";
    private static WebBreedsDataSource webBreedsDataSource;

    @SerializedName("dogKinds")
    @Expose
    private List<DogKind> dogKinds;


    private WebBreedsDataSource() {
        dogKinds = new ArrayList<>();
    }


    public static WebBreedsDataSource getInstance(){
        if (webBreedsDataSource == null) {
            webBreedsDataSource = new WebBreedsDataSource();
        }
        return webBreedsDataSource;
    }

    @Override
    public List<DogKind> getDogsKinds() {
        new ThreadQueryGET().start();
        return dogKinds;
    }

    private void doGetParseDogsKinds() {
        final String DOG_API_URL = "https://dog.ceo/api/breeds/list/all";

        InputStream inputStream = null;
        try {
            URL url = new URL(DOG_API_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new IOException("Got response code " + responseCode);
            } else {
                Log.d(TAG, "responseCode is "+responseCode);
            }

            // получаем ответ в inputStream
            inputStream = conn.getInputStream();
            Log.d(TAG, "inputStream " + ( (inputStream == null) ? " == null" : " not null " ));

            JsonParser jsonParser = JsonParser.newInstance();
            String strOutStream = jsonParser.readInputStream(inputStream);
            Log.d(TAG, "WebBreedsDataSource "+strOutStream+" "+ Thread.currentThread().getName());

//            DogsKindsData dogsKindsData = jsonParser.parseInputStream(inputStream, DogsKindsData.class);
//            dogKinds = dogsKindsData.getDogsKinds();
            Log.d(TAG, "dogKinds "+dogKinds.size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ThreadQueryGET extends Thread{

        @Override
        public void run() {
            doGetParseDogsKinds();
        }
    }

}
