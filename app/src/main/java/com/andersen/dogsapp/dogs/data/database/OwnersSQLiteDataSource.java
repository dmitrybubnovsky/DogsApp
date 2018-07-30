package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.database.tables.DogTable;
import com.andersen.dogsapp.dogs.data.database.tables.OwnerTable;

import java.util.ArrayList;
import java.util.List;

public class OwnersSQLiteDataSource implements IOwnersDataSource {
    private static OwnersSQLiteDataSource ownersDataSource;
    private static final String TAG = "#";

    private SQLiteDatabase db;
    private List<Owner> owners;

    private OwnersSQLiteDataSource(DBHelper dbHelper) {
        DatabaseManager.initInstance(dbHelper);
        loadOwners();
    }

    public static OwnersSQLiteDataSource getInstance(DBHelper dbHelper) {
        if (ownersDataSource == null) {
            ownersDataSource = new OwnersSQLiteDataSource(dbHelper);
        }
        return ownersDataSource;
    }

    @Override
    public List<Owner> getOwners() {
        loadOwners();
        return owners;
    }

    public Owner getLastAddedOwner() {
        db = DatabaseManager.getInstance().openDB();
        Owner owner = new Owner();
        Cursor cursor = null;
        try {
            cursor = db.query(OwnerTable.TABLE_NAME, null, null, null, null,
                    null, OwnerTable.ID+" DESC", "1");
            cursor.moveToFirst();
            owner.setOwnerId(cursor.getInt(cursor.getColumnIndex(OwnerTable.ID)));
            owner.setOwnerName(cursor.getString(cursor.getColumnIndex(OwnerTable.NAME)));
            owner.setOwnerSurname(cursor.getString(cursor.getColumnIndex(OwnerTable.SURNAME)));
            owner.setPreferedDogsKind(cursor.getString(cursor.getColumnIndex(OwnerTable.PREFERED_DOGS_KIND)));
        }finally{
            cursor.close();
            DatabaseManager.getInstance().closeDB();
        }
        return owner;
    }

    private void loadOwners() { //List<Dog> dogs
        db = DatabaseManager.getInstance().openDB();
        Cursor cursor = null;
        owners = new ArrayList<>();
        try {
            cursor = db.query(
                    OwnerTable.TABLE_NAME,
                    null, null, null, null, null, null, null);
            // проверяем БД, если там пусто то список владельцев пустой
            if (cursor.getCount() == 0 || !cursor.moveToNext()) {
                owners.clear();
                Log.d("#", "loadOwners: cursor is empty");
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Owner owner = new Owner();
                    owner.setOwnerId(cursor.getInt(cursor.getColumnIndex(OwnerTable.ID)));
                    owner.setOwnerName(cursor.getString(cursor.getColumnIndex(OwnerTable.NAME)));
                    owner.setOwnerSurname(cursor.getString(cursor.getColumnIndex(OwnerTable.SURNAME)));
                    owner.setPreferedDogsKind(cursor.getString(cursor.getColumnIndex(OwnerTable.PREFERED_DOGS_KIND)));
                    owners.add(owner);
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
            DatabaseManager.getInstance().closeDB();
        }
    }

    // для формы
    @Override
    public long addOwner(String name, String surname, String preferedKind) {
        db = DatabaseManager.getInstance().openDB();
        ContentValues cv = new ContentValues();
        cv.put(OwnerTable.NAME, name);
        cv.put(OwnerTable.SURNAME, surname);
        cv.put(OwnerTable.PREFERED_DOGS_KIND, preferedKind);
        long insertResult = db.insert(OwnerTable.TABLE_NAME, null, cv);
        DatabaseManager.getInstance().closeDB();

        // TEST
        if (insertResult != -1) {
            Log.d(TAG, "addOwner: new owners is inserted. Raw "+insertResult);
        } else {
            Log.d(TAG, "addOwner: Owners was NOT added");
        }
        return insertResult;
    }
}
