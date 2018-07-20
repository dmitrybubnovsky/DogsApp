package com.andersen.dogsapp.dogs.database;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.andersen.dogsapp.dogs.Owner;
import java.util.ArrayList;
import java.util.List;

public class OwnersSQLiteDataSource  {
    private static OwnersSQLiteDataSource ownersDataSource;

    public OwnersCursorWrapper ownersCursor;
  //  private SQLiteDatabase db;
    private List<Owner> owners;

    private OwnersSQLiteDataSource(SQLiteDatabase db){
        //this.db = db;
        ownersCursor = queryOwners(db);
    }

    public static OwnersSQLiteDataSource getInstance (SQLiteDatabase db){
        if(ownersDataSource == null){
            ownersDataSource = new OwnersSQLiteDataSource(db);
        }
        return ownersDataSource;
    }

    public List<Owner> getOwners (){
        List<Owner> owners  = new ArrayList<>();
       // ownersCursor = queryOwners();

        try{
            ownersCursor.moveToNext();
            while (!ownersCursor.isAfterLast()){
                owners.add(ownersCursor.getOwner());
                ownersCursor.moveToNext();
            }
        } finally{
            ownersCursor.close();
        }
        return owners;
    }

    private OwnersCursorWrapper queryOwners(SQLiteDatabase db){
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
