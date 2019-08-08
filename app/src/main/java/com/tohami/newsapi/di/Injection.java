package com.tohami.newsapi.di;


import android.content.Context;

import androidx.annotation.NonNull;

import com.tohami.newsapi.data.retrofit.ServiceHelper;
import com.tohami.newsapi.factory.RepositoryFactory;
import com.tohami.newsapi.factory.ViewModelFactory;

public class Injection {


    @NonNull
    private static RepositoryFactory provideRepositoryFactory(Context context) {
        return new RepositoryFactory(ServiceHelper.getInstance().getRetrofitInterface(context));
    }


    @NonNull
    public static ViewModelFactory provideViewModelFactory(Context context) {
        return new ViewModelFactory(provideRepositoryFactory(context));
    }
}