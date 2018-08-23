package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andersen.dogsapp.dogs.data.database.tables.DogKindTable;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IDatabaseCallback;
import com.andersen.dogsapp.dogs.data.web.ICallback;

import java.util.ArrayList;
import java.util.List;

public class DogKindsSQLiteDataSource  {
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

    public void getDogKinds(ICallback<List<DogKind>> dbCallback) {
        loadDogKinds();
        dbCallback.onResult(dogKinds);
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
    }

    private void addDogKindInLoop(DogKind dogKind) {
        ContentValues cv = new ContentValues();
        cv.put(DogKindTable.KIND, dogKind.getKind());
        cv.put(DogKindTable.IMAGE_URI, dogKind.getUriImageString());
        db.insert(DogKindTable.TABLE_NAME, null, cv);
    }

    public int updateBreedDBWithUriImage(DogKind dogKind) {
        db = DatabaseManager.getInstance().openDB();

        ContentValues cv = new ContentValues();
        cv.put(DogKindTable.IMAGE_URI, dogKind.getUriImageString());
        int result = db.update(DogKindTable.TABLE_NAME, cv, DogKindTable.ID + "=?",
                new String[]{String.valueOf(dogKind.getId())});
        DatabaseManager.getInstance().closeDB();
        return result;
    }

    private void loadDogKinds() {
        db = DatabaseManager.getInstance().openDB();
        dogKinds = new ArrayList<>();
        try {
            Cursor cursor = db.query(
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
            cursor.close();
        } finally {
            DatabaseManager.getInstance().closeDB();
        }
    }

}
