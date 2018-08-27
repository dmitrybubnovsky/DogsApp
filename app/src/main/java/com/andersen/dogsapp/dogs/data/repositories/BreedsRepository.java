package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.database.BreedsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.Breed;
import com.andersen.dogsapp.dogs.data.web.ICallback;
import com.andersen.dogsapp.dogs.data.web.WebBreedsDataSource;

import java.util.List;

public class BreedsRepository {
    private static final String TAG = "#";
    private static BreedsRepository instance;

    private BreedsRepository() {
    }

    public static void init() {
        if (instance == null) {
            instance = new BreedsRepository();
        }
    }

    public static BreedsRepository getInstance() {
        return instance;
    }

    public void getBreeds(ICallback<List<Breed>> responseCallback) {
        // Если БД нет, тогда делаем запрос, получаем List стрингов пород,
        // десериал-ем его в List<Breed> и создаем БД из этого листа
        if (BreedsSQLiteDataSource.getInstance().isBreedsDbEmpty()) {
            WebBreedsDataSource.getInstance().getBreeds(dogBreeds -> {
                BreedsSQLiteDataSource.getInstance().addBreedsToDatabase(dogBreeds);
                responseCallback.onResult(dogBreeds);
            });
        } else {
            //иначе читаем БД пород
            BreedsSQLiteDataSource.getInstance().getBreeds(responseCallback);
        }
    }

    public void getBreedsImage(String breedString, ICallback<String> callback) {
        WebBreedsDataSource.getInstance().getBreedsImage(breedString, callback);
    }

    public void updateBreedDBWithUriImage(Breed breed) {
        BreedsSQLiteDataSource.getInstance().updateBreedDBWithUriImage(breed);
    }
}
