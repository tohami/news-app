package com.tohami.newsapi.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.tohami.newsapi.BuildConfig;
import com.tohami.newsapi.Constants;
import com.tohami.newsapi.base.BaseViewModel;
import com.tohami.newsapi.data.model.DataModel;
import com.tohami.newsapi.data.model.NewsArticle;
import com.tohami.newsapi.data.model.NewsResponse;
import com.tohami.newsapi.data.repos.NewsRepository;
import com.tohami.newsapi.factory.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;

class NewsViewModel extends BaseViewModel {

    private final MediatorLiveData<DataModel<List<NewsArticle>>> newsArticlesLiveData = new MediatorLiveData<>() ;
    private final MutableLiveData<NewsArticle> selectedArticleLiveData = new MutableLiveData<>() ;
    private final NewsRepository newsRepository  ;

    public NewsViewModel(RepositoryFactory repositoryFactory) {
        super(repositoryFactory);
        this.newsRepository = repositoryFactory.create(NewsRepository.class) ;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        newsRepository.cancelAllRequests() ;
    }


    MediatorLiveData<DataModel<List<NewsArticle>>> getNewsArticlesLiveData() {
        return newsArticlesLiveData;
    }

    void refreshNewsList() {
        final LiveData<DataModel<NewsResponse>> theNextWeb =
                newsRepository.getNews("the-next-web", BuildConfig.API_KEY);

        final LiveData<DataModel<NewsResponse>> associatedPress =
                newsRepository.getNews("associated-press", BuildConfig.API_KEY);

        newsArticlesLiveData.addSource(
                theNextWeb ,
                newsResponseDataModel ->
                        newsArticlesLiveData.setValue( combineLatestData(theNextWeb , associatedPress))
        );

        newsArticlesLiveData.addSource(
                associatedPress ,
                newsResponseDataModel ->
                        newsArticlesLiveData.setValue( combineLatestData(theNextWeb , associatedPress))
        );

        newsArticlesLiveData.setValue(new DataModel<>(Constants.Status.LOADING , null , null));
    }


    private DataModel<List<NewsArticle>> combineLatestData(
            LiveData<DataModel<NewsResponse>> source1LiveData,
            LiveData<DataModel<NewsResponse>> source2LiveData
    ) {

        DataModel<NewsResponse> source1Value = source1LiveData.getValue() ;
        DataModel<NewsResponse> source2Value = source2LiveData.getValue() ;

        // Don't send a success until we have both results
        if (source1Value == null || source2Value == null) {
            return new DataModel<>(Constants.Status.LOADING, null, null) ;
        }

        if(source1Value.getStatus() == Constants.Status.FAIL ){
            return new DataModel<>(Constants.Status.FAIL, source1Value.getErrorMsg(), null) ;
        }else if(source2Value.getStatus() == Constants.Status.FAIL){
            return new DataModel<>(Constants.Status.FAIL, source2Value.getErrorMsg(), null) ;
        }

        List<NewsArticle> fullList = new ArrayList<>(source1Value.getResponseBody().getArticles()) ;
        fullList.addAll(source2Value.getResponseBody().getArticles()) ;
        return new DataModel<>(Constants.Status.SUCCESS, null, fullList) ;
    }

    public void setSelcetedArticle(NewsArticle item) {
        selectedArticleLiveData.postValue(item);
    }

    public MutableLiveData<NewsArticle> getSelectedArticleLiveData() {
        return selectedArticleLiveData;
    }
}
