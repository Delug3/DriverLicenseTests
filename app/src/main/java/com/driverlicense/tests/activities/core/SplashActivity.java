package com.driverlicense.tests.activities.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.driverlicense.tests.R;
import com.driverlicense.tests.activities.states.StatesListActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent i = new Intent(SplashActivity.this, StatesListActivity.class); startActivity(i);
                finish();
            }
            }
            , 2000);
    }

    }




