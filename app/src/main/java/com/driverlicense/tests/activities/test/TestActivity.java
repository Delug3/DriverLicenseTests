package com.driverlicense.tests.activities.test;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.driverlicense.tests.R;
import com.driverlicense.tests.models.Test;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    private TextView textViewQuestion, textViewAnswerA, textViewAnswerB, textViewAnswerC, textViewAnswerD , textViewQuestionNumber;
    private ImageView imageViewQuestionImageUrl, imageViewNextQuestion, imageViewLetterA, imageViewLetterB , imageViewLetterC, imageViewLetterD;
    private ConstraintLayout constraintLayoutMain, constraintLayoutAnswerA, constraintLayoutAnswerB, constraintLayoutAnswerC, constraintLayoutAnswerD;
    private ProgressBar progressBarQuiz;
    private String correctAnswer;
    private Random rand = new Random();
    private List<Test> testList = new ArrayList<>();
    private int questionNumber = 0;
    private int progressStatus = 0;
    private boolean allQuestionsCompleted = false;
    private boolean shouldRepeatAnimation = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initViews();
        setListeners();
        getRandomQuestions();
        setProgressBarSize();
        hideNextQuestionView();
        loadQuestionAndAnswers();
        updateQuizProgressBar();
        showQuestionNumber();
    }



    public void getRandomQuestions() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Test_EN");
        query.setLimit(200);
        try {
            List<ParseObject> result = query.find();
            for (int i = 0; i < 5; i++) {

                int randomIndex = rand.nextInt(result.size());

                Test test = new Test();
                test.question = result.get(randomIndex).getString("question");
                test.answerA = result.get(randomIndex).getString("answer_a");
                test.answerB = result.get(randomIndex).getString("answer_b");
                test.answerC = result.get(randomIndex).getString("answer_c");
                test.answerD = result.get(randomIndex).getString("answer_d");
                test.imageUrl = result.get(randomIndex).getString("image_url");
                test.correctAnswer = result.get(randomIndex).getString("correct_answer");

                testList.add(test);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void loadQuestionAndAnswers() {

        String question = testList.get(questionNumber).getQuestion();
        String answerA = testList.get(questionNumber).getAnswerA();
        String answerB = testList.get(questionNumber).getAnswerB();
        String answerC = testList.get(questionNumber).getAnswerC();
        String answerD = testList.get(questionNumber).getAnswerD();
        String imageUrl = testList.get(questionNumber).getImageUrl();
        correctAnswer = testList.get(questionNumber).getCorrectAnswer();

        checkValueAnswerD(answerD);
        loadUI(question, answerA, answerB, answerC,answerD,imageUrl);
        disableNextQuestionViewClick();

        questionNumber++;
    }

    private void checkValueAnswerD(String answerD) {
        if (answerD.equals("null"))
        {
            hideAnswer();
        }
        else
        {
            unHideAnswer();
        }
    }

    private void unHideAnswer() {
        constraintLayoutAnswerD.setVisibility(View.VISIBLE);
        imageViewLetterD.setVisibility(View.VISIBLE);
        textViewAnswerD.setVisibility(View.VISIBLE);
    }

    private void hideAnswer() {
        constraintLayoutAnswerD.setVisibility(View.GONE);
        imageViewLetterD.setVisibility(View.GONE);
        textViewAnswerD.setVisibility(View.GONE);
    }


    private void setProgressBarSize() {
        progressBarQuiz.setMax(testList.size());
    }

    private void hideNextQuestionView()
    {
        imageViewNextQuestion.setVisibility(View.GONE);
    }




    private void loadUI(String question, String answerA, String answerB, String answerC, String answerD, String imageUrl) {

        textViewQuestion.setText(question);
        textViewAnswerA.setText(answerA);
        textViewAnswerB.setText(answerB);
        textViewAnswerC.setText(answerC);
        textViewAnswerD.setText(answerD);

        if (imageUrl.equals("null"))
        {
            hideQuestionImageUrl();
        }
        else
        {
            unHideQuestionImageUrl();
            Picasso.get().load(imageUrl).into(imageViewQuestionImageUrl);
        }

    }

    private void unHideQuestionImageUrl() {
        imageViewQuestionImageUrl.setVisibility(View.VISIBLE);
    }

    private void hideQuestionImageUrl() {
        imageViewQuestionImageUrl.setVisibility(View.GONE);
    }


    private void updateQuizProgressBar()
    {
        if(progressStatus < testList.size()) {
            progressStatus++;
            progressBarQuiz.setProgress(progressStatus);
        }
        else{
            disableMultipleClicks();
            allQuestionsCompleted = true;
            testPassed();
            showTestResults();
        }

    }

    private void showTestResults() {
    }

    private void testPassed() {
    }


    private void disableMultipleClicks(){
        imageViewLetterA.setClickable(false);
        imageViewLetterB.setClickable(false);
        imageViewLetterC.setClickable(false);
        imageViewLetterD.setClickable(false);
        textViewAnswerA.setClickable(false);
        textViewAnswerB.setClickable(false);
        textViewAnswerC.setClickable(false);
        textViewAnswerD.setClickable(false);
    }

    private void showQuestionNumber()
    {
        String actualQuestionNumberAndTotals = progressStatus + "/" + testList.size();
        textViewQuestionNumber.setText(actualQuestionNumberAndTotals);
    }

    private void initViews() {
        constraintLayoutMain = findViewById(R.id.constraint_layout_test_main);
        textViewQuestion = findViewById(R.id.text_view_test_question);
        textViewQuestionNumber = findViewById(R.id.text_view_test_question_number);
        textViewAnswerA = findViewById(R.id.text_view_test_answer_a);
        textViewAnswerB = findViewById(R.id.text_view_test_answer_b);
        textViewAnswerC = findViewById(R.id.text_view_test_answer_c);
        textViewAnswerD = findViewById(R.id.text_view_test_answer_d);
        imageViewQuestionImageUrl = findViewById(R.id.image_view_test_image_url);
        imageViewNextQuestion = findViewById(R.id.image_view_test_next_question);
        imageViewLetterA = findViewById(R.id.image_view_test_letter_a);
        imageViewLetterB = findViewById(R.id.image_view_test_letter_b);
        imageViewLetterC = findViewById(R.id.image_view_test_letter_c);
        imageViewLetterD = findViewById(R.id.image_view_test_letter_d);
        constraintLayoutAnswerA = findViewById(R.id.constraint_layout_test_answer_a);
        constraintLayoutAnswerB = findViewById(R.id.constraint_layout_test_answer_b);
        constraintLayoutAnswerC = findViewById(R.id.constraint_layout_test_answer_c);
        constraintLayoutAnswerD = findViewById(R.id.constraint_layout_test_answer_d);
        progressBarQuiz = findViewById(R.id.progress_bar_test);
    }

    private void setListeners() {
        imageViewLetterA.setOnClickListener(this);
        imageViewLetterB.setOnClickListener(this);
        imageViewLetterC.setOnClickListener(this);
        imageViewLetterD.setOnClickListener(this);
        textViewAnswerA.setOnClickListener(this);
        textViewAnswerB.setOnClickListener(this);
        textViewAnswerC.setOnClickListener(this);
        textViewAnswerD.setOnClickListener(this);
        imageViewNextQuestion.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.image_view_test_letter_a:

            case R.id.text_view_test_answer_a:

                if (correctAnswer.equals("A")) {

                    //totalNumberCorrectAnswers++;
                }
                else{

                    //totalNumberIncorrectAnswers++;
                    //showCorrectAnswer();
                }
                disableMultipleClicks();
                unHideNextQuestionView();
                enableNextQuestionViewClick();
                startAnimationNextQuestion();
                break;


            case R.id.image_view_test_letter_b:

            case R.id.text_view_test_answer_b:

                if (correctAnswer.equals("B")) {

                }
                else{

                }
                disableMultipleClicks();
                unHideNextQuestionView();
                enableNextQuestionViewClick();
                startAnimationNextQuestion();
                break;


            case R.id.image_view_test_letter_c:

            case R.id.text_view_test_answer_c:

                if (correctAnswer.equals("C")) {

                }
                else{

                }
                disableMultipleClicks();
                unHideNextQuestionView();
                enableNextQuestionViewClick();
                startAnimationNextQuestion();
                break;


            case R.id.image_view_test_letter_d:

            case R.id.text_view_test_answer_d:

                if (correctAnswer.equals("D")) {

                }
                else{

                }

                disableMultipleClicks();
                unHideNextQuestionView();
                enableNextQuestionViewClick();
                startAnimationNextQuestion();
                break;


            case R.id.image_view_test_next_question:

                enableSingleClick();
                loadQuestionAndAnswers();
                updateQuizProgressBar();
                stopAnimationNextQuestion();
                showQuestionNumber();
                break;


            default:
                break;
        }

    }

    private void unHideNextQuestionView()
    {
        imageViewNextQuestion.setVisibility(View.VISIBLE);
    }

    private void enableNextQuestionViewClick()
    {
        imageViewNextQuestion.setClickable(true);
    }

    private void disableNextQuestionViewClick()
    {
        imageViewNextQuestion.setClickable(false);
    }

    private void startAnimationNextQuestion()
    {
        if(progressStatus < testList.size())
        {
            imageViewNextQuestion.setImageResource(R.drawable.ic_next_quiz_question);
        }

        else
        {
            imageViewNextQuestion.setImageResource(R.drawable.ic_results);
        }

        shouldRepeatAnimation = true;
        AlphaAnimation fadeIn=new AlphaAnimation(0,1);
        AlphaAnimation fadeOut=new AlphaAnimation(1,0);

        final AnimationSet set = new AnimationSet(false);

        set.addAnimation(fadeIn);
        set.addAnimation(fadeOut);

        fadeOut.setStartOffset(1000);
        set.setDuration(2000);
        imageViewNextQuestion.startAnimation(set);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }
            @Override
            public void onAnimationRepeat(Animation animation) { }
            @Override
            public void onAnimationEnd(Animation animation) {
                if (shouldRepeatAnimation) {
                    imageViewNextQuestion.startAnimation(set);
                }
            }
        });
    }

    private void enableSingleClick(){
        imageViewLetterA.setClickable(true);
        imageViewLetterB.setClickable(true);
        imageViewLetterC.setClickable(true);
        imageViewLetterD.setClickable(true);
        textViewAnswerA.setClickable(true);
        textViewAnswerB.setClickable(true);
        textViewAnswerC.setClickable(true);
        textViewAnswerD.setClickable(true);
    }


    private void stopAnimationNextQuestion()
    {
        shouldRepeatAnimation = false;
        imageViewNextQuestion.clearAnimation();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
