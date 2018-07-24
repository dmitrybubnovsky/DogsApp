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

    private SQLiteDatabase db;
    private OwnersCursorWrapper ownersCursor;
    private List<Owner> owners;

    private OwnersSQLiteDataSource(DBHelper dbHelper) {
        db = dbHelper.getWritableDatabase();
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
        return owners;
    }

    private void loadOwners() {
        addSomeDB();

        owners = new ArrayList<>();
        ownersCursor = queryOwners();
        try {
            ownersCursor.moveToNext();
            while (!ownersCursor.isAfterLast()) {
                owners.add(ownersCursor.getOwner());
                ownersCursor.moveToNext();
            }
        } finally {
            ownersCursor.close();
        }
    }

    private OwnersCursorWrapper queryOwners() {
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

    private void addSomeDB() {
        Cursor cursor = db.query(OwnerTable.TABLE_NAME, null, null,null,null,null,null);
        if( cursor.getCount() == 0) {
            addOwner(1, "Andy", "Garcia", DogKind.AMERICAN_FOXHOUND, "102 103");
            addOwner(2, "Tom", "Cruis", DogKind.BERGER_PICKARD, "101");
            addOwner(3, "Robert", "De Niro", DogKind.CHESAPEAKE, "104 105 106");
            addOwner(4, "Al", "Pacino", DogKind.ENGLISH_COONHOUND, "107 108 109 110");
            addOwner(5, "Garry", "Potter", DogKind.MUNSTERLANDER, "111");
            addOwner(6, "Will", "Smith", DogKind.SAINT_BERNARD, "112 113 114 116");
            addOwner(7, "Snejana", "Denisovna", DogKind.GERMAN_SHEPHERD, "117 118");
            addOwner(8, "James", "Bond", DogKind.SIBERIAN_HUSKY, "119");
            addOwner(9, "Christian", "Baile", DogKind.POCKET_BEAGLE, "120 121 122");
            addOwner(10, "Anjelina", "Jolie", DogKind.WATER_SPANIEL, "123 124 125");

            addDog(101, "Palkan", DogKind.AMERICAN_FOXHOUND, "shepherd", 201, 55, 65);
            addDog(102, "Drujok", DogKind.BERGER_PICKARD, "shepherd", 201, 55, 65);
            addDog(103, "Sharik", DogKind.CHINOOK, "shepherd", 201, 55, 65);
            addDog(104, "Greezly", DogKind.ENGLISH_COONHOUND, "shepherd", 201, 55, 65);
            addDog(105, "Mickey", DogKind.MUNSTERLANDER, "shepherd", 201, 55, 65);
            addDog(106, "Plooto", DogKind.GERMAN_SHEPHERD, "shepherd", 201, 55, 65);
            addDog(107, "Muhtar", DogKind.POCKET_BEAGLE, "shepherd", 201, 55, 65);
            addDog(108, "Kachtanka", DogKind.SAINT_BERNARD, "shepherd", 201, 55, 65);
            addDog(109, "Leopold", DogKind.SHEPHERD, "shepherd", 201, 55, 65);
            addDog(110, "Barboss", DogKind.WATER_SPANIEL, "shepherd", 201, 55, 65);
            addDog(111, "Joochka", DogKind.GERMAN_SHEPHERD, "shepherd", 201, 55, 65);
            addDog(112, "Belka", DogKind.MUNSTERLANDER, "shepherd", 201, 55, 65);
            addDog(113, "Strelka", DogKind.SAINT_BERNARD, "shepherd", 201, 55, 65);
            addDog(114, "Laika", DogKind.CHINOOK, "shepherd", 201, 55, 65);
            addDog(115, "Palma", DogKind.POCKET_BEAGLE, "shepherd", 201, 55, 65);
            addDog(116, "Gerda", DogKind.WATER_SPANIEL, "shepherd", 201, 55, 65);
            addDog(117, "Sally", DogKind.AMERICAN_FOXHOUND, "shepherd", 201, 55, 65);
            addDog(118, "Joochka", DogKind.CHESAPEAKE, "shepherd", 201, 55, 65);
            addDog(119, "Kachtanka", DogKind.GERMAN_SHEPHERD, "shepherd", 201, 55, 65);
            addDog(120, "Pluto", DogKind.ENGLISH_COONHOUND, "shepherd", 201, 55, 65);
            addDog(121, "Strelka", DogKind.STANDARD_SCHNAUZER, "shepherd", 201, 55, 65);
            addDog(122, "Mickey", DogKind.SIBERIAN_HUSKY, "shepherd", 201, 55, 65);
            addDog(123, "Greazy", DogKind.SHEPHERD, "shepherd", 201, 55, 65);
            addDog(124, "Muhtar", DogKind.POCKET_BEAGLE, "shepherd", 201, 55, 65);
            addDog(125, "Leopold", DogKind.STANDARD_SCHNAUZER, "shepherd", 201, 55, 65);
        }
        cursor.close();
    }

    private void addOwner(int id, String name, String surname, String preferedKind, String dogsIds) {
        ContentValues cv = new ContentValues();
        cv.put(OwnerTable.ID, id);
        cv.put(OwnerTable.NAME, name);
        cv.put(OwnerTable.SURNAME, surname);
        cv.put(OwnerTable.PREFERED_DOGS_KIND, preferedKind);
        cv.put(OwnerTable.DOGS_IDS, dogsIds);
        db.insert(OwnerTable.TABLE_NAME, null, cv);
    }

    private void addDog(int id, String name, String kind, String image, int age, int weight, int tall) {
        ContentValues cv = new ContentValues();
        cv.put(DogTable.ID, id);
        cv.put(DogTable.NAME, name);
        cv.put(DogTable.KIND, kind);
        cv.put(DogTable.IMAGE, image);
        cv.put(DogTable.AGE, age);
        cv.put(DogTable.WEIGHT, weight);
        cv.put(DogTable.TALL, tall);
        db.insert(DogTable.TABLE_NAME, null, cv);
    }
}
