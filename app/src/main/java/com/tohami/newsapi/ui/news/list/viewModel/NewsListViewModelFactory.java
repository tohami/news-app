package com.tohami.newsapi.ui.news.list.viewModel;


import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tohami.newsapi.ui.news.list.data.NewsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class NewsListViewModelFactory implements ViewModelProvider.Factory {

    private final ConnectivityManager connectivityManager;
    private NewsRepository repository ;

    @Inject
    public NewsListViewModelFactory(NewsRepository repository , ConnectivityManager connectivityManager) {
        this.repository = repository ;
        this.connectivityManager = connectivityManager ;

    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return (T)new NewsListViewModel(repository , connectivityManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}