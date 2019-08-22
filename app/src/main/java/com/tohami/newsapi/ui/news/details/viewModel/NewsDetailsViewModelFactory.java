package com.tohami.newsapi.ui.news.details.viewModel;


import android.net.ConnectivityManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tohami.newsapi.data.model.NewsArticle;
import com.tohami.newsapi.ui.news.list.data.NewsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class NewsDetailsViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    ConnectivityManager connectivityManager;
    NewsRepository repository;

    private final NewsArticle article;

    public NewsDetailsViewModelFactory(NewsArticle article) {
        this.article = article ;

    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return (T) new NewsDetailsViewModel(repository, connectivityManager, article);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}