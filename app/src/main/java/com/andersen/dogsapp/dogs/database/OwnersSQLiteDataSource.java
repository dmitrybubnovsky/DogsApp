package com.andersen.dogsapp.dogs.database;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andersen.dogsapp.dogs.Owner;
import com.andersen.dogsapp.dogs.database.tables.OwnerTable;
import com.andersen.dogsapp.dogs.database.wrappers.OwnersCursorWrapper;

import java.util.ArrayList;
import java.util.List;

public class OwnersSQLiteDataSource {
    private static OwnersSQLiteDataSource ownersDataSource;
    private static final String TAG = "#";

    public OwnersCursorWrapper ownersCursor;
    private List<Owner> owners;

    private OwnersSQLiteDataSource(SQLiteDatabase db) {
        owners = getOwners(db);
    }

    public static OwnersSQLiteDataSource getInstance(SQLiteDatabase db) {
        if (ownersDataSource == null) {
            ownersDataSource = new OwnersSQLiteDataSource(db);
        }
        return ownersDataSource;
    }

    public List<Owner> getOwners(SQLiteDatabase db) {
        if (owners != null) {
            return owners;
        } else {
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
        }
        return owners;
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
}
