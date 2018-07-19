package com.andersen.dogsapp.dogs.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OwnerDBHelper extends SQLiteOpenHelper {
    public static final String TAG = "# OwnerDBHelper";

    private static final String DB_NAME = "doggy_dogg.db";
    private static final int VERSION = 1;

    OwnerDBHelper(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    // CREATE OWNERS TABLE
    private static final String CREATE_TABLE_OWNER = new StringBuilder()
            .append("CREATE TABLE ").append(OwnerTable.TABLE_NAME).append("(")
            .append(OwnerTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(OwnerTable.NAME).append("TEXT,")
            .append(OwnerTable.SURNAME).append("TEXT,")
            .append(OwnerTable.PREFERED_DOGS_KIND).append("TEXT);").toString();

    // CREATE DOGS TABLE
    private static final String CREATE_TABLE_DOG = new StringBuilder()
            .append("CREATE TABLE ").append(DogTable.TABLE_NAME).append("(")
            .append(DogTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(DogTable.NAME).append("TEXT,")
            .append(DogTable.KIND).append("TEXT,")
            .append(DogTable.TALL).append("INTEGER,")
            .append(DogTable.WEIGHT).append("INTEGER,")
            .append(DogTable.AGE).append("INTEGER);").toString();

    private static final String CREATE_CLASS_TABLE = new StringBuilder()
            .append("CREATE TABLE ").append(ClassTable.TABLE_NAME).append("(")
            .append(ClassTable.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,")
            .append(ClassTable.DOG_ID).append("INTEGER,")
            .append(ClassTable.OWNER_ID).append("INTEGER);").toString();

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
}
