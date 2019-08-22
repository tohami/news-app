package com.tohami.newsapi.ui.news.details.data;

import com.tohami.newsapi.data.model.NewsArticle;
import com.tohami.newsapi.data.model.NewsResponse;
import com.tohami.newsapi.data.retrofit.ApiInterface;
import com.tohami.newsapi.ui.base.BaseRepository;

import javax.inject.Inject;

import io.reactivex.Single;

public class NewsDetailsRepository extends BaseRepository {

    @Inject
    public NewsDetailsRepository(ApiInterface newsApi) {
        super(newsApi);
    }

    public Single<NewsArticle> getArticle(String id) {
        return null;
    }
}