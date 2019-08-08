package com.tohami.newsapi.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected abstract View initializeViews(int layoutId , LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState);

    protected abstract void setListeners();
    protected abstract int getLayoutId() ;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = initializeViews(getLayoutId() , inflater , container , savedInstanceState);
        setListeners();

        return rootView ;
    }
}
