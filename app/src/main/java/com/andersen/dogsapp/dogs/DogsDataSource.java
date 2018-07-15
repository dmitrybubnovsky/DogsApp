package com.andersen.dogsapp.dogs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class DogsDataSource {
    private static DogsDataSource dogsDataSource;

    @SerializedName("dogs")
    @Expose
    private List<Dog> dogs;

    private DogsDataSource(DogsDataSource copy){
        this(copy.dogs);
    }

    private DogsDataSource(List<Dog> dogs){
        this.dogs = new ArrayList<>();
        this.dogs.addAll(dogs);
    }

    public static DogsDataSource init (DogsDataSource instance){
        if(dogsDataSource == null){
            dogsDataSource = new DogsDataSource(instance);
        }
        return dogsDataSource;
    }

    public List<Dog> getDogs(){
        return dogs;
    }
}
