package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

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
//            String dogsIdsString = cursor.getString(cursor.getColumnIndex(OwnerTable.DOGS_IDS));
//            owner.setDogsIds(getIntArrayFromString(dogsIdsString));
        }finally{
            cursor.close();
        }
        DatabaseManager.getInstance().closeDB();
        Log.d(TAG,"owner name "+ owner.getOwnerFullName());
        return owner;
    }

    private void loadOwners() {
        db = DatabaseManager.getInstance().openDB();
        //  addSomeDB();
        Cursor cursor = null;
        owners = new ArrayList<>();
        try {
            cursor = db.query(
                    OwnerTable.TABLE_NAME,
                    null, null, null, null, null, null, null);
            // проверяем БД, если там пусто то список владельцев null
            if (cursor.getCount() == 0 || !cursor.moveToNext()) {
                owners = null;
                Log.d("#", "loadOwners: owners = null");
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
            Log.d(TAG, " ----- Owners DB has "+cursor.getCount()+" rows");

            cursor.close();
        }

        Cursor cursor1 = null;
        try {
            cursor1 = db.query(DogTable.TABLE_NAME, null, null, null, null, null, null, null);
            Log.d(TAG, "-------- Dogs DB has "+cursor1.getCount()+" rows");

        } finally {
            cursor1.close();
        }


        DatabaseManager.getInstance().closeDB();
    }

    // для формы
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

    // для тестовой БД autoincrement
    private void addDog(int ownerId, String name, String kind, String image, int age, int weight, int tall) {
        ContentValues cv = new ContentValues();
        cv.put(DogTable.OWNER_ID, ownerId);
        cv.put(DogTable.NAME, name);
        cv.put(DogTable.KIND, kind);
        cv.put(DogTable.IMAGE, image);
        cv.put(DogTable.AGE, age);
        cv.put(DogTable.WEIGHT, weight);
        cv.put(DogTable.TALL, tall);
        db.insert(DogTable.TABLE_NAME, null, cv);
    }
}
