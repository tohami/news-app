package com.tohami.newsapi.app;

import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.tohami.newsapi.utilities.Constants;
import com.tohami.newsapi.app.di.ApplicationComponent;
import com.tohami.newsapi.app.di.DaggerApplicationComponent;
import com.tohami.newsapi.utilities.LocalizationHelper;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

import static com.tohami.newsapi.utilities.Constants.Prefs.LANGUAGE_KEY;
import static com.tohami.newsapi.utilities.LocalizationHelper.LOCALE_ENGLISH;

public class NewsApplication extends DaggerApplication {

	public static SharedPreferences preferences ;

	@Override
	public void onCreate() {
		preferences = getSharedPreferences(Constants.Prefs.PREF_FILE_NAME, MODE_PRIVATE);
		LocalizationHelper.changeAppLanguage(null, this);
		super.onCreate();

		setupPicasso() ;
	}

	private void setupPicasso() {
		Picasso.Builder picassoBuilder = new Picasso.Builder(this);
		picassoBuilder.downloader(new OkHttp3Downloader(this , Integer.MAX_VALUE));
		picassoBuilder.loggingEnabled(true) ;
		picassoBuilder.indicatorsEnabled(true) ;

		Picasso picasso = picassoBuilder.build();

		Picasso.setSingletonInstance(picasso);

		try {
			Picasso.setSingletonInstance(picasso);
		} catch (IllegalStateException ex) {
			// Picasso instance was already set
			// cannot set it after Picasso.with(Context) was already in use
			ex.printStackTrace();
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		String language = preferences.getString(LANGUAGE_KEY, LOCALE_ENGLISH);

		super.onConfigurationChanged(newConfig);
		LocalizationHelper.changeAppLanguage(language , this);
	}

	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		ApplicationComponent component = DaggerApplicationComponent.builder().application(this).build();
		component.inject(this);

		return component;
	}
}
