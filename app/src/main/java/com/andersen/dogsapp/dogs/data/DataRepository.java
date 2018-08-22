package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.data.entities.Dog;
import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.entities.Owner;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IDatabaseCallback;
import com.andersen.dogsapp.dogs.data.interfaces.IDogsDataSource;
import com.andersen.dogsapp.dogs.data.interfaces.IOwnersDataSource;
import com.andersen.dogsapp.dogs.data.web.IWebCallback;

import java.util.List;

public class DataRepository {
    private static DataRepository instance;
    private IOwnersDataSource ownersDataSource;
    private IDogsDataSource dogsDataSource;
    private IBreedsDataSource breedsDataSource;

    private DataRepository(IOwnersDataSource ownersDataSource, IDogsDataSource dogsDataSource, IBreedsDataSource breedsDataSource) {
        this.ownersDataSource = ownersDataSource;
        this.dogsDataSource = dogsDataSource;
        this.breedsDataSource = breedsDataSource;
    }

    public static void init(IOwnersDataSource ownersDataSource, IDogsDataSource dogsDataSource, IBreedsDataSource breedsDataSource) {
        if (instance == null) {
            instance = new DataRepository(ownersDataSource, dogsDataSource, breedsDataSource);
        }
    }

    public static DataRepository get() {
        return instance;
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

    //   WEB SERVER IS CURRENTLY ЛЕЖИТ
    public void getDogKinds(IWebCallback<List<DogKind>> webCallback, IDatabaseCallback<List<DogKind>> dbCallback) {
        breedsDataSource.getDogsKinds(webCallback, dbCallback);
    }

    public void getBreedsImage(String breedString, IWebCallback<String> callback) {
        breedsDataSource.getBreedsImage(breedString, callback);
    }

    public void updateBreedDBWithUriImage(DogKind dogKind) {
        breedsDataSource.updateBreedDBWithUriImage(dogKind);
    }
}
