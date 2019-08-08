package com.tohami.newsapi.base;


import androidx.lifecycle.ViewModel;

import com.tohami.newsapi.factory.RepositoryFactory;

public abstract class BaseViewModel extends ViewModel {

    private final RepositoryFactory repositoryFactory;

    public BaseViewModel(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

}