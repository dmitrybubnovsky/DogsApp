package com.andersen.dogsapp.dogs.data;

import android.app.Activity;
import android.provider.ContactsContract;

import java.util.List;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.json.DogsKindsData;

public class DataRepository {
    private static DataRepository instance;
    private IOwnersDataSource ownersDataSource;
    private IDogsDataSource dogsDataSource;
    private IBreedsDataSource iBreedsDataSource;

    private DataRepository(IOwnersDataSource ownersDataSource, IDogsDataSource dogsDataSource, IBreedsDataSource iBreedsDataSource) {
        this.ownersDataSource = ownersDataSource;
        this.dogsDataSource = dogsDataSource;
        this.iBreedsDataSource = iBreedsDataSource;
    }

    public static void init(IOwnersDataSource ownersDataSource, IDogsDataSource dogsDataSource, IBreedsDataSource iBreedsDataSource) {
        if (instance == null) {
            instance = new DataRepository(ownersDataSource, dogsDataSource, iBreedsDataSource);
        }
    }

    public static DataRepository get() {
        return instance;
    }

    public List<DogKind> getDogKinds(){
        return iBreedsDataSource.getDogsKinds();
    }

    public List<Dog> getOwnerDogs(Owner owner) {
        return dogsDataSource.getOwnerDogs(owner);
    }

    public List<Dog> getDogs() {
        return dogsDataSource.getDogs();
    }

    public Owner addOwner(Owner owner) {
        return ownersDataSource.addOwner(owner);
    }

    public Dog addDog(Dog dog) {
        return dogsDataSource.addDog(dog);
    }

    public List<Owner> getOwners() {
        List<Owner> owners = ownersDataSource.getOwners();
        for (Owner owner : owners) {
            owner.setDogs(getOwnerDogs(owner));
        }
        return owners;
    }
}
