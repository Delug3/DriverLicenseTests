package com.driverlicense.tests.activities.practice;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.driverlicense.tests.R;

public class PracticeQuizResultsActivity extends AppCompatActivity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        int totalCorrectAnswers, totalIncorrectAnswers;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                totalCorrectAnswers = extras.getInt("TOTAL_CORRECT_ANSWERS");
                totalIncorrectAnswers = extras.getInt("TOTAL_INCORRECT_ANSWERS");


            }
        }
    }
}
