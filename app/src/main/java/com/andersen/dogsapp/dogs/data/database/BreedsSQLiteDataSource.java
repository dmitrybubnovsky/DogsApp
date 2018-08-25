package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andersen.dogsapp.dogs.data.database.tables.BreedTable;
import com.andersen.dogsapp.dogs.data.entities.Breed;
import com.andersen.dogsapp.dogs.data.web.ICallback;

import java.util.ArrayList;
import java.util.List;

public class BreedsSQLiteDataSource {
    private static final String TAG = "#";
    private static BreedsSQLiteDataSource breedsDataSource;
    private SQLiteDatabase db;
    private List<Breed> breeds;

    private BreedsSQLiteDataSource() {
        loadBreeds();
    }

    public static BreedsSQLiteDataSource getInstance() {
        if (breedsDataSource == null) {
            breedsDataSource = new BreedsSQLiteDataSource();
        }
        return breedsDataSource;
    }

    public void getBreeds(ICallback<List<Breed>> dbCallback) {
        loadBreeds();
        dbCallback.onResult(breeds);
    }

    public boolean isBreedsDatabaseEmpty() {
        db = DatabaseManager.getInstance().openDB();
        try (Cursor cursor = db.query(
                BreedTable.TABLE_NAME,
                null, null, null, null, null, null, null)) {
            return cursor.getCount() == 0 || !cursor.moveToNext();
        } finally {
            DatabaseManager.getInstance().closeDB();
        }
    }

    public void addBreedsToDatabase(List<Breed> breeds) {
        db = DatabaseManager.getInstance().openDB();
        for (Breed breed : breeds) {
            insertBreedInLoop(breed);
        }
        DatabaseManager.getInstance().closeDB();
    }

    private void insertBreedInLoop(Breed breed) {
        ContentValues cv = new ContentValues();
        cv.put(BreedTable.BREED, breed.getBreedString());
        cv.put(BreedTable.IMAGE_URI, breed.getUriImageString());
        db.insert(BreedTable.TABLE_NAME, null, cv);
    }

    public void updateBreedDBWithUriImage(Breed breed) {
        db = DatabaseManager.getInstance().openDB();

        ContentValues cv = new ContentValues();
        cv.put(BreedTable.IMAGE_URI, breed.getUriImageString());
        db.update(BreedTable.TABLE_NAME, cv, BreedTable.ID + "=?",
                new String[]{String.valueOf(breed.getId())});
        DatabaseManager.getInstance().closeDB();
    }

    private void loadBreeds() {
        db = DatabaseManager.getInstance().openDB();
        breeds = new ArrayList<>();
        try (Cursor cursor = db.query(
                BreedTable.TABLE_NAME,
                null, null, null, null, null, null, null)) {

            // проверяем БД, если там пусто то список пород пустой
            if (cursor.getCount() == 0 || !cursor.moveToNext()) {
                breeds.clear();
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Breed breed = new Breed();
                    breed.setId(cursor.getInt(cursor.getColumnIndex(BreedTable.ID)));
                    breed.setBreedString(cursor.getString(cursor.getColumnIndex(BreedTable.BREED)));
                    breed.setImageString(cursor.getString(cursor.getColumnIndex(BreedTable.IMAGE_URI)));
                    breeds.add(breed);
                    cursor.moveToNext();
                }
            }
        } finally {
            DatabaseManager.getInstance().closeDB();
        }
    }
}
