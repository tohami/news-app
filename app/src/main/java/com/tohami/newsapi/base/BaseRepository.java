package com.tohami.newsapi.base;

import com.tohami.newsapi.Constants;
import com.tohami.newsapi.data.model.DataModel;
import com.tohami.newsapi.data.retrofit.ApiInterface;

import org.json.JSONObject;

import retrofit2.Response;

public abstract class BaseRepository {

    private final ApiInterface newsApi  ;

    public BaseRepository(ApiInterface newsApi){
        this.newsApi = newsApi ;
    }

    protected ApiInterface getNewsApi() {
        return newsApi;
    }

    /**
     * map retrofit response to data model based on response status
     * @param response retrofit call response
     * @param <T> the response generic type
     * @return return @{@link DataModel} of the result or error
     */
    protected <T>DataModel<T> mapNetworkResponseToDataModel(Response<T> response) {
        DataModel<T> result  ;
        if(response.isSuccessful()){
            result = new DataModel<>(Constants.Status.SUCCESS ,null , response.body()) ;
        }else {
            String error = "";
            try {
                JSONObject jsonError = new JSONObject(response.errorBody().string());
                error = jsonError.getJSONObject("error").getString("message");
            } catch (Exception e) {
                error = Constants.Api.DEFAULT_REQUEST_ERROR ;
            }finally {
                result = new DataModel<>(Constants.Status.FAIL , error , null) ;
            }
        }
        return result ;
    }

    public abstract void cancelAllRequests();
}
