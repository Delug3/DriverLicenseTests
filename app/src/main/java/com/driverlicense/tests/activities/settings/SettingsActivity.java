package com.driverlicense.tests.activities.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.driverlicense.tests.R;
import com.driverlicense.tests.activities.core.MainMenuActivity;
import com.driverlicense.tests.activities.states.StatesListActivity;

public class SettingsActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayoutSettingsStateSelection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        configureToolbar();

        constraintLayoutSettingsStateSelection = findViewById(R.id.constraint_layout_settings_state_selection);

        constraintLayoutSettingsStateSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, StatesListActivity.class);
                startActivity(i);
                finish();
            }
        });

    }




    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        TextView textViewToolBarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowTitleEnabled(false);

        }
        textViewToolBarTitle.setText("Settings");
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
