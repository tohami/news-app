package com.tohami.newsapi.dagger.module;

import com.tohami.newsapi.ui.news.details.view.NewsDetailsFragment;
import com.tohami.newsapi.ui.news.list.view.NewsListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = ViewModelModule.class)
public abstract class MainActivityBindingModule {

    @ContributesAndroidInjector
    abstract NewsListFragment provideNewsListFragment();

    @ContributesAndroidInjector
    abstract NewsDetailsFragment provideNewsDetailsFragment();
}
