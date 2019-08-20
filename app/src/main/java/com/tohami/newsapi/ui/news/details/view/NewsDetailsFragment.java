package com.tohami.newsapi.ui.news.details.view;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tohami.newsapi.R;
import com.tohami.newsapi.ui.base.BaseFragment;
import com.tohami.newsapi.data.model.NewsArticle;

import static com.tohami.newsapi.utilities.Constants.News.ARGS_ARTICLE;

public class NewsDetailsFragment extends BaseFragment {

    private ImageView ivArticleImage;
    private TextView tvArticleTitle;
    private TextView tvArticleAuthor;
    private TextView tvArticlePublishDate;
    private TextView tvArticleDescription;
    private Button btnOpenWebsite;
    private NewsArticle newsArticle;
    private View articleContainer , tvError ;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsArticle = getArticleFromBundle(getArguments()) ;

        updateUi(newsArticle);
    }

    private NewsArticle getArticleFromBundle(@Nullable Bundle args) {
        if(args != null && args.containsKey(ARGS_ARTICLE)){
            return new Gson().fromJson(args.getString(ARGS_ARTICLE) , NewsArticle.class);
        }else {
            return null ;
        }
    }

    private void updateUi(@Nullable NewsArticle newsArticle) {
        if(newsArticle != null) {
            tvArticleTitle.setText(newsArticle.getTitle());
            tvArticleAuthor.setText(newsArticle.getAuthor());
            tvArticlePublishDate.setText(newsArticle.getPublishedAtFormatted());
            tvArticleDescription.setText(newsArticle.getDescription());
            if (newsArticle.getUrlToImage() == null || newsArticle.getUrlToImage().isEmpty())
                ivArticleImage.setImageResource(R.drawable.temp_image);
            else {
                Picasso.get().load(newsArticle.getUrlToImage())
                        .error(R.drawable.temp_image)
                        .placeholder(R.drawable.temp_image)
                        .into(ivArticleImage);
            }
        }else {
            articleContainer.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected View initializeViews(int layoutId, LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(layoutId, container, false);

        articleContainer = rootView.findViewById(R.id.article_container) ;
        tvError = rootView.findViewById(R.id.tv_error) ;
        ivArticleImage = rootView.findViewById(R.id.iv_article_image);
        tvArticleTitle = rootView.findViewById(R.id.tv_article_title);
        tvArticleAuthor = rootView.findViewById(R.id.tv_article_author);
        tvArticlePublishDate = rootView.findViewById(R.id.tv_article_publish_date);
        tvArticleDescription = rootView.findViewById(R.id.tv_article_description);
        btnOpenWebsite = rootView.findViewById(R.id.btn_open_website);
        return rootView;
    }

    @Override
    protected void setListeners() {
        btnOpenWebsite.setOnClickListener(view -> {
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(view.getContext(), Uri.parse(newsArticle.getUrl()));
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_details;
    }
}
