package com.tohami.newsapi.ui.main;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.tohami.newsapi.R;
import com.tohami.newsapi.ui.base.BaseActivity;
import com.tohami.newsapi.ui.news.list.viewModel.NewsListViewModel;
import com.tohami.newsapi.ui.news.list.viewModel.NewsListViewModelFactory;
import com.tohami.newsapi.utilities.ViewHelpers;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;
    private NewsListViewModel mViewModel;

    @Inject
    NewsListViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsListViewModel.class);

        if (savedInstanceState == null)
            mViewModel.refreshNewsList();
    }

    @Override
    protected void initializeViews() {
        Toolbar appbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nvView);
        setToolbar(appbar, R.color.colorPrimary, getString(R.string.app_name), true, false);
        navController = Navigation.findNavController(this, R.id.main_nav_container);
    }

    private void setUpNavDrawerListeners() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, myToolbar, R.string.app_name, R.string.app_name) {

            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }
        };

        mDrawerToggle.setToolbarNavigationClickListener(view -> navController.popBackStack());

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            ViewHelpers.showToast(
                    MainActivity.this,
                    getString(R.string.clicked, menuItem.getTitle())
            );

            drawerLayout.closeDrawers();
            return false;
        });

        drawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void enableDisableNavDrawer(boolean enable) {
        mDrawerToggle.setDrawerIndicatorEnabled(enable);
        drawerLayout.setDrawerLockMode(enable ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerToggle.syncState();
    }

    @Override
    protected void setListeners() {
        setUpNavDrawerListeners();
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()) {
                case R.id.fragmentNewsList:
                    enableDisableNavDrawer(true);

                    break;
                case R.id.fragmentArticle:
                    enableDisableNavDrawer(false);
                    break;
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }


}
