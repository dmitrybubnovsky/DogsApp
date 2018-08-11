package com.andersen.dogsapp.dogs.data.web.retrofitapi;

import com.andersen.dogsapp.dogs.data.entities.DogKind;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogBreedsAPI {
    @GET("")
    Call<List<DogKind>> getBreeds(String url);
}
