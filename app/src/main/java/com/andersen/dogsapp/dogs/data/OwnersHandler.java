package com.andersen.dogsapp.dogs.data;
import com.andersen.dogsapp.dogs.Owner;
import java.util.ArrayList;
import java.util.List;

public class OwnersHandler {
    private static OwnersHandler instance;
    private List<Owner> owners;

    public OwnersHandler() {
        owners = new ArrayList<>();
    }

    public static OwnersHandler getInstance (){
        if(instance == null){
            instance = new OwnersHandler();
        }
        return instance;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    public List<Owner> getOwners() {
        return owners;
    }
}
