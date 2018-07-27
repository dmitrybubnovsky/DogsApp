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
      //  addSomeDB();
        Cursor cursor = null;
        owners = new ArrayList<>();
        try {
            cursor = db.query(
                    OwnerTable.TABLE_NAME,
                    null, null, null, null, null, null, null);
            // проверяем БД, если там пусто то список владельцев null
            if(cursor.getCount() == 0 || !cursor.moveToNext()){
                owners = null;    Log.d("#", "loadOwners: owners = null");
            } else {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Owner owner = new Owner();
                    owner.setOwnerId(cursor.getInt(cursor.getColumnIndex(OwnerTable.ID)));
                    owner.setOwnerName(cursor.getString(cursor.getColumnIndex(OwnerTable.NAME)));
                    owner.setOwnerSurname(cursor.getString(cursor.getColumnIndex(OwnerTable.SURNAME)));
                    owner.setPreferedDogsKind(cursor.getString(cursor.getColumnIndex(OwnerTable.PREFERED_DOGS_KIND)));
                    String dogsIdsString = cursor.getString(cursor.getColumnIndex(OwnerTable.DOGS_IDS));
                    owner.setDogsIds(getIntArrayFromString(dogsIdsString));
                    owners.add(owner);
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
        DatabaseManager.getInstance().closeDB();
    }

    // converts string of ints like "2 3 4" to int[] like new int[]{2,3,4}
    private int[] getIntArrayFromString(String dogIdsString) {
        if (dogIdsString == null){
            return new int[0];
        } else {
            String[] arrayStrings = dogIdsString.split(" ");
            int[] dogsIds = new int[arrayStrings.length];
            for (int i = 0; i < dogsIds.length; i++) {
                dogsIds[i] = Integer.parseInt(arrayStrings[i]);
            }
            return dogsIds;
        }
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
        return insertResult;
    }

    // для тестовой БД
    private void addOwner(ContentValues cv, String name, String surname, String preferedKind, String dogsIds) {
//        ContentValues cv = new ContentValues();
        cv.put(OwnerTable.NAME, name);
        cv.put(OwnerTable.SURNAME, surname);
        cv.put(OwnerTable.PREFERED_DOGS_KIND, preferedKind);
        cv.put(OwnerTable.DOGS_IDS, dogsIds);
        db.insert(OwnerTable.TABLE_NAME, null, cv);
    }

    // для тестовой БД
    private void addDog(ContentValues cv, int id, String name, String kind, String image, int age, int weight, int tall) {
//        ContentValues cv = new ContentValues();
        cv.put(DogTable.ID, id);
        cv.put(DogTable.NAME, name);
        cv.put(DogTable.KIND, kind);
        cv.put(DogTable.IMAGE, image);
        cv.put(DogTable.AGE, age);
        cv.put(DogTable.WEIGHT, weight);
        cv.put(DogTable.TALL, tall);
        db.insert(DogTable.TABLE_NAME, null, cv);
    }

    private void addSomeDB() {
        db = DatabaseManager.getInstance().openDB();
        Cursor cursor = db.query(OwnerTable.TABLE_NAME, null, null, null, null, null, null);
        ContentValues cv = new ContentValues();
        if (cursor.getCount() == 0) {
            addOwner(cv, "Andy", "Garcia", DogKind.AMERICAN_FOXHOUND, "102 103");
            addOwner(cv, "Tom", "Cruis", DogKind.BERGER_PICKARD, "101");
            addOwner(cv, "Robert", "De Niro", DogKind.CHESAPEAKE, "104 105 106");
            addOwner(cv, "Al", "Pacino", DogKind.ENGLISH_COONHOUND, "107 108 109 110");
            addOwner(cv, "Garry", "Potter", DogKind.MUNSTERLANDER, "111");
            addOwner(cv, "Will", "Smith", DogKind.SAINT_BERNARD, "112 113 114 116");
            addOwner(cv, "Snejana", "Denisovna", DogKind.GERMAN_SHEPHERD, "117 118");
            addOwner(cv, "James", "Bond", DogKind.SIBERIAN_HUSKY, "119");
            addOwner(cv, "Christian", "Baile", DogKind.POCKET_BEAGLE, "120 121 122");
            addOwner(cv, "Anjelina", "Jolie", DogKind.WATER_SPANIEL, "123 124 125");
            addDog(cv,101, "Palkan", DogKind.AMERICAN_FOXHOUND, "american_foxhound", 35, 55, 65);
            addDog(cv,102, "Drujok", DogKind.AFGHAN_HOUND, "afghan_hound", 21, 55, 65);
            addDog(cv,103, "Sharik", DogKind.AMERICAN_BULLDOG, "american_bulldog", 38, 55, 65);
            addDog(cv,104, "Greezly", DogKind.AUSTRALIAN, "australiancattle", 35, 55, 65);
            addDog(cv,105, "Mickey", DogKind.BELGIAN, "belgiantervuren", 29, 55, 65);
            addDog(cv,106, "Plooto", DogKind.BERGER_PICKARD, "berger_picard", 31, 55, 65);
            addDog(cv,107, "Muhtar", DogKind.BOLOGNESE, "bolognese", 33, 55, 65);
            addDog(cv,108, "Kachtanka", DogKind.BOXER, "boxer", 38, 55, 65);
            addDog(cv,109, "Leopold", DogKind.BULL_TERRIER, "bullterrier", 32, 55, 65);
            addDog(cv,110, "Barboss", DogKind.CHESAPEAKE, "chesapeake_bay_retriever", 38, 55, 65);
            addDog(cv,111, "Joochka", DogKind.CHINOOK, "chinook", 38, 55, 65);
            addDog(cv,112, "Belka", DogKind.DOGOARGENTINO, "dogoargentino", 36, 55, 65);
            addDog(cv,113, "Strelka", DogKind.ENGLISH_COONHOUND, "english_coonhound", 38, 55, 65);
            addDog(cv,114, "Laika", DogKind.GERMAN_SHEPHERD, "german_shepherd", 37, 55, 65);
            addDog(cv,115, "Palma", DogKind.ICELANDIC, "icelandicsheepdog", 30, 55, 65);
            addDog(cv,116, "Gerda", DogKind.KOMONDOR, "komondor", 39, 55, 65);
            addDog(cv,117, "Sally", DogKind.MUDI, "mudi", 38, 55, 65);
            addDog(cv,118, "Joochka", DogKind.MUNSTERLANDER, "munsterlander_pointer", 38, 55, 65);
            addDog(cv,119, "Kachtanka", DogKind.PHARAONHOUND, "pharaohhound", 38, 55, 65);
            addDog(cv,120, "Pluto", DogKind.POCKET_BEAGLE, "pocket_beagle", 27, 55, 65);
            addDog(cv,121, "Strelka", DogKind.PUG, "pug", 38, 55, 65);
            addDog(cv,122, "Mickey", DogKind.SAINT_BERNARD, "saint_bernard", 41, 55, 65);
            addDog(cv,123, "Greazy", DogKind.SCOTTISH, "scottishterrier", 26, 55, 65);
            addDog(cv,124, "Muhtar", DogKind.SIBERIAN_HUSKY, "siberian_husky", 27, 55, 65);
            addDog(cv,125, "Leopold", DogKind.STAFFORD, "staffordshire", 16, 55, 65);
        }
        cursor.close();
        DatabaseManager.getInstance().closeDB();
    }
}
