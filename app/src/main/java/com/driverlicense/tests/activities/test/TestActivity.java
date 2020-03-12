package com.driverlicense.tests.activities.test;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
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
    Random rand = new Random();
    private int questionNumber = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initViews();
        setListeners();
        getRandomQuestions();
        loadQuestionAndAnswers();
    }

    public List<Test> getRandomQuestions() {

        List<Test> testList = new ArrayList<>();;
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
        return testList;
    }

        private void loadQuestionAndAnswers() {

        List<Test> testList = getRandomQuestions();

        String question = testList.get(questionNumber).getQuestion();
        String answerA = testList.get(questionNumber).getAnswerA();
        String answerB = testList.get(questionNumber).getAnswerB();
        String answerC = testList.get(questionNumber).getAnswerC();
        String answerD = testList.get(questionNumber).getAnswerD();
        String imageUrl = testList.get(questionNumber).getImageUrl();
        String correctAnswer = testList.get(questionNumber).getCorrectAnswer();

        loadUI(question, answerA, answerB, answerC,answerD,imageUrl);

        questionNumber++;
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

    }

    private void hideQuestionImageUrl() {

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

    @Override
    public void onClick(View view) {

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
