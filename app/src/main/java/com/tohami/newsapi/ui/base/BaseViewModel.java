package com.tohami.newsapi.ui.base;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {
    private final ConnectivityManager connectivityManager ;

    private CompositeDisposable compositeDisposable ;

    public BaseViewModel(ConnectivityManager connectivityManager){
        this.connectivityManager = connectivityManager ;
        this.compositeDisposable = new CompositeDisposable() ;
    }

    protected void addDisposable(Disposable disposable){
        this.compositeDisposable.add(disposable) ;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        this.compositeDisposable.clear();
    }

    protected boolean isNetworkAvailable( ) {
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            return activeNetInfo.isAvailable() && activeNetInfo.isConnected();
        } else {
            return false;
        }
    }
}
