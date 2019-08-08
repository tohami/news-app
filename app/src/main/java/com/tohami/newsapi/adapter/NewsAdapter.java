package com.tohami.newsapi.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tohami.newsapi.data.model.NewsArticle;
import com.tohami.newsapi.databinding.ItemNewsBinding;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleItemHolder> {

    private final List<NewsArticle> newsList;
    private final OnItemClickedListener onItemClickListener;

    public NewsAdapter(List<NewsArticle> list, OnItemClickedListener clickListener) {
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
        //Uses DataBinding to inflate the Item View
        ItemNewsBinding itemBinding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleItemHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleItemHolder holder, int position) {
        holder.bind(newsList.size() > position ? newsList.get(position) : null);
    }

    /**
     * View Holder for a {@link NewsArticle} RecyclerView list item.
     */
    public class ArticleItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemNewsBinding mDataBinding;

        ArticleItemHolder(ItemNewsBinding itemBinding) {
            super(itemBinding.getRoot());
            this.mDataBinding = itemBinding;

            View itemView = itemBinding.getRoot();
            itemView.setOnClickListener(this);
        }

        private void bind(NewsArticle article) {

            if (article != null) {
                //When article is not null, data binding will be automatically done in the layout
                mDataBinding.setArticle(article);
                //For Immediate Binding
                mDataBinding.executePendingBindings();
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
                onItemClickListener.onItemClicked(article);

            }
        }
    }


    public interface OnItemClickedListener {
        void onItemClicked(NewsArticle item);
    }
}