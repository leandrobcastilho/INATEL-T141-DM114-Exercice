package com.example.leandrocastilho.helowoordturbo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.leandrocastilho.helowoordturbo.R;

public class WSSalesUtil {

    private WSSalesUtil() {
    }

    public static String getHostAddress(Context context) {

        String host;
        String baseAddress;
        int port;
        SharedPreferences sharedSettings = PreferenceManager.getDefaultSharedPreferences(context);
        host = sharedSettings.getString(
                context.getString(R.string.pref_ws_sales_host),
                context.getString(R.string.pref_ws_sales_default_host));
        port = sharedSettings.getInt(
                context.getString(R.string.pref_host_port),
                Integer.parseInt(context.getString(R.string.pref_ws_default_port)));

        if (host.endsWith("/")) {
            host = host.substring(0, host.length() - 1);
        }

        //HTTP
        //if (!host.startsWith("https://")) {
        //    host = "https://" + host;
        //}
        if (!host.startsWith("https://")) {
            host = "https://" + host;
        }

        //baseAddress = host + ":" + port;//HTTP
        baseAddress = host;
        return baseAddress;
    }

}
