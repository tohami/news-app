package com.tohami.newsapi.data.retrofit;

import com.tohami.newsapi.data.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("articles")
    Call<NewsResponse> getNewsList(@Query("source") String newsSource,
                                   @Query("apiKey") String apiKey);
}
