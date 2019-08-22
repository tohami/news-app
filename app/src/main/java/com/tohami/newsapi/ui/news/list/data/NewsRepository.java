package com.tohami.newsapi.ui.news.list.data;

import android.net.ConnectivityManager;

import com.tohami.newsapi.data.model.NewsResponse;
import com.tohami.newsapi.data.retrofit.ApiInterface;
import com.tohami.newsapi.ui.base.BaseRepository;
import com.tohami.newsapi.utilities.NetworkHelper;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsRepository extends BaseRepository {

    @Inject
    public NewsRepository(ApiInterface newsApi) {
        super(newsApi);
    }

    public Single<NewsResponse> getNews(String source, String key) {
        return getNewsApi().getNewsList(source, key);
    }
}