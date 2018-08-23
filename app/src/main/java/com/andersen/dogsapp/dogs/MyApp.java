package com.andersen.dogsapp.dogs;

import android.app.Application;

import com.andersen.dogsapp.dogs.data.DataRepository;
import com.andersen.dogsapp.dogs.data.DogKindRepository;
import com.andersen.dogsapp.dogs.data.OwnersDataRepository;
import com.andersen.dogsapp.dogs.data.database.DBHelper;
import com.andersen.dogsapp.dogs.data.database.DatabaseManager;
import com.andersen.dogsapp.dogs.data.database.DogsSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.database.OwnersSQLiteDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;

public class MyApp extends Application {
    public static final String TAG = "# MyApp";

    @Override
    public void onCreate() {
        super.onCreate();

        DBHelper dbHelper = DBHelper.getInstance(this);
        DatabaseManager.initInstance(dbHelper);
        IOwnersDataSource iOwnersDataSource = OwnersDataRepository.getInstance();
        IDogsDataSource iDogsDataSource = DogsSQLiteDataSource.getInstance();
        IBreedsDataSource iBreedsDataSource = DogKindRepository.getInstance();
        DataRepository.init(iOwnersDataSource, iDogsDataSource, iBreedsDataSource);
    }
}
