package com.tohami.newsapi.utilities;

import android.content.Context;
import android.content.res.Resources;

import java.util.Locale;

import static com.tohami.newsapi.utilities.Constants.Prefs.APP_LOCALE_KEY;

/**
 * Created by Sherif.ElNady on 10/19/2016.
 */
public class LocalizationHelper {

    public static final String LOCALE_ENGLISH = "en";
    public static final String LOCALE_ARABIC = "ar";

    private static final String DEFAULT_LOCALE = LOCALE_ENGLISH;

    public static void changeAppLanguage(String languageToLoad, Context ctx) {

        try {
            if (languageToLoad != null && !"".equals(languageToLoad)) {
                Resources res = ctx.getApplicationContext().getResources();
                android.content.res.Configuration config = res.getConfiguration();

                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                config.setLocale(locale);
                ctx.getResources().updateConfiguration(config, ctx.getResources().getDisplayMetrics());

				// save new language to shared preferences
                AppPreferences.setString(APP_LOCALE_KEY, languageToLoad, ctx);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String getLanguage(Context context) {
		// get language from shared preferences
        return AppPreferences.getString(APP_LOCALE_KEY, context, DEFAULT_LOCALE);
    }

}
