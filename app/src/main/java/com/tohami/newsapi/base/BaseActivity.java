package com.tohami.newsapi.base;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.tohami.newsapi.NewsApplication;
import com.tohami.newsapi.R;
import com.tohami.newsapi.utilities.LocaleContextWrapper;

import static com.tohami.newsapi.Constants.Prefs.LANGUAGE_KEY;
import static com.tohami.newsapi.utilities.LocalizationHelper.LOCALE_ENGLISH;



public abstract class BaseActivity extends AppCompatActivity {

    protected Toolbar myToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initializeViews();
        setListeners();
    }

    protected void setToolbar(Toolbar toolbar, int toolbarBackgroundColor, String title, boolean showUpButton, boolean withElevation) {
        myToolbar = toolbar;
        myToolbar.setTitle(title);
        myToolbar.setBackgroundColor(ContextCompat.getColor(this, toolbarBackgroundColor));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && withElevation) {
            toolbar.setElevation(getResources().getDimension(R.dimen.row_item_margin_horizontal));
        }
        setSupportActionBar(myToolbar);

        showUpActionButton(showUpButton);
    }

    public void setToolbarTitle(String title) {
        if (myToolbar != null) {
            myToolbar.setTitle(title);
        }
    }

    private void showUpActionButton(boolean show) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(show);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String language = NewsApplication.preferences.getString(LANGUAGE_KEY, LOCALE_ENGLISH);
            ContextWrapper contextWrapper = LocaleContextWrapper.wrap(newBase, language);
            super.attachBaseContext(contextWrapper);
        } else {
            super.attachBaseContext(newBase);
        }
    }


    protected abstract void initializeViews();

    protected abstract void setListeners();

    protected abstract int getLayoutId();
}
