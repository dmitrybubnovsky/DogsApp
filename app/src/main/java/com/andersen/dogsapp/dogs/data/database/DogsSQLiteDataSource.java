package com.andersen.dogsapp.dogs.data.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.Owner;
import com.andersen.dogsapp.dogs.data.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.database.tables.DogTable;
import com.andersen.dogsapp.dogs.data.database.wrappers.DogsCursorWrapper;
import java.util.ArrayList;
import java.util.List;

public class DogsSQLiteDataSource implements IDogsDataSource {
    private static DogsSQLiteDataSource dogsDataSource;
    private DogsCursorWrapper dogsCursor;
    private List<Dog> dogs;
    private SQLiteDatabase db;


    private DogsSQLiteDataSource(DBHelper dbHelper) {
        db = dbHelper.getWritableDatabase();
        loadDogs();
    }

    public static DogsSQLiteDataSource getInstance(DBHelper dbHelper) {
        if (dogsDataSource == null) {
            dogsDataSource = new DogsSQLiteDataSource(dbHelper);
        }
        return dogsDataSource;
    }

    private void loadDogs() {
        dogs = new ArrayList<>();
        try {
            dogsCursor = queryDogs();
            dogsCursor.moveToNext();
            while (!dogsCursor.isAfterLast()) {
                dogs.add(dogsCursor.getDog());
                dogsCursor.moveToNext();
            }
        } finally {
            dogsCursor.close();
        }
    }

    private DogsCursorWrapper queryDogs() {
        Cursor cursor = db.query(
                DogTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new DogsCursorWrapper(cursor);
    }

    // temporary implementation.
    // TODO: add new appropriate database and change to query request
    @Override
    public List<Dog> getOwnerDogs(Owner owner) {
        List<Dog> ownerDogs = new ArrayList<>();
        int[] dogsIds = owner.getDogsIds(); // get array of dogs' ids on single owner
        int dogsQuantity = owner.getDogsQuantity();
        for (int i = 0; i < dogsQuantity; i++) {
            ownerDogs.add(getDogById(dogsIds[i]));
        }
        return ownerDogs;
    }

    // temporary implementation.
    private Dog getDogById(int dogId) {
        for (Dog dog : dogs) {
            if (dog.getDogId() == dogId) {
                return dog;
            }
        }
        throw new IndexOutOfBoundsException("Class DataRepository. Method getDogById. Not acceptable Id");
    }
}
