package com.hsallajo.gradle.networkutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static boolean isOnline(Context context){
        ConnectivityManager c =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (c == null)
            return false;

        NetworkInfo info = c.getActiveNetworkInfo();

        return (info != null
                && info.isConnectedOrConnecting()
                && info.isConnected());
    }
}
