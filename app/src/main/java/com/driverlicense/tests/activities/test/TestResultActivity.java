package com.driverlicense.tests.activities.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.driverlicense.tests.R;
import com.driverlicense.tests.activities.share.ShareActivity;
import com.driverlicense.tests.activities.sheet.AnswerSheetActivity;
import com.driverlicense.tests.models.SavedAnswers;

import java.util.ArrayList;
import java.util.List;

public class TestResultActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewTestResult, textViewTestScore, textViewTestPercentage, textViewTestTry, textViewTestAnswerSheet, textViewTestShare ;
    private ImageView imageViewTestPercentage, imageViewTestTry, imageViewTestAnswerSheet, imageViewTestShare;
    private int totalNumberCorrectAnswers = 0;
    private List<SavedAnswers> savedAnswersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);

        initViews();
        setListeners();

        Integer totalNumberIncorrectAnswers, totalQuestions;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                totalNumberIncorrectAnswers = null;
                totalQuestions = null;
            } else {
                totalNumberCorrectAnswers = extras.getInt("CORRECT_ANSWERS");
                totalNumberIncorrectAnswers = extras.getInt("INCORRECT_ANSWERS");
                totalQuestions = extras.getInt("TOTAL_QUESTIONS");
                savedAnswersList = extras.getParcelableArrayList("SAVED_ANSWERS");

                setContent();
            }
        }
    }

    private void initViews() {
        textViewTestPercentage = findViewById(R.id.text_view_test_percentage);
        textViewTestResult = findViewById(R.id.text_view_test_result);
        textViewTestScore = findViewById(R.id.text_view_test_score);
        textViewTestTry = findViewById(R.id.text_view_test_try);
        imageViewTestPercentage = findViewById(R.id.image_view_test_percentage);
        imageViewTestTry = findViewById(R.id.image_view_test_try);
        imageViewTestAnswerSheet = findViewById(R.id.image_view_test_answer_sheet);
        imageViewTestShare = findViewById(R.id.image_view_test_share);
        textViewTestAnswerSheet = findViewById(R.id.text_view_test_sheet);
        textViewTestShare = findViewById(R.id.text_view_test_share);
    }

    private void setListeners(){
        imageViewTestTry.setOnClickListener(this);
        textViewTestTry.setOnClickListener(this);
        imageViewTestAnswerSheet.setOnClickListener(this);
        textViewTestAnswerSheet.setOnClickListener(this);
        imageViewTestShare.setOnClickListener(this);
        textViewTestShare.setOnClickListener(this);
    }

    private int getScorePercentage() {
        //total questions = 20
        //20 correct answers = 100% so 1 is 5%
        totalNumberCorrectAnswers = totalNumberCorrectAnswers * 100;
        return totalNumberCorrectAnswers;
    }

    private void setContent() {
        int score = getScorePercentage();
        String scorePercentage = score +"%";
        textViewTestPercentage.setText(scorePercentage);

        //score = 100, test completed
        if(score == 100)
        {
            textViewTestResult.setText(R.string.test_passed);
            textViewTestScore.setText(R.string.test_score);
            textViewTestScore.setTextColor(ContextCompat.getColor(this, R.color.green));
            imageViewTestPercentage.setImageResource(R.drawable.ic_green_percentage);
            textViewTestTry.setText(R.string.do_new_test);
            imageViewTestTry.setImageResource(R.drawable.ic_green_try);
        }
        else
        {
            textViewTestResult.setText(R.string.test_not_passed);
            textViewTestScore.setText(R.string.test_score);
            textViewTestScore.setTextColor(ContextCompat.getColor(this, R.color.red));
            imageViewTestPercentage.setImageResource(R.drawable.ic_red_percentage);
            textViewTestTry.setText(R.string.try_again);
            imageViewTestTry.setImageResource(R.drawable.ic_red_try);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_test_try:
            case R.id.image_view_test_try:
                Intent intentTest = new Intent(TestResultActivity.this, TestActivity.class);
                startActivity(intentTest);
                finish();
                break;

            case R.id.text_view_test_sheet:
            case R.id.image_view_test_answer_sheet:
                Intent intentAnswerSheet = new Intent(TestResultActivity.this, AnswerSheetActivity.class);
                intentAnswerSheet.putParcelableArrayListExtra("SAVED_ANSWERS", (ArrayList<? extends Parcelable>) savedAnswersList);
                startActivity(intentAnswerSheet);
                finish();
                break;

            case R.id.text_view_test_share:
            case R.id.image_view_test_share:
                Intent intentShare = new Intent(TestResultActivity.this, ShareActivity.class);
                startActivity(intentShare);
                break;

            default:
                break;
        }
    }
}