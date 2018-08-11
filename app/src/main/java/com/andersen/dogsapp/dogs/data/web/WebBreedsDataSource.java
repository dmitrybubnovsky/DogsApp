package com.andersen.dogsapp.dogs.data.web;

import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.json.BreedDeserializer;
import com.andersen.dogsapp.dogs.data.json.JsonParser;
import com.andersen.dogsapp.dogs.data.web.retrofitapi.DogBreedsAPI;
import com.andersen.dogsapp.dogs.utils.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class WebBreedsDataSource implements IBreedsDataSource {
    private static final String TAG = "#";

    private final String BASE_URL = "https://dog.ceo/api/";
    private final String URL_BREEDS_ALL = "breeds/list/all";

    private static WebBreedsDataSource webBreedsDataSource;

    private Retrofit retrofit;
    private DogBreedsAPI breedsAPI;

    @SerializedName("dogKinds")
    @Expose
    private List<DogKind> dogKinds;


    private WebBreedsDataSource() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(buildGsonConverter())
                .build();

        breedsAPI = retrofit.create(DogBreedsAPI.class);
        Log.d(TAG, ""+retrofit.getClass().toString());
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
        Call<List<DogKind>> call = breedsAPI.getBreeds();
        call.enqueue(new Callback<List<DogKind>>() {
            @Override
            public void onResponse(Call<List<DogKind>> call, Response<List<DogKind>> response) {
                Log.d(TAG,"response " + response.body().size());
                dogKinds = response.body();
                Log.d(TAG, ""+dogKinds.size());
                callback.onResponse(dogKinds);
            }

            @Override
            public void onFailure(Call<List<DogKind>> call, Throwable t) {

            }
        });
    }

    private static GsonConverterFactory buildGsonConverter() {

        GsonBuilder gsonBuilder = new GsonBuilder();

        Type type = new TypeToken<List<DogKind>>() {}.getType();
        gsonBuilder.registerTypeAdapter(type, new BreedDeserializer());
        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
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
