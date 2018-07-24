package com.andersen.dogsapp.dogs.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andersen.dogsapp.dogs.Owner;
import com.andersen.dogsapp.dogs.data.DogKind;
import com.andersen.dogsapp.dogs.data.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.database.tables.DogTable;
import com.andersen.dogsapp.dogs.data.database.tables.OwnerTable;
import com.andersen.dogsapp.dogs.data.database.wrappers.OwnersCursorWrapper;

import java.util.ArrayList;
import java.util.List;

public class OwnersSQLiteDataSource implements IOwnersDataSource {
    private static OwnersSQLiteDataSource ownersDataSource;
    private static final String TAG = "#";

    private OwnersCursorWrapper ownersCursor;
    private List<Owner> owners;

    private OwnersSQLiteDataSource(DBHelper dbHelper) {
        loadOwners(dbHelper);
    }

    public static OwnersSQLiteDataSource getInstance(DBHelper dbHelper) {
        if (ownersDataSource == null) {
            ownersDataSource = new OwnersSQLiteDataSource(dbHelper);
        }
        return ownersDataSource;
    }

    @Override
    public List<Owner> getOwners() {
        return owners;
    }

    private void loadOwners(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        addSomeDB(dbHelper);

        owners = new ArrayList<>();
        ownersCursor = queryOwners(db);
        try {
            ownersCursor.moveToNext();
            while (!ownersCursor.isAfterLast()) {
                owners.add(ownersCursor.getOwner());
                ownersCursor.moveToNext();
            }
        } finally {
            ownersCursor.close();
        }
        dbHelper.close();
    }

    private OwnersCursorWrapper queryOwners(SQLiteDatabase db) {
        Log.d(TAG, "queryOwners");
        Cursor cursor = db.query(
                OwnerTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new OwnersCursorWrapper(cursor);
    }

    private void addSomeDB(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(OwnerTable.TABLE_NAME, null, null,null,null,null,null);
        if( cursor.getCount() == 0) {
            addOwner(dbHelper, 1, "Andy", "Garcia", DogKind.AMERICAN_FOXHOUND, "102 103");
            addOwner(dbHelper, 2, "Tom", "Cruis", DogKind.BERGER_PICKARD, "101");
            addOwner(dbHelper, 3, "Robert", "De Niro", DogKind.CHESAPEAKE, "104 105 106");
            addOwner(dbHelper, 4, "Al", "Pacino", DogKind.ENGLISH_COONHOUND, "107 108 109 110");
            addOwner(dbHelper, 5, "Garry", "Potter", DogKind.MUNSTERLANDER, "111");
            addOwner(dbHelper, 6, "Will", "Smith", DogKind.SAINT_BERNARD, "112 113 114 116");
            addOwner(dbHelper, 7, "Snejana", "Denisovna", DogKind.GERMAN_SHEPHERD, "117 118");
            addOwner(dbHelper, 8, "James", "Bond", DogKind.SIBERIAN_HUSKY, "119");
            addOwner(dbHelper, 9, "Christian", "Baile", DogKind.POCKET_BEAGLE, "120 121 122");
            addOwner(dbHelper, 10, "Anjelina", "Jolie", DogKind.WATER_SPANIEL, "123 124 125");

            addDog(dbHelper, 101, "Palkan", DogKind.AMERICAN_FOXHOUND, "shepherd", 201, 55, 65);
            addDog(dbHelper, 102, "Drujok", DogKind.BERGER_PICKARD, "shepherd", 201, 55, 65);
            addDog(dbHelper, 103, "Sharik", DogKind.CHINOOK, "shepherd", 201, 55, 65);
            addDog(dbHelper, 104, "Greezly", DogKind.ENGLISH_COONHOUND, "shepherd", 201, 55, 65);
            addDog(dbHelper, 105, "Mickey", DogKind.MUNSTERLANDER, "shepherd", 201, 55, 65);
            addDog(dbHelper, 106, "Plooto", DogKind.GERMAN_SHEPHERD, "shepherd", 201, 55, 65);
            addDog(dbHelper, 107, "Muhtar", DogKind.POCKET_BEAGLE, "shepherd", 201, 55, 65);
            addDog(dbHelper, 108, "Kachtanka", DogKind.SAINT_BERNARD, "shepherd", 201, 55, 65);
            addDog(dbHelper, 109, "Leopold", DogKind.SHEPHERD, "shepherd", 201, 55, 65);
            addDog(dbHelper, 110, "Barboss", DogKind.WATER_SPANIEL, "shepherd", 201, 55, 65);
            addDog(dbHelper, 111, "Joochka", DogKind.GERMAN_SHEPHERD, "shepherd", 201, 55, 65);
            addDog(dbHelper, 112, "Belka", DogKind.MUNSTERLANDER, "shepherd", 201, 55, 65);
            addDog(dbHelper, 113, "Strelka", DogKind.SAINT_BERNARD, "shepherd", 201, 55, 65);
            addDog(dbHelper, 114, "Laika", DogKind.CHINOOK, "shepherd", 201, 55, 65);
            addDog(dbHelper, 115, "Palma", DogKind.POCKET_BEAGLE, "shepherd", 201, 55, 65);
            addDog(dbHelper, 116, "Gerda", DogKind.WATER_SPANIEL, "shepherd", 201, 55, 65);
            addDog(dbHelper, 117, "Sally", DogKind.AMERICAN_FOXHOUND, "shepherd", 201, 55, 65);
            addDog(dbHelper, 118, "Joochka", DogKind.CHESAPEAKE, "shepherd", 201, 55, 65);
            addDog(dbHelper, 119, "Kachtanka", DogKind.GERMAN_SHEPHERD, "shepherd", 201, 55, 65);
            addDog(dbHelper, 120, "Pluto", DogKind.ENGLISH_COONHOUND, "shepherd", 201, 55, 65);
            addDog(dbHelper, 121, "Strelka", DogKind.STANDARD_SCHNAUZER, "shepherd", 201, 55, 65);
            addDog(dbHelper, 122, "Mickey", DogKind.SIBERIAN_HUSKY, "shepherd", 201, 55, 65);
            addDog(dbHelper, 123, "Greazy", DogKind.SHEPHERD, "shepherd", 201, 55, 65);
            addDog(dbHelper, 124, "Muhtar", DogKind.POCKET_BEAGLE, "shepherd", 201, 55, 65);
            addDog(dbHelper, 125, "Leopold", DogKind.STANDARD_SCHNAUZER, "shepherd", 201, 55, 65);
        }
        cursor.close();
    }

    private void addOwner(DBHelper dbHelper, int id, String name, String surname, String preferedKind, String dogsIds) {
        ContentValues cv = new ContentValues();
        cv.put(OwnerTable.ID, id);
        cv.put(OwnerTable.NAME, name);
        cv.put(OwnerTable.SURNAME, surname);
        cv.put(OwnerTable.PREFERED_DOGS_KIND, preferedKind);
        cv.put(OwnerTable.DOGS_IDS, dogsIds);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(OwnerTable.TABLE_NAME, null, cv);
    }

    private void addDog(DBHelper dbHelper, int id, String name, String kind, String image, int age, int weight, int tall) {
        ContentValues cv = new ContentValues();
        cv.put(DogTable.ID, id);
        cv.put(DogTable.NAME, name);
        cv.put(DogTable.KIND, kind);
        cv.put(DogTable.IMAGE, image);
        cv.put(DogTable.AGE, age);
        cv.put(DogTable.WEIGHT, weight);
        cv.put(DogTable.TALL, tall);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert(DogTable.TABLE_NAME, null, cv);
    }
}
