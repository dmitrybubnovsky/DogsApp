package com.andersen.dogsapp.dogs.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.andersen.dogsapp.dogs.data.database.tables.ClassTable;
import com.andersen.dogsapp.dogs.data.database.tables.DogTable;
import com.andersen.dogsapp.dogs.data.database.tables.OwnerTable;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper dbHelper = null;

    public static final String TAG = "# DBHelper";
    private static final String DB_NAME = "doggy_dogg.db";
    private static final int VERSION = 1;

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    // CREATE OWNERS TABLE
    private static final String CREATE_TABLE_OWNER_QUERY = new StringBuilder()
            .append("CREATE TABLE ").append(OwnerTable.TABLE_NAME).append(" (")
            .append(OwnerTable.ID).append(" INTEGER,")
            .append(OwnerTable.NAME).append(" TEXT,")
            .append(OwnerTable.SURNAME).append(" TEXT,")
            .append(OwnerTable.PREFERED_DOGS_KIND).append(" TEXT,")
            .append(OwnerTable.DOGS_IDS).append(" TEXT);").toString();

    // CREATE DOGS TABLE
    private static final String CREATE_TABLE_DOG_QUERY = new StringBuilder()
            .append("CREATE TABLE ").append(DogTable.TABLE_NAME).append("(")
            .append(DogTable.ID).append(" INTEGER,")
            .append(DogTable.NAME).append(" TEXT,")
            .append(DogTable.KIND).append(" TEXT,")
            .append(DogTable.IMAGE).append(" TEXT,")
            .append(DogTable.TALL).append(" INTEGER,")
            .append(DogTable.WEIGHT).append(" INTEGER,")
            .append(DogTable.AGE).append(" INTEGER);").toString();

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_OWNER_QUERY);
        db.execSQL(CREATE_TABLE_DOG_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OwnerTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DogTable.TABLE_NAME);
        onCreate(db);
    }
}
