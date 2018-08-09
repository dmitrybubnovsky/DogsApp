package com.andersen.dogsapp.dogs.data.web;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.json.JsonParser;
import com.andersen.dogsapp.dogs.utils.NetworkManager;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
    public void getDogsKinds(ICallback<List<DogKind>> callback) {
        new Thread(() -> {
            try{
                InputStream inputStream = NetworkManager.dogsKindsGetQuery();
                dogKinds = JsonParser.newInstance().readBreedsInputStream(inputStream);
            } catch (IOException e){
                e.printStackTrace();
            }
            callback.onResponse(dogKinds);
        }).start();
    }

//    private List<DogKind> dogsKindsGetQuery() {
//    private void dogsKindsGetQuery() {
//        final String DOG_API_URL = "https://dog.ceo/api/breeds/list/all";
//
//        InputStream inputStream;
//        try {
//            URL url = new URL(DOG_API_URL);
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(5000);
//            conn.setConnectTimeout(5000);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            conn.connect();
//
//            int responseCode = conn.getResponseCode();
//            if (responseCode != 200) {
//                throw new IOException("Got response code " + responseCode);
//            } else {
//                Log.d(TAG, "responseCode is "+responseCode);
//            }
//
//            // получаем ответ в inputStream
//            inputStream = conn.getInputStream();
//
//            JsonParser jsonParser = JsonParser.newInstance();
//            String strOutStream = jsonParser.readInputStream(inputStream);
//            dogKinds = jsonParser.parseBreeds(strOutStream);
//
////            Log.d(TAG, "inputStream " + ( (inputStream == null) ? " == null" : " not null " ));
////            Log.d(TAG, "strOutStream "+strOutStream);
////            Log.d(TAG, "WebBreeds dogKinds "+dogKinds.size());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
