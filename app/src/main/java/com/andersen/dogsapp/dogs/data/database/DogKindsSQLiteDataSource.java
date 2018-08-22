package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.database.tables.DogKindTable;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IDatabaseCallback;

import java.util.ArrayList;
import java.util.List;

public class DogKindsSQLiteDataSource {
    private static final String TAG = "#";
    private static DogKindsSQLiteDataSource dogKindsDataSource;
    private SQLiteDatabase db;
    private List<DogKind> dogKinds;

    private DogKindsSQLiteDataSource() {
        loadDogKinds();
    }

    public static DogKindsSQLiteDataSource getInstance() {
        if (dogKindsDataSource == null) {
            dogKindsDataSource = new DogKindsSQLiteDataSource();
        }
        return dogKindsDataSource;
    }

    public void getDogKinds(IDatabaseCallback<List<DogKind>> dbCallback) {
        loadDogKinds();
        Log.d(TAG, "dogKinds third id = "+dogKinds.get(3).getId());
        dbCallback.onDatabaseCallback(dogKinds);
    }

    public boolean isBreedDatabaseEmpty() {
        db = DatabaseManager.getInstance().openDB();
        Cursor cursor = null;
        try {
            cursor = db.query(
                    DogKindTable.TABLE_NAME,
                    null, null, null, null, null, null, null);
            return cursor.getCount() == 0 || !cursor.moveToNext();
        } finally {
            cursor.close();
            DatabaseManager.getInstance().closeDB();
        }
    }

    public void addBreedsToDatabase(List<DogKind> breeds) {
        db = DatabaseManager.getInstance().openDB();
        for (DogKind dogKind : breeds) {
            addDogKindInLoop(dogKind);
        }
        DatabaseManager.getInstance().closeDB();
        Log.d(TAG, "addBreedsToDatabase called. Breeds DB was created ---------------- !!!!!!!!!!!");

    }

    private void addDogKindInLoop(DogKind dogKind) {
        ContentValues cv = new ContentValues();
        cv.put(DogKindTable.KIND, dogKind.getKind());
        cv.put(DogKindTable.IMAGE_URI, dogKind.getUriImageString());
        long id = db.insert(DogKindTable.TABLE_NAME, null, cv);
//        Log.d(TAG, "id " + id);
//        Log.d(TAG, "dogKindInstance " + dogKind.getKind());
//        Log.d(TAG, "UriImageString " + dogKind.getUriImageString());
//        Log.d(TAG, "-----------------------------------");
    }

    public DogKind addDogKind(DogKind dogKind) {
        db = DatabaseManager.getInstance().openDB();

        ContentValues cv = new ContentValues();
        cv.put(DogKindTable.KIND, dogKind.getKind());
        cv.put(DogKindTable.IMAGE_URI, dogKind.getUriImageString());
        long insertResult = db.insert(DogKindTable.TABLE_NAME, null, cv);
        DatabaseManager.getInstance().closeDB();

        if (insertResult != -1) {
            dogKind.setId((int) insertResult);
            return dogKind;
        } else {
            return null; //new DogKind()
        }
    }

    public int updateBreedDBWithUriImage(DogKind dogKind) {
        db = DatabaseManager.getInstance().openDB();

        ContentValues cv = new ContentValues();
        cv.put(DogKindTable.IMAGE_URI, dogKind.getUriImageString());
        Log.d(TAG, "--------------    updateBreedDBWithUriImage DogKindTable.ID = "+dogKind.getId());
        int result = db.update(DogKindTable.TABLE_NAME, cv, DogKindTable.ID + "=?",
                new String[]{String.valueOf(dogKind.getId())});
        DatabaseManager.getInstance().closeDB();
        return result;
    }

    private void loadDogKinds() {
        db = DatabaseManager.getInstance().openDB();
        Cursor cursor = null;
        dogKinds = new ArrayList<>();
        try {
            cursor = db.query(
                    DogKindTable.TABLE_NAME,
                    null, null, null, null, null, null, null);
            // проверяем БД, если там пусто то список пород пустой
            if (cursor.getCount() == 0 || !cursor.moveToNext()) {
                dogKinds.clear();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    DogKind dogKind = new DogKind();
                    dogKind.setId(cursor.getInt(cursor.getColumnIndex(DogKindTable.ID)));
                    dogKind.setKind(cursor.getString(cursor.getColumnIndex(DogKindTable.KIND)));
                    dogKind.setImageString(cursor.getString(cursor.getColumnIndex(DogKindTable.IMAGE_URI)));
                    dogKinds.add(dogKind);
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
            DatabaseManager.getInstance().closeDB();
        }
    }

}
