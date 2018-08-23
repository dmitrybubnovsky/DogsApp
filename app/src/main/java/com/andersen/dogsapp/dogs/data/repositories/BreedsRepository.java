package com.andersen.dogsapp.dogs.data.repositories;

import com.andersen.dogsapp.dogs.data.database.DogKindsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.ICallback;
import com.andersen.dogsapp.dogs.data.web.WebBreedsDataSource;

import java.util.List;

public class BreedsRepository {
    private static final String TAG = "#";
    private static BreedsRepository instance;

    public BreedsRepository() {
    }

    public static void init() {
        if (instance == null) {
            instance = new BreedsRepository();
        }
    }

    public static BreedsRepository getInstance() {
        return instance;
    }

    public void getDogsKinds(ICallback<List<DogKind>> responseCallback) {
        // Если БД нет, тогда делаем запрос, получаем List стрингов пород,
        // десериал-ем его в List<DogKind> и создаем БД из этого листа
        if (DogKindsSQLiteDataSource.getInstance().isDogKindsDatabaseEmpty()) {
            WebBreedsDataSource.getInstance().getDogsKinds(dogKinds -> {
                DogKindsSQLiteDataSource.getInstance().addBreedsToDatabase(dogKinds);
                responseCallback.onResult(dogKinds);
            });
        } else {
            //иначе читам БД пород
            DogKindsSQLiteDataSource.getInstance().getDogKinds(responseCallback);
        }
    }

    public void getBreedsImage(String breedString, ICallback<String> callback) {
        WebBreedsDataSource.getInstance().getBreedsImage(breedString, callback);
    }

    public int updateBreedDBWithUriImage(DogKind dogKind) {
        return DogKindsSQLiteDataSource.getInstance().updateBreedDBWithUriImage(dogKind);
    }
}
