package com.driverlicense.tests.activities.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.driverlicense.tests.R;

public class TestActivity extends AppCompatActivity {
    //  Random rand = new Random();
   // int randomIndex = rand.nextInt(result.size());
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }
}
