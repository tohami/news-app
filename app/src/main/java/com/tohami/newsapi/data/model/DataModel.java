package com.tohami.newsapi.data.model;

import com.tohami.newsapi.Constants;

//mutable response body
public class DataModel<T> {
    private final Constants.Status status ;
    private final String errorMsg ;
    private final T responseBody ;

    /**
     * generic data model to be used in communication between repo and viewModel
     * @param status the response status one on @{@link com.tohami.newsapi.Constants.Status}
     * @param errorMsg the error message , will be null if response is success
     * @param responseBody the response , will be null if request failed
     */
    public DataModel(Constants.Status status, String errorMsg, T responseBody) {
        this.status = status;
        this.errorMsg = errorMsg;
        this.responseBody = responseBody;
    }

    public Constants.Status getStatus() {
        return status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public T getResponseBody() {
        return responseBody;
    }
}
