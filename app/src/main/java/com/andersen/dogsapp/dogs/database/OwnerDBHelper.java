package com.andersen.dogsapp.dogs.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


    public long addRelation(String dogId, String ownerId) {
        ContentValues cv = new ContentValues();
        cv.put(DogTable.ID, dogId);
        cv.put(OwnerTable.ID, ownerId);

        return getWritableDatabase().insert(ClassTable.TABLE_NAME, null, cv);
    }

    public void addSomeDB() {
        addOwner(1, "Andy", "Garcia", "Collie", "102 103");
        addOwner(2, "Tom", "Cruis", "Doberman Pincher", "101");
        addOwner(3, "Robert", "De Niro", "Pincher", "104 105 106");
        addOwner(4, "Al", "Pacino", "Spaniel", "107 108 109 110");
        addOwner(5, "Garry", "Potter", "Austrian Sheepdog", "111");
        addOwner(6, "Will", "Smith", "Spaniel", "112 113 114 116");
        addOwner(7, "Snejana", "Denisovna", "Pincher", "117 118");
        addOwner(8, "James", "Bond", "Collie", "119");
        addOwner(9, "Christian", "Baile", "Doberman Pincher", "120 121 122");
        addOwner(10, "Anjelina", "Jolie", "Pit Bull", "123 124 125");

        addDog(101, "Palkan", "Collie", "shepherd", 201, 55, 65);
        addDog(102, "Drujok", "Doberman Pincher", "shepherd", 201, 55, 65);
        addDog(103, "Sharik", "Pincher", "shepherd", 201, 55, 65);
        addDog(104, "Greezly", "Spaniel", "shepherd", 201, 55, 65);
        addDog(105, "Mickey", "Kaukaz Sheepdog", "shepherd", 201, 55, 65);
        addDog(106, "Plooto", "Collie", "shepherd", 201, 55, 65);
        addDog(107, "Muhtar", "Husky", "shepherd", 201, 55, 65);
        addDog(108, "Kachtanka", "Spaniel", "shepherd", 201, 55, 65);
        addDog(109, "Leopold", "Husky", "shepherd", 201, 55, 65);
        addDog(110, "Barboss", "Austrian Sheepdog", "shepherd", 201, 55, 65);
        addDog(111, "Joochka", "Collie", "shepherd", 201, 55, 65);
        addDog(112, "Belka", "Husky", "shepherd", 201, 55, 65);
        addDog(113, "Strelka", "Doberman", "shepherd", 201, 55, 65);
        addDog(114, "Laika", "Husky", "shepherd", 201, 55, 65);
        addDog(115, "Palma", "Spaniel", "shepherd", 201, 55, 65);
        addDog(116, "Gerda", "Pit Bull", "shepherd", 201, 55, 65);
        addDog(117, "Sally", "Collie", "shepherd", 201, 55, 65);
        addDog(118, "Joochka", "Husky", "shepherd", 201, 55, 65);
        addDog(119, "Kachtanka", "Spaniel", "shepherd", 201, 55, 65);
        addDog(120, "Pluto", "Husky", "shepherd", 201, 55, 65);
        addDog(121, "Strelka", "Collie", "shepherd", 201, 55, 65);
        addDog(122, "Mickey", "Pit Bull", "shepherd", 201, 55, 65);
        addDog(123, "Greazy", "Spaniel", "shepherd", 201, 55, 65);
        addDog(124, "Muhtar", "Husky", "shepherd", 201, 55, 65);
        addDog(125, "Leopold", "Collie", "shepherd", 201, 55, 65);
    }
}
