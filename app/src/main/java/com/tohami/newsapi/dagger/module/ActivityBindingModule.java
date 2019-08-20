package com.tohami.newsapi.dagger.module;

import com.tohami.newsapi.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainActivityBindingModule.class})
    abstract MainActivity bindMainActivity();
}