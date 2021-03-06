package com.andersen.dogsapp.dogs.data.web;

import android.support.annotation.NonNull;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.Breed;
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

public class WebBreedsDataSource {
    private static final String TAG = "# WebDataSource";
    private static WebBreedsDataSource webBreedsDataSource;
    private final String BASE_URL = "https://dog.ceo/api/";
    private Retrofit instanceRetrofit;
    private DogBreedsAPI instanceAPI;

    @SerializedName("breeds")
    @Expose
    private List<Breed> breeds;


    private WebBreedsDataSource() {
        instanceRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(logLevel())
                .addConverterFactory(buildGsonConverter())
                .build();
        instanceAPI = instanceRetrofit.create(DogBreedsAPI.class);
        breeds = new ArrayList<>();
    }

    public static WebBreedsDataSource getInstance() {
        if (webBreedsDataSource == null) {
            webBreedsDataSource = new WebBreedsDataSource();
        }
        return webBreedsDataSource;
    }

    private static GsonConverterFactory buildGsonConverter() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Type type = new TypeToken<List<String>>() {
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

    public void getBreeds(ICallback<List<Breed>> resultCallback) {
        Call<List<String>> call = instanceAPI.getBreeds();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(@NonNull Call<List<String>> call, @NonNull Response<List<String>> response) {
                if (response.isSuccessful()) {
                    breeds = convertStringListToBreedList(response.body());
                    resultCallback.onResult(breeds);
                } else {
                    Log.d(TAG, "response is NOT successful");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void getBreedsImage(String breedString, ICallback<String> webCallback) {
        Call<List<String>> call = instanceAPI.getBreedImageUriString(breedString);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> listString;
                String uriImageString;
                // Если с response'ом все ок, тогда вытягиваем Uri строку,
                // которая приходит к нам от десериализатора как List<String>
                if (response.isSuccessful()) {
                    listString = response.body();
                    uriImageString = listString.get(0);
                    webCallback.onResult(uriImageString);
                } else {
                    // Если с response'ом проблема
                    Log.d(TAG, "WebBreedsDataSource: getBreedsImage: response was NOT successful");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private List<Breed> convertStringListToBreedList(List<String> breedsListString) {

        List<Breed> breeds = new ArrayList<>();
        for (String breedString : breedsListString) {
            breeds.add(new Breed(breedString));
        }
        return breeds;
    }
}
