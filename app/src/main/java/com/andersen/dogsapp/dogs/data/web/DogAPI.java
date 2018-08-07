package com.andersen.dogsapp.dogs.data.web;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

public class DogAPI {
    private final String DOG_API_URL = "https://dog.ceo/api";

    public DogAPI() {
    }

    public URL getDogURL()
    {
        URL url = null;

        Uri uri = Uri.parse(DOG_API_URL).buildUpon()
                .appendPath("breeds")
                .appendPath("image")
                .appendPath("random")
                .build();

        try
        {
            url = new URL(uri.toString());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return url;
    }

    public URL getDogURL(String breed, boolean random)
    {
        URL url = null;
        Uri uri;

        if (random)
        {
            uri = Uri.parse(DOG_API_URL).buildUpon()
                    .appendPath("breed")
                    .appendPath(breed)
                    .appendPath("images")
                    .appendPath("random")
                    .build();
        }
        else
        {
            uri = Uri.parse(DOG_API_URL).buildUpon()
                    .appendPath("breed")
                    .appendPath(breed)
                    .appendPath("images")
                    .build();
        }

        try
        {
            url = new URL(uri.toString());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return url;
    }

    public String getJSON(URL url) throws IOException
    {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream stream = connection.getInputStream();
        Scanner scanner = new Scanner(stream);
        scanner.useDelimiter("\\A");

        boolean hasData = scanner.hasNext();

        try
        {
            if (hasData)
            {
                return scanner.next();
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            Log.d("Error", e.toString());
            return null;
        }
        finally
        {
            connection.disconnect();
        }
    }

    public Uri getImage(String json)
    {
        final String MESSAGE = "message";
        Random random = new Random();
        Uri imageURL = null;

        try
        {
            JSONObject imageObj = new JSONObject(json);
            Object imageMsg = imageObj.get(MESSAGE);
            if (imageMsg instanceof String)
                imageURL = Uri.parse(imageMsg.toString());
            else if (imageMsg instanceof JSONArray)
            {
                int urlIndex = random.nextInt(((JSONArray) imageMsg).length() + 1);
                imageURL = Uri.parse(((JSONArray) imageMsg).getString(urlIndex));
            }
        }
        catch (JSONException jse)
        {
            jse.printStackTrace();
        }
        return imageURL;
    }
}