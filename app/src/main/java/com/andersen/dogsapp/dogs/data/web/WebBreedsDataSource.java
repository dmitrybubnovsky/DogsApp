package com.andersen.dogsapp.dogs.data.web;

import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.json.JsonParser;
import com.andersen.dogsapp.dogs.data.web.retrofitapi.DogBreedsAPI;
import com.andersen.dogsapp.dogs.utils.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class WebBreedsDataSource implements IBreedsDataSource {
    private final String BASE_URL = "https://dog.ceo/api";
    private final String URL_BREEDS_ALL = "https://dog.ceo/api";

    private static final String TAG = "#";
    private static WebBreedsDataSource webBreedsDataSource;

    private Gson mGson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build();
    private DogBreedsAPI breedsAPI = retrofit.create(DogBreedsAPI.class);

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
        breedsAPI.getBreeds(URL_BREEDS_ALL).enqueue(new Callback<List<DogKind>>() {
            @Override
            public void onResponse(Call<List<DogKind>> call, Response<List<DogKind>> response) {
                dogKinds = response.body();
                Log.d(TAG, ""+dogKinds.size());
                callback.
            }

            @Override
            public void onFailure(Call<List<DogKind>> call, Throwable t) {

            }
        });
    }

//    @Override
//    public void getDogsKinds(ICallback<List<DogKind>> callback) {
//        new Thread(() -> {
//            try {
//                InputStream inputStream = NetworkManager.dogsKindsGetQuery();
//                dogKinds = JsonParser.newInstance().readBreedsInputStream(inputStream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            callback.onResponse(dogKinds);
//        }).start();
//    }
}
