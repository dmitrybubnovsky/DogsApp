package com.andersen.dogsapp.dogs.data.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.database.tables.DogTable;

import java.util.ArrayList;
import java.util.List;

public class DogsSQLiteDataSource implements IDogsDataSource {
    private static DogsSQLiteDataSource dogsDataSource;
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
        Cursor cursor = null;
        try {
            cursor = db.query(
                    DogTable.TABLE_NAME, null, null, null, null, null,
                    null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Dog dog = new Dog();
                dog.setDogId(cursor.getInt(cursor.getColumnIndex(DogTable.ID)));
                dog.setDogName(cursor.getString(cursor.getColumnIndex(DogTable.NAME)));
                dog.setDogKind(cursor.getString(cursor.getColumnIndex(DogTable.KIND)));
                dog.setDogImageString(cursor.getString(cursor.getColumnIndex(DogTable.IMAGE)));
                dog.setDogAge(cursor.getInt(cursor.getColumnIndex(DogTable.AGE)));
                dog.setDogTall(cursor.getInt(cursor.getColumnIndex(DogTable.TALL)));
                dog.setDogWeight(cursor.getInt(cursor.getColumnIndex(DogTable.WEIGHT)));
                dogs.add(dog);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
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
