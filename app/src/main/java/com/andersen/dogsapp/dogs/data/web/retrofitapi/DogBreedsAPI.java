package com.andersen.dogsapp.dogs.data.web.retrofitapi;

import com.andersen.dogsapp.dogs.data.entities.DogKind;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogBreedsAPI {
    @GET("breeds/list/all")
    Call<List<String>> getBreeds();

    @GET("dogKindInstance/{dogKindInstance}/images/random")
    Call<List<String>> getBreedImageUriString(@Path("dogKindInstance") String breed);
}
