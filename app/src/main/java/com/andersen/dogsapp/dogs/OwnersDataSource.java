package com.andersen.dogsapp.dogs;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class OwnersDataSource {
    private static OwnersDataSource ownersDataSource;

    @SerializedName("owners")
    @Expose
    private List<Owner> owners;

    private OwnersDataSource(OwnersDataSource copy){
        this(copy.owners);
    }

    private OwnersDataSource(List<Owner> owners){
        this.owners = new ArrayList<>();
        this.owners.addAll(owners);
    }

    public static OwnersDataSource init (OwnersDataSource instance){
        if(ownersDataSource == null){
            ownersDataSource = new OwnersDataSource(instance);
        }
        return ownersDataSource;
    }

    public List<Owner> getOwners(){
        return owners;
    }
}
