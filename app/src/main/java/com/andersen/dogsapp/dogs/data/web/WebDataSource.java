package com.andersen.dogsapp.dogs.data.web;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    private static final String TAG = "#^ " + WebDataSource.class.getSimpleName();
    private static WebDataSource webDataSource;

    private final String DOG_API_URL = "https://dog.ceo/api";

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
        queryGETDogsKinds();
        return dogKinds;
    }

    private static boolean isOnline(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d(TAG, "ONLINE");
            return true;
        } else {
            Log.d(TAG, "NOT ONLINE");
            return false;
        }
    }

    private void queryGETDogsKinds() {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
