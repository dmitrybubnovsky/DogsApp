package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.database.tables.DogTable;

import java.util.ArrayList;
import java.util.List;

public class DogsSQLiteDataSource implements IDogsDataSource {
    private static final String TAG = "#";
    private static DogsSQLiteDataSource dogsDataSource;

    private List<Dog> dogs;
    private SQLiteDatabase db;

    private DogsSQLiteDataSource(DBHelper dbHelper) {
        loadDogs();
    }

    public static DogsSQLiteDataSource getInstance(DBHelper dbHelper) {
        if (dogsDataSource == null) {
            dogsDataSource = new DogsSQLiteDataSource(dbHelper);
        }
        return dogsDataSource;
    }


    private void loadDogs() {
        // открываю БД
        db = DatabaseManager.getInstance().openDB();
        dogs = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.query(DogTable.TABLE_NAME, null, null,
                    null, null, null, null,
                    null);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Dog dog = new Dog();
                    dog.setDogId(cursor.getInt(cursor.getColumnIndex(DogTable.ID)));
                    dog.setDogName(cursor.getString(cursor.getColumnIndex(DogTable.NAME)));
                    dog.setDogOwnerId(cursor.getInt(cursor.getColumnIndex(DogTable.OWNER_ID)));
                    dog.setDogKind(cursor.getString(cursor.getColumnIndex(DogTable.KIND)));
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
        } finally {
            cursor.close();
            DatabaseManager.getInstance().closeDB();
        }
    }

    @Override
    public List<Dog> getDogs(){
        loadDogs();
        return dogs;
    }

    @Override
    public List<Dog> getOwnerDogs(Owner owner) {
        int dogOwnerId = owner.getOwnerId();
        // открываю БД
        db = DatabaseManager.getInstance().openDB();
        List<Dog> ownerDogs = new ArrayList<>(); // TODO: check: dogs.clear()
        Cursor cursor = null;
        try {
            cursor = db.query(DogTable.TABLE_NAME, null, DogTable.OWNER_ID + "=?",
                    new String[]{String.valueOf(dogOwnerId)}, null, null, null,
                    null);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Dog dog = new Dog();
                    dog.setDogId(cursor.getInt(cursor.getColumnIndex(DogTable.ID)));
                    dog.setDogName(cursor.getString(cursor.getColumnIndex(DogTable.NAME)));
                    dog.setDogOwnerId(cursor.getInt(cursor.getColumnIndex(DogTable.OWNER_ID)));
                    dog.setDogKind(cursor.getString(cursor.getColumnIndex(DogTable.KIND)));
                    dog.setDogImageString(cursor.getString(cursor.getColumnIndex(DogTable.IMAGE)));
                    dog.setDogAge(cursor.getInt(cursor.getColumnIndex(DogTable.AGE)));
                    dog.setDogTall(cursor.getInt(cursor.getColumnIndex(DogTable.TALL)));
                    dog.setDogWeight(cursor.getInt(cursor.getColumnIndex(DogTable.WEIGHT)));
                    ownerDogs.add(dog);
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
            // закрываю БД
            DatabaseManager.getInstance().closeDB();
            return ownerDogs;
        }
    }

    // для формы
//    public void addDog(int ownerID, int dogAge, int dogTall, int dogWeight, String dogName) {
    @Override
    public Dog addDog(Dog dog) {
        db = DatabaseManager.getInstance().openDB();
        ContentValues cv = new ContentValues();
        cv.put(DogTable.OWNER_ID, dog.getOwnerId());  Log.d(TAG, "addDog: DOG_ID " + dog.getDogId());
        cv.put(DogTable.AGE, dog.getDogAge());
        cv.put(DogTable.TALL, dog.getDogTall());
        cv.put(DogTable.WEIGHT, dog.getDogWeight());
//        cv.put(DogTable.IMAGE, dogImageString);
        cv.put(DogTable.NAME, dog.getDogName());
//        cv.put(DogTable.KIND, dogKind);

        long insertResult = db.insert(DogTable.TABLE_NAME, null, cv);
        DatabaseManager.getInstance().closeDB();

        if (insertResult != -1) {
            dog.setDogId((int)insertResult);
            return dog;
        } else {
            Log.d(TAG, "DogsSQLiteDataSource. addDog: Dog was NOT added");
            return new Dog();
        }
    }
}
