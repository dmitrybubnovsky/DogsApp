package com.andersen.dogsapp.dogs.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class WebBreedService extends IntentService {
    public static final String BREEDS_SERVICE_MESSAGE = "MESSAGE";
    public static final String BREEDS_SERVICE_PAYLOAD = "PAYLOAD";
    public static final String TAG = "#";

    public WebBreedService() {
        super("WebBreedService.class.getSimpleName()");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Uri uri = intent.getData();
        Log.d(TAG, "onHandleIntent: "+uri.toString());



        Intent messageIntent = new Intent (BREEDS_SERVICE_MESSAGE);
        messageIntent.putExtra(BREEDS_SERVICE_PAYLOAD, "Service all done");
        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate WebBreddService ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy WebBreddService ");
    }
}
