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


    public static WebBreedsDataSource getInstance() {
        if (webBreedsDataSource == null) {
            webBreedsDataSource = new WebBreedsDataSource();
        }
        return webBreedsDataSource;
    }

    @Override
    public void getDogsKinds(ICallback<List<DogKind>> callback) {
        new Thread(() -> {
            try {
                InputStream inputStream = NetworkManager.dogsKindsGetQuery();
                dogKinds = JsonParser.newInstance().readBreedsInputStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            callback.onResponse(dogKinds);
        }).start();
    }
}
