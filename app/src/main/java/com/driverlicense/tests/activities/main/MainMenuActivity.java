package com.driverlicense.tests.activities.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.driverlicense.tests.R;
import com.driverlicense.tests.activities.core.MyGlobals;
import com.driverlicense.tests.activities.practice.PracticeLevelsListActivity;
import com.driverlicense.tests.activities.reading.ReadingListActivity;
import com.driverlicense.tests.activities.settings.SettingsActivity;
import com.driverlicense.tests.activities.signs.TrafficSignsListActivity;
import com.driverlicense.tests.activities.test.TestActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Manuel Fernandez Garcia.  Email(mfgarcia87@gmail.com) on 15/12/2019.
 */
public class MainMenuActivity extends AppCompatActivity {

    MyGlobals myGlobals;
    private ConstraintLayout constraintLayoutMainMenuPractice, constraintLayoutMainMenuSettings, constraintLayoutMainMenuTrafficSigns, constraintLayoutMainMenuTest, constraintLayoutMainMenuReading;
    private DrawerLayout drawerLayoutMainMenu;
    private AdView mAdView;
    //main activity including practice, test, reading and settings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkInternetConnection();
        configureNavigationDrawer();
        configureToolbar();
        loadAds();
        initViews();


        constraintLayoutMainMenuPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, PracticeLevelsListActivity.class);
                startActivity(i);
            }
        });


        constraintLayoutMainMenuSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, SettingsActivity.class);
                startActivity(i);
            }
        });

        constraintLayoutMainMenuTrafficSigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, TrafficSignsListActivity.class);
                startActivity(i);
            }
        });

        constraintLayoutMainMenuTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, TestActivity.class);
                startActivity(i);
            }
        });

        constraintLayoutMainMenuReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, ReadingListActivity.class);
                startActivity(i);
            }
        });

    }

    private void checkInternetConnection() {
        myGlobals = new MyGlobals(MainMenuActivity.this);
        myGlobals.checkConnectivityChanges();

    }

    private void initViews() {
        constraintLayoutMainMenuPractice = findViewById(R.id.constraint_layout_main_menu_practice);
        constraintLayoutMainMenuSettings = findViewById(R.id.constraint_layout_main_menu_settings);
        constraintLayoutMainMenuTrafficSigns = findViewById(R.id.constraint_layout_main_menu_traffic_signs);
        constraintLayoutMainMenuTest = findViewById(R.id.constraint_layout_main_menu_test);
        constraintLayoutMainMenuReading = findViewById(R.id.constraint_layout_main_menu_reading);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.empty, menu);
        return true;
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        TextView textViewToolBarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null) {
            actionbar.setHomeAsUpIndicator(R.drawable.ic_lateral_menu_white);
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowTitleEnabled(false);

        }
        textViewToolBarTitle.setText(R.string.main_menu);
    }

    private void configureNavigationDrawer() {
        drawerLayoutMainMenu = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.navigation_main_menu);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int itemId = menuItem.getItemId();
                if (itemId == R.id.nav_state_selection) {
                    Snackbar snackBar = Snackbar.make(drawerLayoutMainMenu,"First Option", Snackbar.LENGTH_LONG);
                    snackBar.getView().setBackgroundColor(ContextCompat.getColor(MainMenuActivity.this, R.color.blue));
                    snackBar.show();
                } else if (itemId == R.id.nav_language_selection) {
                    Snackbar snackBar = Snackbar.make(drawerLayoutMainMenu,"Second Option", Snackbar.LENGTH_LONG);
                    snackBar.getView().setBackgroundColor(ContextCompat.getColor(MainMenuActivity.this, R.color.blue));
                    snackBar.show();
                } else if (itemId == R.id.nav_share) {
                    Snackbar snackBar = Snackbar.make(drawerLayoutMainMenu,"Third Option", Snackbar.LENGTH_LONG);
                    snackBar.getView().setBackgroundColor(ContextCompat.getColor(MainMenuActivity.this, R.color.blue));
                    snackBar.show();
                }

                return false;
            }
        });
    }

    private void loadAds()
    {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adview_main_menu);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        case android.R.id.home:

            drawerLayoutMainMenu.openDrawer(GravityCompat.START);
            return true;

        default:
            return super.onOptionsItemSelected(item);
    }

    }
}
