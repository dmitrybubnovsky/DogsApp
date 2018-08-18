package com.andersen.dogsapp.dogs;

import android.app.Application;

import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.web.ICallback;
import com.andersen.dogsapp.dogs.data.web.WebBreedsDataSource;

import java.util.List;

public class MyApp extends Application {

    public List<DogKind> dogBreedsList;

    @Override
    public void onCreate() {
        super.onCreate();


        DBHelper dbHelper = DBHelper.getInstance(this);
        IOwnersDataSource iOwnersDataSource = OwnersSQLiteDataSource.getInstance(dbHelper);
        IDogsDataSource iDogsDataSource = DogsSQLiteDataSource.getInstance(dbHelper);
        IBreedsDataSource iBreedsDataSource = WebBreedsDataSource.getInstance();
        DataRepository.init(iOwnersDataSource, iDogsDataSource, iBreedsDataSource);

        DataRepository.get().getDogKinds(new ICallback<List<DogKind>>() {
            @Override
            public void onResponseICallback(List<DogKind> dogBreeds) {
                dogBreedsList = dogBreeds;
//                runOnUiThread(() -> dogKinds = dogBreeds);
            }
        });
    }
}
