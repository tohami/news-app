package com.tohami.newsapi.ui.news.details.viewModel;

import android.net.ConnectivityManager;

import com.tohami.newsapi.data.model.DataModel;
import com.tohami.newsapi.data.model.NewsArticle;
import com.tohami.newsapi.ui.base.BaseViewModel;
import com.tohami.newsapi.ui.news.list.data.NewsRepository;
import com.tohami.newsapi.utilities.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class NewsDetailsViewModel extends BaseViewModel {

    private BehaviorSubject<DataModel<NewsArticle>> newsArticlesSubject = BehaviorSubject.create();
    private NewsArticle article;

    NewsDetailsViewModel(NewsRepository newsRepository, ConnectivityManager connectivityManager , NewsArticle article) {
        super(connectivityManager);
        this.article = article ;
    }

    public Observable<DataModel<NewsArticle>> getNewsDetailsObservable() {

        //simulate network delay
        addDisposable(Observable.timer(3, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(a -> {
                    DataModel<NewsArticle> articleDataModel = new DataModel<>(Constants.Status.SUCCESS, null, article);
                    newsArticlesSubject.onNext(articleDataModel);
                })
        );

        //send loading
        DataModel<NewsArticle> loading = new DataModel<>(Constants.Status.LOADING, null, null);
        newsArticlesSubject.onNext(loading);

        return newsArticlesSubject;
    }


}
