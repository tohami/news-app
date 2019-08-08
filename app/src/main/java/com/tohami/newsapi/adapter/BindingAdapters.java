package com.tohami.newsapi.adapter;
import androidx.databinding.BindingAdapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingAdapters {
        @BindingAdapter({"imageUrl", "error"})
        public static void loadImage(ImageView view, String url, Drawable error) {
            if(url == null || url.isEmpty())
                view.setImageDrawable(error);
            else {
                Picasso.get().load(url).error(error).placeholder(error).into(view);
            }
        }

}
