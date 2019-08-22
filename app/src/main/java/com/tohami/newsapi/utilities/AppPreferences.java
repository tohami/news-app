package com.tohami.newsapi.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


class AppPreferences {

    static String getString(String key, Context ctx, String defaultValue) {
        SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        return appPreferences.getString(key, defaultValue);
    }

    static void setString(String key, String value, Context ctx) {
        SharedPreferences appPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        appPreferences.edit().putString(key, value).apply();
    }

}
