package com.tohami.newsapi.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tohami.newsapi.R;
import com.tohami.newsapi.adapter.NewsAdapter;
import com.tohami.newsapi.base.BaseFragment;
import com.tohami.newsapi.data.model.NewsArticle;
import com.tohami.newsapi.databinding.FragmentNewsBinding;
import com.tohami.newsapi.di.Injection;

import java.util.ArrayList;

public class NewsFragment extends BaseFragment implements NewsAdapter.OnItemClickedListener {

    private NewsViewModel mViewModel;
    private FragmentNewsBinding binding;


    public static NewsFragment newInstance() {
        return new NewsFragment();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity(), Injection.provideViewModelFactory(getContext()))
                .get(NewsViewModel.class);

        NewsAdapter adapter = new NewsAdapter(new ArrayList<NewsArticle>() , this);
        binding.rvNews.setAdapter(adapter);

        mViewModel.getNewsArticlesLiveData().observe(this, listDataModel -> {
            switch (listDataModel.getStatus()) {
                case SUCCESS:
                    binding.refreshLayout.setEnabled(true);
                    binding.rvNews.setVisibility(View.VISIBLE);
                    binding.errMsg.setVisibility(View.GONE);
                    binding.bookloading.setVisibility(View.GONE);
                    adapter.replaceItems(listDataModel.getResponseBody());
                    break;
                case FAIL:
                    binding.refreshLayout.setEnabled(true);
                    binding.rvNews.setVisibility(View.GONE);
                    binding.errMsg.setVisibility(View.VISIBLE);
                    binding.bookloading.setVisibility(View.GONE);
                    binding.errMsg.setText(listDataModel.getErrorMsg());
                    break;
                case LOADING:
                    binding.refreshLayout.setEnabled(false);
                    binding.rvNews.setVisibility(View.GONE);
                    binding.errMsg.setVisibility(View.GONE);
                    binding.bookloading.setVisibility(View.VISIBLE);
                    binding.bookloading.start();
                    adapter.replaceItems(new ArrayList<>());
                    break;
                default:
                    adapter.replaceItems(new ArrayList<>());
            }
        });

        mViewModel.refreshNewsList();
    }

    @Override
    protected View initializeViews(int layoutId, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, layoutId, container, false);
        return binding.getRoot();
    }

    @Override
    protected void setListeners() {
        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.refreshNewsList();
                //hide the refresh indication
                binding.refreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void onItemClicked(NewsArticle item) {
        mViewModel.setSelcetedArticle(item) ;
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_fragmentNewsList_to_fragmentArticle);
    }
}
