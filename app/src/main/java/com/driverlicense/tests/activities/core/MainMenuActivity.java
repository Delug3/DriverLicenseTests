package com.driverlicense.tests.activities.core;

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
import com.driverlicense.tests.activities.practice.PracticeLevelsListActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Manuel Fernandez Garcia.  Email(mfgarcia87@gmail.com) on 15/12/2019.
 */
public class MainMenuActivity extends AppCompatActivity {

    private String stateQuizNameValue;
    private ConstraintLayout constraintLayoutMainMenuPractice;
    private DrawerLayout drawerLayoutMainMenu;
    //main activity including practice, test, reading and settings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureNavigationDrawer();
        configureToolbar();

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            stateQuizNameValue = extras.getString("STATE_QUIZ_NAME");
        }

        constraintLayoutMainMenuPractice = findViewById(R.id.constraint_layout_main_menu_practice);

        constraintLayoutMainMenuPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, PracticeLevelsListActivity.class);
                i.putExtra("STATE_QUIZ_NAME",stateQuizNameValue);
                startActivity(i);
            }
        });
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
        textViewToolBarTitle.setText("Main Menu");
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
