package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andersen.dogsapp.dogs.data.database.tables.DogKindTable;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.web.ICallback;

import java.util.ArrayList;
import java.util.List;

public class BreedsSQLiteDataSource {
    private static final String TAG = "#";
    private static BreedsSQLiteDataSource dogKindsDataSource;
    private SQLiteDatabase db;
    private List<DogKind> dogKinds;

    private BreedsSQLiteDataSource() {
        loadDogKinds();
    }

    public static BreedsSQLiteDataSource getInstance() {
        if (dogKindsDataSource == null) {
            dogKindsDataSource = new BreedsSQLiteDataSource();
        }
        return dogKindsDataSource;
    }

    public void getDogKinds(ICallback<List<DogKind>> dbCallback) {
        loadDogKinds();
        dbCallback.onResult(dogKinds);
    }

    public boolean isDogKindsDatabaseEmpty() {
        db = DatabaseManager.getInstance().openDB();
        try (Cursor cursor = db.query(
                DogKindTable.TABLE_NAME,
                null, null, null, null, null, null, null)) {
            return cursor.getCount() == 0 || !cursor.moveToNext();
        } finally {
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

    public void updateBreedDBWithUriImage(DogKind dogKind) {
        db = DatabaseManager.getInstance().openDB();

        ContentValues cv = new ContentValues();
        cv.put(DogKindTable.IMAGE_URI, dogKind.getUriImageString());
        db.update(DogKindTable.TABLE_NAME, cv, DogKindTable.ID + "=?",
                new String[]{String.valueOf(dogKind.getId())});
        DatabaseManager.getInstance().closeDB();
    }

    private void loadDogKinds() {
        db = DatabaseManager.getInstance().openDB();
        dogKinds = new ArrayList<>();
        try (Cursor cursor = db.query(
                DogKindTable.TABLE_NAME,
                null, null, null, null, null, null, null)) {

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
            DatabaseManager.getInstance().closeDB();
        }
    }
}
