package com.tohami.newsapi.data.retrofit;

import android.content.Context;

import com.tohami.newsapi.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceHelper {

    private static ServiceHelper mInstance;
    private ApiInterface retrofitInterface;

    public static synchronized ServiceHelper getInstance() {
        if (mInstance == null) {
            mInstance = new ServiceHelper();
        }
        return mInstance;
    }

    private OkHttpClient buildOkHttpClient(final Context context) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.MINUTES)
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .build();
    }

    public ApiInterface getRetrofitInterface(Context context) {

        if (retrofitInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(buildOkHttpClient(context.getApplicationContext()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            retrofitInterface = retrofit.create(ApiInterface.class);
        }

        return retrofitInterface;
    }
}
