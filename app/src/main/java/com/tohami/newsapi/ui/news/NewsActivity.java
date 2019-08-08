package com.tohami.newsapi.ui.news;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.tohami.newsapi.R;
import com.tohami.newsapi.base.BaseActivity;

public class NewsActivity extends BaseActivity {
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            Toast.makeText(
                    NewsActivity.this,
                    getString(R.string.clicked , menuItem.getTitle() , "\uD83D\uDE02"),
                    Toast.LENGTH_SHORT)
                    .show();

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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }


}
