package com.andersen.dogsapp.dogs.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.andersen.dogsapp.dogs.data.database.tables.BreedTable;
import com.andersen.dogsapp.dogs.data.database.tables.DogTable;
import com.andersen.dogsapp.dogs.data.database.tables.OwnerTable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = "# DBHelper";
    private static final String DB_NAME = "doggy_dogg.db";
    private static final int VERSION = 1;
    // CREATE OWNERS TABLE
    private static final String CREATE_TABLE_OWNER_QUERY = new StringBuilder()
            .append("CREATE TABLE ").append(OwnerTable.TABLE_NAME).append(" (")
            .append(OwnerTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(OwnerTable.NAME).append(" TEXT DEFAULT 'Unknown',")
            .append(OwnerTable.SURNAME).append(" TEXT DEFAULT 'Unknown',")
            .append(OwnerTable.PREFERRED_BREED).append(" TEXT DEFAULT 'homeless');").toString();
    // CREATE DOGS TABLE
    private static final String CREATE_TABLE_DOG_QUERY = new StringBuilder()
            .append("CREATE TABLE ").append(DogTable.TABLE_NAME).append("(")
            .append(DogTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(DogTable.OWNER_ID).append(" INTEGER,") // NOT NULL
            .append(DogTable.NAME).append(" TEXT ,")
            .append(DogTable.KIND).append(" TEXT,")
            .append(DogTable.IMAGE).append(" TEXT DEFAULT 'new_dog',")
            .append(DogTable.TALL).append(" INTEGER,")
            .append(DogTable.WEIGHT).append(" INTEGER,")
            .append(DogTable.AGE).append(" INTEGER);").toString();

    private static final String CREATE_TABLE_BREED_QUERY = new StringBuilder()
            .append("CREATE TABLE ").append(BreedTable.TABLE_NAME).append("(")
            .append(BreedTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(BreedTable.BREED).append(" TEXT,")
            .append(BreedTable.IMAGE_URI).append(" TEXT);").toString();

    private static DBHelper dbHelper = null;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_OWNER_QUERY);
        db.execSQL(CREATE_TABLE_DOG_QUERY);
        db.execSQL(CREATE_TABLE_BREED_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OwnerTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DogTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BreedTable.TABLE_NAME);
        onCreate(db);
    }
}
