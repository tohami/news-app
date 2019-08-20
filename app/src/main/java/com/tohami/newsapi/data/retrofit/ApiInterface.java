package com.tohami.newsapi.data.retrofit;

import com.tohami.newsapi.data.model.NewsResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("articles")
    Single<NewsResponse> getNewsList(@Query("source") String newsSource,
                                     @Query("apiKey") String apiKey);
}
