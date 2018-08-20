package com.andersen.dogsapp.dogs.data.web;

import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
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

//    @Override
    public void getDogsKinds(IWebCallback<List<DogKind>> webCallback) {
        Call<List<String>> call = instanceAPI.getBreeds();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    dogKinds = convertStringListToDogKindList(response.body());
                    webCallback.onWebCallback(dogKinds);
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

//    @Override
    public void getBreedsImage (String breedString, IWebCallback<String> webCallback){
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
                    webCallback.onWebCallback(uriImageString);
                } else {
                    // Если с response'ом проблема
                    Log.d(TAG, "WebBreedsDataSource: getBreedsImage: response was NOT successful");
//                    uriImageString = listString.get(0);
//                    webCallback.onWebCallback(uriImageString);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                t.printStackTrace();
            }
        });
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

    private List<DogKind> convertStringListToDogKindList (List<String> breedsListString) {

        List<DogKind> dogKinds = new ArrayList<>();
        for (String breedString : breedsListString) {
            dogKinds.add(new DogKind(breedString));
        }
        return dogKinds;
    }
}
