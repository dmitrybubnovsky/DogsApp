package com.andersen.dogsapp.dogs.data.web;

import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.json.BreedDeserializer;
import com.andersen.dogsapp.dogs.data.web.retrofitapi.DogBreedsAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebBreedsDataSource implements IBreedsDataSource {
    private static final String TAG = "# WebDataSource";

    private final String BASE_URL = "https://dog.ceo/api/";
    private static WebBreedsDataSource webBreedsDataSource;
    private Retrofit instanceRetrofit;
    private DogBreedsAPI instanceAPI;

    @SerializedName("dogKinds")
    @Expose
    private List<DogKind> dogKinds;


    private WebBreedsDataSource() {
        instanceRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(logLevel())
                .addConverterFactory(buildGsonConverter())
                .build();
        instanceAPI = instanceRetrofit.create(DogBreedsAPI.class);
        dogKinds = new ArrayList<>();
    }

    public static WebBreedsDataSource getInstance() {
        if (webBreedsDataSource == null) {
            webBreedsDataSource = new WebBreedsDataSource();
        }
        return webBreedsDataSource;
    }

    @Override
    public void getDogsKinds(ICallback<List<DogKind>> iCallback) {
        Call<List<DogKind>> call = instanceAPI.getBreeds();
        call.enqueue(new Callback<List<DogKind>>() {
            @Override
            public void onResponse(Call<List<DogKind>> call, Response<List<DogKind>> response) {
                if (response.isSuccessful()) {
                    dogKinds = response.body();
                    iCallback.onResponseICallback(dogKinds);
                } else {
                    Log.d(TAG, "response is NOT successful");
                }
            }

            @Override
            public void onFailure(Call<List<DogKind>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public String getBreedsImage (ICallback<String> iCallback){

        // !!! TODO: FIX RETURN
        return "";
    }

    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Type type = new TypeToken<List<DogKind>>() {
        }.getType();
        gsonBuilder.registerTypeAdapter(type, new BreedDeserializer());
        Gson gson = gsonBuilder.create();
        return GsonConverterFactory.create(gson);
    }

    private static OkHttpClient logLevel() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        return client;
    }
}
