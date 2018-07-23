package com.andersen.dogsapp.dogs.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andersen.dogsapp.dogs.Dog;
import com.andersen.dogsapp.dogs.database.tables.DogTable;
import com.andersen.dogsapp.dogs.database.wrappers.DogsCursorWrapper;

import java.util.ArrayList;
import java.util.List;

public class DogsSQLiteDataSource {
    private static DogsSQLiteDataSource dogsDataSource;

    public DogsCursorWrapper dogsCursor;
    private List<Dog> dogs;

    private DogsSQLiteDataSource(OwnerDBHelper ownerDBHelper) {
        dogs = getDogs(ownerDBHelper);
    }

    public static DogsSQLiteDataSource getInstance(OwnerDBHelper ownerDBHelper) {
        if (dogsDataSource == null) {
            dogsDataSource = new DogsSQLiteDataSource(ownerDBHelper);
        }
        return dogsDataSource;
    }

    public List<Dog> getDogs(OwnerDBHelper ownerDBHelper) {
        SQLiteDatabase db = ownerDBHelper.getWritableDatabase();
        if (dogs != null) {
            return dogs;
        } else {
            dogs = new ArrayList<>();
            try {
                dogsCursor = queryDogs(db);
                dogsCursor.moveToNext();
                while (!dogsCursor.isAfterLast()) {
                    dogs.add(dogsCursor.getDog());
                    dogsCursor.moveToNext();
                }
            } finally {
                dogsCursor.close();
            }
        }
        return dogs;
    }

    private DogsCursorWrapper queryDogs(SQLiteDatabase db) {
        Cursor cursor = db.query(
                DogTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        return new DogsCursorWrapper(cursor);
    }
}
