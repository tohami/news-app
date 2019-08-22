package com.tohami.newsapi.ui.news.list.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.tohami.newsapi.R;
import com.tohami.newsapi.data.model.DataModel;
import com.tohami.newsapi.ui.base.BaseFragment;
import com.tohami.newsapi.data.model.NewsArticle;
import com.tohami.newsapi.ui.news.list.viewModel.NewsListViewModel;
import com.tohami.newsapi.ui.news.list.viewModel.NewsListViewModelFactory;
import com.victor.loading.book.BookLoading;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

import static com.tohami.newsapi.utilities.Constants.News.ARGS_ARTICLE;

public class NewsListFragment extends BaseFragment implements NewsListAdapter.OnItemClickedListener {
    private RecyclerView rvNews;
    private SwipeRefreshLayout rlNews;
    private TextView tvError;
    private BookLoading bookLoading ;
    private NewsListAdapter newsListAdapter;

    private NewsListViewModel mViewModel;
    @Inject
    NewsListViewModelFactory viewModelFactory;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory)
                .get(NewsListViewModel.class);

        mViewModel.getNewsArticlesSubject().subscribe(getNewsConsumer);
    }

    @Override
    protected View initializeViews(int layoutId, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate( layoutId, container, false);
        
        rlNews = rootView.findViewById(R.id.refresh_layout) ;
        rvNews = rootView.findViewById(R.id.rv_news) ;
        tvError = rootView.findViewById(R.id.tv_error) ;
        bookLoading = rootView.findViewById(R.id.bookloading) ;
        
        newsListAdapter = new NewsListAdapter(new ArrayList<>() , this);
        rvNews.setAdapter(newsListAdapter);
        
        return rootView;
    }

    @Override
    protected void setListeners() {
        rlNews.setOnRefreshListener(() -> {
            mViewModel.refreshNewsList();
            //hide the refresh indication
            rlNews.setRefreshing(false);
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    public void onItemClicked(View view ,NewsArticle item) {
        String articleJson = new Gson().toJson(item);
        Bundle args = new Bundle() ;
        args.putString(ARGS_ARTICLE , articleJson);
        Navigation
                .findNavController(view)
                .navigate(R.id.action_fragmentNewsList_to_fragmentNewsDetails , args);
    }


    private Consumer<DataModel<List<NewsArticle>>> getNewsConsumer = new Consumer<DataModel<List<NewsArticle>>>() {
        @Override
        public void accept(DataModel<List<NewsArticle>> listDataModel) {
            switch (listDataModel.getStatus()) {
                case SUCCESS:
                    rlNews.setEnabled(true);
                    rvNews.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.GONE);
                    bookLoading.setVisibility(View.GONE);
                    newsListAdapter.replaceItems(listDataModel.getResponseBody());
                    break;
                case FAIL:
                    rlNews.setEnabled(true);
                    rvNews.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    bookLoading.setVisibility(View.GONE);
                    tvError.setText(listDataModel.getErrorMsg());
                    break;
                case NO_NETWORK:
                    rlNews.setEnabled(true);
                    rvNews.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
                    bookLoading.setVisibility(View.GONE);
                    tvError.setText(R.string.error_no_internet_connection);
                    break;
                case LOADING:
                    rlNews.setEnabled(false);
                    rvNews.setVisibility(View.GONE);
                    tvError.setVisibility(View.GONE);
                    bookLoading.setVisibility(View.VISIBLE);
                    bookLoading.start();
                    newsListAdapter.replaceItems(new ArrayList<>());
                    break;
                default:
                    newsListAdapter.replaceItems(new ArrayList<>());
            }
        }
    } ;
}
