package com.tohami.newsapi.utilities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;


public class LocaleContextWrapper extends ContextWrapper {

    private LocaleContextWrapper(Context base) {
        super(base);
    }
    public static ContextWrapper wrap(Context context, String language) {
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();
        Locale newLocale = new Locale(language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(newLocale);
            LocaleList localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);

        } else {
            configuration.setLocale(newLocale);
            context = context.createConfigurationContext(configuration);

        }

        return new LocaleContextWrapper(context);
    }

}
