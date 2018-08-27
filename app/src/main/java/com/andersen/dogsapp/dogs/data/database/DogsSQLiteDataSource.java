package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.database.tables.DogTable;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;

import java.util.ArrayList;
import java.util.List;

public class DogsSQLiteDataSource implements IDogsDataSource {
    private static final String TAG = "#";
    private static DogsSQLiteDataSource dogsDataSource;
    private List<Dog> dogs;
    private SQLiteDatabase db;

    private DogsSQLiteDataSource() {
        loadDogs();
    }

    public static DogsSQLiteDataSource getInstance() {
        if (dogsDataSource == null) {
            dogsDataSource = new DogsSQLiteDataSource();
        }
        return dogsDataSource;
    }


    private void loadDogs() {
        // открываем БД
        db = DatabaseManager.getInstance().openDB();
        dogs = new ArrayList<>();

        try (Cursor cursor = db.query(DogTable.TABLE_NAME, null, null,
                null, null, null, null, null)) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Dog dog = new Dog();
                    dog.setDogId(cursor.getInt(cursor.getColumnIndex(DogTable.ID)));
                    dog.setDogName(cursor.getString(cursor.getColumnIndex(DogTable.NAME)));
                    dog.setDogOwnerId(cursor.getInt(cursor.getColumnIndex(DogTable.OWNER_ID)));
                    dog.setBreed(cursor.getString(cursor.getColumnIndex(DogTable.KIND)));
                    dog.setDogImageString(cursor.getString(cursor.getColumnIndex(DogTable.IMAGE)));
                    dog.setDogAge(cursor.getInt(cursor.getColumnIndex(DogTable.AGE)));
                    dog.setDogTall(cursor.getInt(cursor.getColumnIndex(DogTable.TALL)));
                    dog.setDogWeight(cursor.getInt(cursor.getColumnIndex(DogTable.WEIGHT)));
                    dogs.add(dog);
                    cursor.moveToNext();
                }
            } else {
                Log.d(TAG, "loadDogs. cursor.count == 0");
            }
            cursor.close();
        } finally { // закрываем БД
            DatabaseManager.getInstance().closeDB();
        }
    }

    @Override
    public List<Dog> getDogs() {
        loadDogs();
        return dogs;
    }

    @Override
    public List<Dog> getOwnerDogs(Owner owner) {
        // открываю БД
        db = DatabaseManager.getInstance().openDB();

        int dogOwnerId = owner.getOwnerId();

        List<Dog> ownerDogs = new ArrayList<>();

        try (Cursor cursor = db.query(DogTable.TABLE_NAME, null, DogTable.OWNER_ID + "=?",
                new String[]{String.valueOf(dogOwnerId)}, null, null, null,
                null)) {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Dog dog = new Dog();
                    dog.setDogId(cursor.getInt(cursor.getColumnIndex(DogTable.ID)));
                    dog.setDogName(cursor.getString(cursor.getColumnIndex(DogTable.NAME)));
                    dog.setDogOwnerId(cursor.getInt(cursor.getColumnIndex(DogTable.OWNER_ID)));
                    dog.setBreed(cursor.getString(cursor.getColumnIndex(DogTable.KIND)));
                    dog.setDogImageString(cursor.getString(cursor.getColumnIndex(DogTable.IMAGE)));
                    dog.setDogAge(cursor.getInt(cursor.getColumnIndex(DogTable.AGE)));
                    dog.setDogTall(cursor.getInt(cursor.getColumnIndex(DogTable.TALL)));
                    dog.setDogWeight(cursor.getInt(cursor.getColumnIndex(DogTable.WEIGHT)));
                    ownerDogs.add(dog);
                    cursor.moveToNext();
                }
            }
            return ownerDogs;
        } finally {
            DatabaseManager.getInstance().closeDB();
        }
    }

    @Override
    public Dog addDog(Dog dog) {
        db = DatabaseManager.getInstance().openDB();

        ContentValues cv = new ContentValues();
        cv.put(DogTable.OWNER_ID, dog.getOwnerId());
        cv.put(DogTable.AGE, dog.getDogAge());
        cv.put(DogTable.TALL, dog.getDogTall());
        cv.put(DogTable.WEIGHT, dog.getDogWeight());
        cv.put(DogTable.IMAGE, dog.getDogImageString());
        cv.put(DogTable.NAME, dog.getDogName());
        cv.put(DogTable.KIND, dog.getBreed());

        long insertResult = db.insert(DogTable.TABLE_NAME, null, cv);
        DatabaseManager.getInstance().closeDB();

        if (insertResult != -1) {
            dog.setDogId((int) insertResult);
            return dog;
        } else {
            return new Dog("addDog method. Error of Dog creating");
        }
    }
}
