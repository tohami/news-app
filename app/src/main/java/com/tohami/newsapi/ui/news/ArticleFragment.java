package com.tohami.newsapi.ui.news;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.tohami.newsapi.R;
import com.tohami.newsapi.base.BaseFragment;
import com.tohami.newsapi.databinding.FragmentArticleBinding;
import com.tohami.newsapi.di.Injection;

public class ArticleFragment extends BaseFragment {

    private FragmentArticleBinding binding;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NewsViewModel mViewModel = ViewModelProviders.of(getActivity(), Injection.provideViewModelFactory(getContext()))
                .get(NewsViewModel.class);

        mViewModel.getSelectedArticleLiveData()
                .observe(this , newsArticle -> binding.setArticle(newsArticle));

        binding.setArticle(mViewModel.getSelectedArticleLiveData().getValue());

    }

    @Override
    protected View initializeViews(int layoutId, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,layoutId , container, false);
        return binding.getRoot();
    }

    @Override
    protected void setListeners() {
        binding.btnOpenWebsite.setOnClickListener(view -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(view.getContext(), Uri.parse(binding.getArticle().getUrl()));
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_article;
    }
}
