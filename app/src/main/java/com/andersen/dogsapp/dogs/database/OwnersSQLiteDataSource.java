package com.andersen.dogsapp.dogs.database;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.andersen.dogsapp.dogs.Owner;
import java.util.ArrayList;
import java.util.List;

public class OwnersSQLiteDataSource  {
    private static OwnersSQLiteDataSource ownersDataSource;
    private static final String TAG = "# OwnersSQLiteDataSource";

    public OwnersCursorWrapper ownersCursor;
  //  private SQLiteDatabase db;
    private List<Owner> owners;

    private OwnersSQLiteDataSource(){
        //this.db = db;
       // ownersCursor = queryOwners();
    }

    public static OwnersSQLiteDataSource getInstance (){
        if(ownersDataSource == null){
            ownersDataSource = new OwnersSQLiteDataSource();
        }
        return ownersDataSource;
    }

    public List<Owner> getOwners (SQLiteDatabase db){
        List<Owner> owners  = new ArrayList<>();
        ownersCursor = queryOwners(db);

        try{
            ownersCursor.moveToNext();
            while (!ownersCursor.isAfterLast()){
                owners.add(ownersCursor.getOwner());
                ownersCursor.moveToNext();
            }
        } finally{
            ownersCursor.close();
        }
        this.owners = owners;
        return owners;
    }

    private OwnersCursorWrapper queryOwners(SQLiteDatabase db){
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
