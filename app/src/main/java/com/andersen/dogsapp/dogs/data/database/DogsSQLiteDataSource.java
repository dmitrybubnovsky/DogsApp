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
        dogs = new ArrayList<>(); // TODO: check: dogs.clear()
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
    public void addDog(int ownerID, int dogAge, int dogTall, int dogWeight, String dogName, String dogKind) {
//    public long addDog(int ownerID, int dogAge, int dogTall, int dogWeight, String dogImageString, String dogName, String dogKind) {
        db = DatabaseManager.getInstance().openDB();
        ContentValues cv = new ContentValues();
        cv.put(DogTable.OWNER_ID, ownerID);
        cv.put(DogTable.AGE, dogAge);
        cv.put(DogTable.TALL, dogTall);
        cv.put(DogTable.WEIGHT, dogWeight);
//        cv.put(DogTable.IMAGE, dogImageString);
        cv.put(DogTable.NAME, dogName);
        cv.put(DogTable.KIND, dogKind);

        long insertResult = db.insert(DogTable.TABLE_NAME, null, cv);
        DatabaseManager.getInstance().closeDB();
        if(insertResult == -1) {Log.d(TAG, "DogsSQLite: insert Dog has failed. Houston we have a problem!");}
    }

    // temporary implementation.
    // TODO: add new appropriate database and change to query request
//    @Override
//    public List<Dog> getOwnerDogs(Owner owner) {
//        List<Dog> ownerDogs = new ArrayList<>();
//        int[] dogsIds = owner.getDogsIds(); // get array of dogs' ids on single owner
//        int dogsQuantity = owner.getDogsQuantity();
//        for (int i = 0; i < dogsQuantity; i++) {
//            ownerDogs.add(getDogById(dogsIds[i]));
//        }
//        return ownerDogs;
//    }

//    @Override
//    public List<Dog> getOwnerDogs(Owner owner) {
//        loadDogs(owner.getOwnerId());
//        return dogs;
//    }

    // temporary implementation.
    private Dog getDogById(int dogId) {
        loadDogs();
        for (Dog dog : dogs) {
            if (dog.getDogId() == dogId) {
                return dog;
            }
        }
        throw new IndexOutOfBoundsException("Class DataRepository. Method getDogById. Not acceptable Id");
    }
}
