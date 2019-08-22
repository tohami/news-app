package com.tohami.newsapi.dagger.module;

import androidx.lifecycle.ViewModelProvider;

import com.tohami.newsapi.ui.news.list.viewModel.NewsListViewModelFactory;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(NewsListViewModelFactory factory);
}