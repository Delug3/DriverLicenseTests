package com.driverlicense.tests.activities.practice;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.driverlicense.tests.R;

public class PracticeQuizResultsActivity extends AppCompatActivity {

    private TextView textViewTotalNumberCorrectAnswers;
    private TextView textViewTotalNumberIncorrectAnswers;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_results);

        textViewTotalNumberCorrectAnswers = findViewById(R.id.text_view_result_correct_answers);
        textViewTotalNumberIncorrectAnswers = findViewById(R.id.text_view_results_incorrect_answers);

        int totalCorrectAnswers, totalIncorrectAnswers;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                totalCorrectAnswers = extras.getInt("TOTAL_CORRECT_ANSWERS");
                totalIncorrectAnswers = extras.getInt("TOTAL_INCORRECT_ANSWERS");

                textViewTotalNumberCorrectAnswers.setText(String.valueOf(totalCorrectAnswers));
                textViewTotalNumberIncorrectAnswers.setText(String.valueOf(totalIncorrectAnswers));
            }
        }
    }
}
