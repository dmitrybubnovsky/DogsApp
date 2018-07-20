package com.andersen.dogsapp.dogs.database;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.andersen.dogsapp.dogs.Dog;
import java.util.ArrayList;
import java.util.List;

public class DogsSQLiteDataSource {
    private static DogsSQLiteDataSource dogsDataSource;

    public DogsCursorWrapper dogsCursor;
    //  private SQLiteDatabase db;
    private List<Dog>dogs;

    private DogsSQLiteDataSource(SQLiteDatabase db){
        //this.db = db;
        dogsCursor = queryDogs(db);
    }

    public static DogsSQLiteDataSource getInstance (SQLiteDatabase db){
        if(dogsDataSource == null){
            dogsDataSource = new DogsSQLiteDataSource(db);
        }
        return dogsDataSource;
    }

    public List<Dog> getDogs (){
        List<Dog> dogs  = new ArrayList<>();
        // dogsCursor = queryDogs();

        try{
            dogsCursor.moveToNext();
            while (!dogsCursor.isAfterLast()){
                dogs.add(dogsCursor.getDog());
                dogsCursor.moveToNext();
            }
        } finally{
            dogsCursor.close();
        }
        return dogs;
    }

    private DogsCursorWrapper queryDogs(SQLiteDatabase db){
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
