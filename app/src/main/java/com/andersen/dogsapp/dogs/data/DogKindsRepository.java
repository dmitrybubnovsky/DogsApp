package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.data.database.DogKindsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IDatabaseCallback;
import com.andersen.dogsapp.dogs.data.web.IWebCallback;
import com.andersen.dogsapp.dogs.data.web.WebBreedsDataSource;

import java.util.List;

public class DogKindsRepository implements IBreedsDataSource {
    private static final String TAG = "#";
    private static DogKindsRepository instance;

    public DogKindsRepository() {
    }

    public static DogKindsRepository getInstance() {
        if (instance == null) {
            instance = new DogKindsRepository();
        }
        return instance;
    }

    public static DogKindsRepository get() {
        return instance;
    }

    @Override
    public void getDogsKinds(IWebCallback<List<DogKind>> responseCallback, IDatabaseCallback<List<DogKind>> dbCallback) {
        // Если БД нет, тогда делаем запрос, получаем List стрингов пород,
        // десериал-ем его в List<DogKind> и создаем БД из этого листа
        if (DogKindsSQLiteDataSource.getInstance().isBreedDatabaseEmpty()) {
            WebBreedsDataSource.getInstance().getDogsKinds(responseCallback);
        } else {
            //иначе читам БД пород
            DogKindsSQLiteDataSource.getInstance().getDogKinds(dbCallback);
        }
    }

    @Override
    public void getBreedsImage(String breedString, IWebCallback<String> callback) {
        WebBreedsDataSource.getInstance().getBreedsImage(breedString, callback);
    }

    @Override
    public int updateBreedDBWithUriImage(DogKind dogKind) {
        return DogKindsSQLiteDataSource.getInstance().updateBreedDBWithUriImage(dogKind);
    }
}
