package com.tohami.newsapi.ui.news.list.viewModel;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.ViewModel;

import com.tohami.newsapi.BuildConfig;
import com.tohami.newsapi.utilities.Constants;
import com.tohami.newsapi.data.model.DataModel;
import com.tohami.newsapi.data.model.NewsArticle;
import com.tohami.newsapi.data.model.NewsResponse;
import com.tohami.newsapi.ui.news.list.data.NewsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

import static com.tohami.newsapi.utilities.Constants.Api.RESULT_OK;

public class NewsListViewModel extends ViewModel {
    private final ConnectivityManager connectivityManager ;

    private final BehaviorSubject<DataModel<List<NewsArticle>>> newsArticlesSubject = BehaviorSubject.create();
    private final NewsRepository newsRepository  ;

    private DisposableSingleObserver<DataModel<List<NewsArticle>>> apiObserver;

    @Inject
    public NewsListViewModel(NewsRepository newsRepository , ConnectivityManager connectivityManager) {
        this.newsRepository = newsRepository ;
        this.connectivityManager = connectivityManager ;

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        apiObserver.dispose();
    }


    public BehaviorSubject<DataModel<List<NewsArticle>>> getNewsArticlesSubject() {
        return newsArticlesSubject;
    }

    public void refreshNewsList() {

        if(!isNetworkAvailable()){
            DataModel<List<NewsArticle>> error = new DataModel<>(Constants.Status.NO_NETWORK, null, null);
            newsArticlesSubject.onNext(error);
            return;
        }

        Single<NewsResponse> theNextWeb = newsRepository.getNews("the-next-web", BuildConfig.API_KEY);

        Single<NewsResponse> associatedPress = newsRepository.getNews("associated-press", BuildConfig.API_KEY);

        apiObserver = Single.zip(theNextWeb , associatedPress , this::combineLatestData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<DataModel<List<NewsArticle>>>() {
            @Override
            public void onSuccess(DataModel<List<NewsArticle>> value) {
                newsArticlesSubject.onNext(value);
            }

            @Override
            public void onError(Throwable e) {
                DataModel<List<NewsArticle>> error = new DataModel<>(Constants.Status.FAIL, e.getMessage(), null);
                newsArticlesSubject.onNext(error);
            }
        });

        DataModel<List<NewsArticle>> loading =new DataModel<>(Constants.Status.LOADING , null , null);
        newsArticlesSubject.onNext(loading);

    }

    private DataModel<List<NewsArticle>> combineLatestData(
            NewsResponse source1,
            NewsResponse source2
    ) {


        if(!source1.getStatus().equals(RESULT_OK)){
            return new DataModel<>(Constants.Status.FAIL, source1.getMessage(), null) ;
        }
        else if(!source2.getStatus().equals(RESULT_OK) ){
            return new DataModel<>(Constants.Status.FAIL, source2.getMessage(), null) ;
        }

        List<NewsArticle> fullList = new ArrayList<>(source1.getArticles()) ;
        fullList.addAll(source2.getArticles()) ;
        return new DataModel<>(Constants.Status.SUCCESS, null, fullList) ;
    }

    private boolean isNetworkAvailable( ) {
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            return activeNetInfo.isAvailable() && activeNetInfo.isConnected();
        } else {
            return false;
        }
    }
}
