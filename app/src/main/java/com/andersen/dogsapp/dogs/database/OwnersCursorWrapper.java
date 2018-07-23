package com.andersen.dogsapp.dogs.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.andersen.dogsapp.dogs.Owner;

public class OwnersCursorWrapper extends CursorWrapper {
    public OwnersCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public Owner getOwner() {
        Owner owner = new Owner();
        owner.setOwnerId(getInt(getColumnIndex(OwnerTable.ID)));
        owner.setOwnerName(getString(getColumnIndex(OwnerTable.NAME)));
        owner.setOwnerSurname(getString(getColumnIndex(OwnerTable.SURNAME)));
        owner.setPreferedDogsKind(getString(getColumnIndex(OwnerTable.PREFERED_DOGS_KIND)));
        owner.setDogsIds(getIntArrayFromString(getString(getColumnIndex(OwnerTable.DOGS_IDS))));
        return owner;
    }

    // converts string of ints like "2 3 4" to int[] like new int[]{2,3,4}
    private int[] getIntArrayFromString(String dogIdsString) {
        String[] arrayStrings = dogIdsString.split(" ");
        int[] dogsIds = new int[arrayStrings.length];
        for (int i = 0; i < dogsIds.length; i++) {
            dogsIds[i] = Integer.parseInt(arrayStrings[i]);
        }
        return dogsIds;
    }
}
