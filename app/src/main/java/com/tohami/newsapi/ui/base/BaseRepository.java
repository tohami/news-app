package com.tohami.newsapi.ui.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.tohami.newsapi.data.retrofit.ApiInterface;
import com.tohami.newsapi.utilities.NetworkHelper;

public abstract class BaseRepository {

    private final ApiInterface newsApi;

    public BaseRepository(ApiInterface newsApi ) {
        this.newsApi = newsApi;
    }

    protected ApiInterface getNewsApi() {
        return newsApi;
    }

}
