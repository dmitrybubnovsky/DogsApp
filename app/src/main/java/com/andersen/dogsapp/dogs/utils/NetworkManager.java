package com.andersen.dogsapp.dogs.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.json.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkManager {
    public static final String TAG = "#";

    public static boolean hasNetWorkAccess(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d(TAG, "ONLINE");
            return true;
        } else {
            Log.d(TAG, "NOT ONLINE");
            return false;
        }
    }

    public static InputStream dogsKindsGetQuery() {
        final String DOG_API_URL = "https://dog.ceo/api/breeds/list/all";

        InputStream inputStream;
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

//            Log.d(TAG, "inputStream " + ( (inputStream == null) ? " == null" : " not null " ));
//            Log.d(TAG, "strOutStream "+strOutStream);
//            Log.d(TAG, "WebBreeds dogKinds "+dogKinds.size());
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
