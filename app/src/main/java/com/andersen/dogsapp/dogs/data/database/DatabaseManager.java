package com.andersen.dogsapp.dogs.data.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;

public class DatabaseManager {
    private static DatabaseManager instance;
    private static DBHelper dbHelper;
    private AtomicInteger openCounter = new AtomicInteger();
    private SQLiteDatabase sqliteDB;

    public static synchronized void initInstance(DBHelper helper) {
        if (instance == null) {
            instance = new DatabaseManager();
            dbHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initInstance(..) method first.");
        }
        return instance;
    }

    public synchronized SQLiteDatabase openDB() {
        if (openCounter.incrementAndGet() == 1) {
            // открываем БД
            sqliteDB = dbHelper.getWritableDatabase();
        }
        return sqliteDB;
    }

    public synchronized void closeDB() {
        if (openCounter.decrementAndGet() == 0) {
            // закрываем БД
            sqliteDB.close();
        }
    }
}
