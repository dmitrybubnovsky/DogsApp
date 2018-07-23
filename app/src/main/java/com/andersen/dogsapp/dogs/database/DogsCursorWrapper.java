package com.andersen.dogsapp.dogs.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.andersen.dogsapp.dogs.Dog;

public class DogsCursorWrapper extends CursorWrapper {
    public DogsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Dog getDog() {
        Dog dog = new Dog();
        dog.setDogId(getInt(getColumnIndex(DogTable.ID)));
        dog.setDogName(getString(getColumnIndex(DogTable.NAME)));
        dog.setDogKind(getString(getColumnIndex(DogTable.KIND)));
        dog.setDogImageString(getString(getColumnIndex(DogTable.IMAGE)));
        dog.setDogAge(getInt(getColumnIndex(DogTable.AGE)));
        dog.setDogTall(getInt(getColumnIndex(DogTable.TALL)));
        dog.setDogWeight(getInt(getColumnIndex(DogTable.WEIGHT)));
        return dog;
    }
}
