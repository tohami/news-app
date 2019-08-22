package com.tohami.newsapi.ui.news.list.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tohami.newsapi.R;
import com.tohami.newsapi.data.model.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ArticleItemHolder> {

    private final List<NewsArticle> newsList;
    private final OnItemClickedListener onItemClickListener;

    public NewsListAdapter(List<NewsArticle> list, OnItemClickedListener clickListener) {
        newsList = new ArrayList<>(list);
        this.onItemClickListener = clickListener;
    }

    public void addItems(List<NewsArticle> list) {
        newsList.addAll(list);
        notifyDataSetChanged();
    }

    public void replaceItems(List<NewsArticle> list) {
        newsList.clear();
        newsList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        newsList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @NonNull
    @Override
    public ArticleItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new ArticleItemHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleItemHolder holder, int position) {
        holder.bind(newsList.size() > position ? newsList.get(position) : null);
    }

    /**
     * View Holder for a {@link NewsArticle} RecyclerView list item.
     */
    public class ArticleItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivArticleImage;
        TextView tvArticleTitle;
        TextView tvArticleAuthor;
        TextView tvArticlePublishDate;

        ArticleItemHolder(View rootView) {
            super(rootView);

            ivArticleImage = rootView.findViewById(R.id.iv_article_image);
            tvArticleTitle = rootView.findViewById(R.id.tv_article_title);
            tvArticleAuthor = rootView.findViewById(R.id.tv_article_author);
            tvArticlePublishDate = rootView.findViewById(R.id.tv_article_publish_date);

            itemView.setOnClickListener(this);
        }

        private void bind(NewsArticle article) {

            if (article != null) {
                tvArticleTitle.setText(article.getTitle());
                tvArticleAuthor.setText(article.getAuthor());
                tvArticlePublishDate.setText(article.getPublishedAt());

                if(article.getUrlToImage() == null || article.getUrlToImage().isEmpty())
                    ivArticleImage.setImageResource(R.drawable.temp_image);
                else {
                    Picasso.get().load(article.getUrlToImage())
                            .error(R.drawable.temp_image)
                            .placeholder(R.drawable.temp_image)
                            .into(ivArticleImage);
                }
            }
        }

        /**
         * Called when a view has been clicked.
         *
         * @param view The view that was clicked.
         */
        @Override
        public void onClick(View view) {
            if (getAdapterPosition() > RecyclerView.NO_POSITION && getAdapterPosition() < newsList.size()) {
                NewsArticle article = newsList.get(getAdapterPosition());
                onItemClickListener.onItemClicked(view , article);

            }
        }
    }


    public interface OnItemClickedListener {
        void onItemClicked(View view , NewsArticle item);
    }
}