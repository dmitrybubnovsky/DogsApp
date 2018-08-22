package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andersen.dogsapp.dogs.data.database.tables.OwnerTable;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;

import java.util.ArrayList;
import java.util.List;

public class OwnersSQLiteDataSource implements IOwnersDataSource {
    private static final String TAG = "#";
    private static OwnersSQLiteDataSource ownersDataSource;
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

    private void loadOwners() {
        db = DatabaseManager.getInstance().openDB();
        owners = new ArrayList<>();
        try (Cursor cursor = db.query( OwnerTable.TABLE_NAME,
                null, null, null, null, null, null, null)) {// проверяем БД, если там пусто то список владельцев пустой
            if (cursor.getCount() == 0 || !cursor.moveToNext()) {
                owners.clear();
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
            cursor.close();
        } finally {
            DatabaseManager.getInstance().closeDB();
        }
    }

    // для формы
    @Override
    public Owner addOwner(Owner owner) {
        db = DatabaseManager.getInstance().openDB();

        ContentValues cv = new ContentValues();
        cv.put(OwnerTable.NAME, owner.getOwnerName());
        cv.put(OwnerTable.SURNAME, owner.getOwnerSurname());
        cv.put(OwnerTable.PREFERED_DOGS_KIND, owner.getPreferedDogsKind());
        long insertResult = db.insert(OwnerTable.TABLE_NAME, null, cv);
        DatabaseManager.getInstance().closeDB();

        if (insertResult != -1) {
            owner.setOwnerId((int) insertResult);
            return owner;
        } else {
            Log.d(TAG, "OwnersSQLiteDataSource. addOwner: Owners was NOT added");
            return new Owner();
        }
    }
}
