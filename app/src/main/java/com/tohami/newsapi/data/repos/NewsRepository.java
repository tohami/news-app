package com.tohami.newsapi.data.repos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tohami.newsapi.Constants;
import com.tohami.newsapi.base.BaseRepository;
import com.tohami.newsapi.data.model.DataModel;
import com.tohami.newsapi.data.model.NewsResponse;
import com.tohami.newsapi.data.retrofit.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository extends BaseRepository {

    final private List<Call> networkCalls ;

    public NewsRepository(ApiInterface newsApi) {
        super(newsApi);
        networkCalls = new ArrayList<>() ;
    }

    @Override
    public void cancelAllRequests() {
        for (Call networkCall : networkCalls) {
            networkCall.cancel() ;
        }
    }


    public LiveData<DataModel<NewsResponse>> getNews(String source, String key) {
        final MutableLiveData<DataModel<NewsResponse>> newsData = new MutableLiveData<>();

        Call<NewsResponse> networkCall = getNewsApi().getNewsList(source, key) ;
        networkCalls.add(networkCall) ;
        networkCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(@NotNull Call<NewsResponse> call,
                                   @NotNull Response<NewsResponse> response) {
                newsData.setValue(mapNetworkResponseToDataModel(response));
            }

            @Override
            public void onFailure(@NotNull Call<NewsResponse> call,@NotNull Throwable t) {
                String error = /*t.getLocalizedMessage() != null
                        ? t.getLocalizedMessage()
                        :*/ Constants.Api.DEFAULT_NETWORK_ERROR;
                DataModel<NewsResponse> result =
                        new DataModel<>(Constants.Status.FAIL, error, null);

                newsData.setValue(result);
            }
        });
        return newsData;
    }
}