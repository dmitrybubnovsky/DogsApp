package com.andersen.dogsapp.dogs.data;

import com.andersen.dogsapp.dogs.data.entities.DogKind;
import com.andersen.dogsapp.dogs.data.interfaces.IBreedsDataSource;
import com.andersen.dogsapp.dogs.data.web.ICallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DogKindSource{ //  implements IBreedsDataSource
//    private static DogKindSource instance;
//    private List<DogKind> dogKinds;
//
//
//    private IBreedsDataSource iBreedsDataSource;
//
//    private DogKindSource(IBreedsDataSource iBreedsDataSource) {
//        this.iBreedsDataSource = iBreedsDataSource;
//    }
//
//    public static void init(IBreedsDataSource iBreedsDataSource) {
//        if (instance == null) {
//            instance = new DogKindSource(ownersDataSource, dogsDataSource, iBreedsDataSource);
//        }
//    }
//
//    public static DogKindSource get() {
//        return instance;
//    }
//
//    public void getDogKinds(ICallback<List<DogKind>> callback){
//        iBreedsDataSource.getDogsKinds(callback);
//    }
//
//    public void getBreedsImage(String breedString, ICallback<String> callback){
//        iBreedsDataSource.getBreedsImage(breedString, callback);
//    }
}
