package com.andersen.dogsapp.dogs;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataRepository {
    private static DataRepository dataRepository;

    private List<Dog> dogs;
    private List<Owner> owners;

    private DataRepository(Context context){
        GsonBuilder builder = new GsonBuilder();
        final Gson GSON = builder.create();
        String json;

        json = getAssetsJSON("owners.json", context);
        OwnersDataSource ownersDataSource = OwnersDataSource.init(GSON.fromJson(json, OwnersDataSource.class));
        owners = ownersDataSource.getOwners();

        json = getAssetsJSON("dogs.json", context);
        DogsDataSource dogsDataSource = DogsDataSource.init(GSON.fromJson(json, DogsDataSource.class));
        dogs = dogsDataSource.getDogs();
    }

    public static DataRepository get(Context context){
        if(dataRepository == null){
            dataRepository = new DataRepository(context);
        } return dataRepository;
    }

    private static String getAssetsJSON(String fileName, Context context){
        String json = null;
        try{
            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String (buffer, "UTF-8");
        } catch (IOException e ){
            e.printStackTrace();
        }
        return json;
    }

    public List<Owner> getOwners(){
        return owners;
    }

    public ArrayList<String> getOwnersNames(){
        ArrayList<String> fullNames = new ArrayList<>();
        for(Owner owner : owners){
            fullNames.add(owner.getOwnerName()+" "+owner.getOwnerSurname());
        }
        return fullNames;
    }

    public ArrayList<String> getPrefereDogsKinds(){
        ArrayList<String> dogsKinds = new ArrayList<>();
        for(Owner owner : owners){
            dogsKinds.add(owner.getPreferedDogsKind());
        }
        return dogsKinds;
    }

    public int[] getDogsCountsEachOwnerArray(){
        int[] quantitiesDogs = new int[owners.size()];
        for (int i=0;i<owners.size();i++){
            quantitiesDogs[i] = owners.get(i).getDogsQuantity();
        }
        return quantitiesDogs;
    }

    public List<Dog> getDogs(){
        return dogs;
    }

    public Owner getOwnerById(int ownerId){
        for(Owner owner : owners){
            if (owner.getOwnerId() == (ownerId)){
                return owner;
            }
        }
        throw new IndexOutOfBoundsException("Class DataRepository. Method getOwnerById. Not acceptable ownerId");
    }


    public Dog getDogById(int dogId){
        for(Dog dog : dogs){
            if (dog.getDogId() == (dogId)){
                return dog;
            }
        }
        throw new IndexOutOfBoundsException("Class DataRepository. Method getDogById. Not acceptable ownerId");
    }

    public ArrayList<String> getDogsNamesByOwnerId(int ownerId){
        ArrayList<String> dogsNames = new ArrayList<>();
        Owner owner = this.getOwnerById(ownerId);
        int[] dogsIds = owner.getDogsIds();// get array of dogs' ids on single owner
        int dogsQuantity = owner.getDogsQuantity();
        for(int i = 0; i<dogsQuantity; i++){
            dogsNames.add(this.getDogById(dogsIds[i]).getDogName());
            Log.d("#", dogsNames.get(i));
        }
        return dogsNames;
    }

    public ArrayList<String> getDogsKindsByOwnerId(int ownerId){
        ArrayList<String> dogsKinds = new ArrayList<>();
        Owner owner = this.getOwnerById(ownerId);
        int[] dogsIds = owner.getDogsIds();
        int dogsQuantity = owner.getDogsQuantity();
        for(int i = 0; i<dogsQuantity; i++){
            dogsKinds.add(this.getDogById(dogsIds[i]).getDogKind());
            Log.d("#", dogsKinds.get(i));
        }
        return dogsKinds;
    }

    public int[] getDogsIdsByOwnerId(int ownerId){
        return this.getOwnerById(ownerId).getDogsIds();
    }
}
