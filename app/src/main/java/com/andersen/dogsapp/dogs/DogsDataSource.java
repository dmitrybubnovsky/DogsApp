package com.andersen.dogsapp.dogs;
import android.content.Context;
import android.util.Log;
import com.andersen.dogsapp.dogs.data.DogsData;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DogsDataSource {
    private static DogsDataSource dogsDataSource;

    private DogsData dogsData;
    private List<Dog> dogs;

    private DogsDataSource(Context context){
        dogs = getDogs(context);
    }

    public static DogsDataSource getInstance(Context context){
        if(dogsDataSource == null){
            dogsDataSource = new DogsDataSource(context);
        }
        return dogsDataSource;
    }

    public List<Dog> getDogs(Context context){
        if (dogs != null){
            return dogs;
        }
        try {
            JsonParser jsonParser = JsonParser.newInstance();
            InputStream inputStream = context.getAssets().open("dogs.json");
            dogsData = jsonParser.parseInputStream(inputStream, DogsData.class);
            return dogsData.getDogs();
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
