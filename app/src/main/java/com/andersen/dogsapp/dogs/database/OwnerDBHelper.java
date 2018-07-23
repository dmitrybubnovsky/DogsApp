package com.andersen.dogsapp.dogs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.andersen.dogsapp.dogs.data.DogKind;
import com.andersen.dogsapp.dogs.database.tables.ClassTable;
import com.andersen.dogsapp.dogs.database.tables.DogTable;
import com.andersen.dogsapp.dogs.database.tables.OwnerTable;

public class OwnerDBHelper extends SQLiteOpenHelper {
    public static final String TAG = "# OwnerDBHelper";

    private static final String DB_NAME = "doggy_dogg.db";
    private static final int VERSION = 1;

    public OwnerDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    // CREATE OWNERS TABLE
    private static final String CREATE_TABLE_OWNER = new StringBuilder()
            .append("CREATE TABLE ").append(OwnerTable.TABLE_NAME).append(" (")
            //     .append(OwnerTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(OwnerTable.ID).append(" INTEGER,")
            .append(OwnerTable.NAME).append(" TEXT,")
            .append(OwnerTable.SURNAME).append(" TEXT,")
            .append(OwnerTable.PREFERED_DOGS_KIND).append(" TEXT,")
            .append(OwnerTable.DOGS_IDS).append(" TEXT);").toString();

    // CREATE DOGS TABLE
    private static final String CREATE_TABLE_DOG = new StringBuilder()
            .append("CREATE TABLE ").append(DogTable.TABLE_NAME).append("(")
            .append(DogTable.ID).append(" INTEGER,")
            //   .append(DogTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(DogTable.NAME).append(" TEXT,")
            .append(DogTable.KIND).append(" TEXT,")
            .append(DogTable.IMAGE).append(" TEXT,")
            .append(DogTable.TALL).append(" INTEGER,")
            .append(DogTable.WEIGHT).append(" INTEGER,")
            .append(DogTable.AGE).append(" INTEGER);").toString();

    private static final String CREATE_CLASS_TABLE = new StringBuilder()
            .append("CREATE TABLE ").append(ClassTable.TABLE_NAME).append("(")
            .append(ClassTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(ClassTable.DOG_ID).append(" INTEGER,")
            .append(ClassTable.OWNER_ID).append(" INTEGER);").toString();

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_OWNER);

        db.execSQL(CREATE_TABLE_DOG);

        db.execSQL(CREATE_CLASS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OwnerTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DogTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ClassTable.TABLE_NAME);
        onCreate(db);
    }

    public long addOwner(int id, String name, String surname, String preferedKind, String dogsIds) {
        ContentValues cv = new ContentValues();
        cv.put(OwnerTable.ID, id);
        cv.put(OwnerTable.NAME, name);
        cv.put(OwnerTable.SURNAME, surname);
        cv.put(OwnerTable.PREFERED_DOGS_KIND, preferedKind);
        cv.put(OwnerTable.DOGS_IDS, dogsIds);

        return getWritableDatabase().insert(OwnerTable.TABLE_NAME, null, cv);
    }

    public long addDog(int id, String name, String kind, String image, int age, int weight, int tall) {
        ContentValues cv = new ContentValues();
        cv.put(DogTable.ID, id);
        cv.put(DogTable.NAME, name);
        cv.put(DogTable.KIND, kind);
        cv.put(DogTable.IMAGE, image);
        cv.put(DogTable.AGE, age);
        cv.put(DogTable.WEIGHT, weight);
        cv.put(DogTable.TALL, tall);

        return getWritableDatabase().insert(DogTable.TABLE_NAME, null, cv);
    }

    public void addSomeDB() {

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
}
