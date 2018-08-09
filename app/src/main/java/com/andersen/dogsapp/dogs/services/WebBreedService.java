package com.andersen.dogsapp.dogs.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.json.DogsKindsData;
import com.andersen.dogsapp.dogs.data.json.JsonParser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WebBreedService extends IntentService {
    public static final String BREEDS_SERVICE_MESSAGE = "MESSAGE";
    public static final String BREEDS_RECEIVER = "BREEDS_RECEIVER";
    public static final String TAG = "#";

    @SerializedName("dogKinds")
    @Expose
    private List<DogKind> dogKinds;



    public WebBreedService() {
        super("WebBreedService.class.getSimpleName()");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Uri uri = intent.getData();
        Log.d(TAG, "onHandleIntent: "+uri.toString());

        doGetParseDogsKinds(uri);

        Intent messageIntent = new Intent (BREEDS_SERVICE_MESSAGE);
        messageIntent.putExtra(BREEDS_RECEIVER, (Parcelable) dogKinds);
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate WebBreddService ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy WebBreddService ");
    }

//    @Override
    public List<DogKind> getDogsKinds(Uri uri) {
        doGetParseDogsKinds(uri);

        return dogKinds;
    }

    private void doGetParseDogsKinds(Uri uri) {
        final String DOG_API_URL = uri.toString();

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
