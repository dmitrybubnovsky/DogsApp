package com.andersen.dogsapp.dogs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class DogsDataSource {
    private static DogsDataSource dogsDataSource;

    @SerializedName("dogs")
    @Expose
    public List<Dog> dogs;

    private DogsDataSource(DogsDataSource copy){
        this(copy.dogs);
    }

    private DogsDataSource(List<Dog> dogs){
        this.dogs = new ArrayList<>();
        this.dogs.addAll(dogs);
    }

    public static DogsDataSource init (DogsDataSource instance){
        if(dogsDataSource == null){
            return new DogsDataSource(instance);
        }
        return dogsDataSource;
    }

    public List<Dog> getDogs(){
        return dogs;
    }

    public void setDogs(List<Dog> dogs){
        if (dogs == null){
            this.dogs.addAll(dogs);
        }
    }

    public Dog getDog(int dogId){
        for(Dog dog : dogs){
            if (dog.getOwnerId() == (dogId))
                return dog;
        }
        return null;
    }
    public List<String> getDogsNames(){
        ArrayList<String> dogsNames = new ArrayList<>();
        String dogName = null;
        for(Dog dog : dogs){
            dogName = dog.getDogName();
            dogsNames.add(dogName);
        }
        return dogsNames;
    }
}
