package com.andersen.dogsapp.dogs.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkHelper {
    public static final String TAG = "#";

    public static boolean hasNetWorkAccess(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d(TAG, "ONLINE");
            return true;
        } else {
            Log.d(TAG, "NOT ONLINE");
            return false;
        }
    }

}
