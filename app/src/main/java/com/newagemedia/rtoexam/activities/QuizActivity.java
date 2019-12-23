package com.newagemedia.rtoexam.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.newagemedia.rtoexam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestionName;
    private TextView textViewAnswerOne;
    private TextView textViewAnswerTwo;
    private TextView textViewAnswerThree;
    private TextView textViewAnswerFour;
    private ConstraintLayout constraintLayoutAnswerOne;
    private ConstraintLayout constraintLayoutAnswerTwo;
    private ConstraintLayout constraintLayoutAnswerThree;
    private ConstraintLayout constraintLayoutAnswerFour;
    private ProgressBar progressBarQuiz;
    private Drawable OriginalBackgroundColor;
    private String correctAnswer;
    private int progressStatus = 0;
    //variable to move to the next question
    private int questionNumber = 0;
    //variable to know total number of questions, to set progressbar max value
    private List<String> quizData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        TextView textViewLevelName = findViewById(R.id.text_view_quiz_level_name);
        ConstraintLayout constraintLayoutNextQuestion = findViewById(R.id.constraint_layout_quiz_next_question);
        textViewQuestionName = findViewById(R.id.text_view_quiz_question);
        textViewAnswerOne = findViewById(R.id.text_view_quiz_answer_one);
        textViewAnswerTwo = findViewById(R.id.text_view_quiz_answer_two);
        textViewAnswerThree = findViewById(R.id.text_view_quiz_answer_three);
        textViewAnswerFour = findViewById(R.id.text_view_quiz_answer_four);
        constraintLayoutAnswerOne = findViewById(R.id.constraint_layout_quiz_answer_one);
        constraintLayoutAnswerTwo = findViewById(R.id.constraint_layout_quiz_answer_two);
        constraintLayoutAnswerThree = findViewById(R.id.constraint_layout_quiz_answer_three);
        constraintLayoutAnswerFour = findViewById(R.id.constraint_layout_quiz_answer_four);
        progressBarQuiz = findViewById(R.id.progress_bar_quiz);

        String levelName;
       // List<String> quizData = null;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                levelName = null;
                //quizData = null;

            } else {
                levelName = extras.getString("LEVEL_NAME");
                quizData = getIntent().getStringArrayListExtra("LEVEL_QUIZ");
                textViewLevelName.setText(levelName);

            }
        }

        OriginalBackgroundColor = textViewQuestionName.getBackground();
        //set max value of progress bar depending on quizData size
        progressBarQuiz.setMax(quizData.size());

        loadQuestionAndAnswers();
        updateQuizProgressBar();

        //compare correct answer with answer number, if its the same, then green, not the same, red and method call
        //green:#FF00C853
        //red:#FFD50000
        textViewAnswerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("one")) {
                    constraintLayoutAnswerOne.setBackgroundColor(Color.parseColor("#1D00C853"));
                }
                else{
                    constraintLayoutAnswerOne.setBackgroundColor(Color.parseColor("#23D50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("two")) {
                    constraintLayoutAnswerTwo.setBackgroundColor(Color.parseColor("#1D00C853"));
                }
                else{
                    constraintLayoutAnswerTwo.setBackgroundColor(Color.parseColor("#23D50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("three")) {
                    constraintLayoutAnswerThree.setBackgroundColor(Color.parseColor("#1D00C853"));
                }
                else{
                    constraintLayoutAnswerThree.setBackgroundColor(Color.parseColor("#23D50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("four")) {
                    constraintLayoutAnswerFour.setBackgroundColor(Color.parseColor("#1D00C853"));
                }
                else{
                    constraintLayoutAnswerFour.setBackgroundColor(Color.parseColor("#23D50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });


        constraintLayoutNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuestionAndAnswers();
                updateQuizProgressBar();
                enableSingleClick();

            }
        });


    }

    /** Values are placed in Json Array.
     * Json Objects inside Json Array
     * Need to retrieve object position from "quizData" json array via getJSONArray.
     * This method is the responsible for loading new questions in the activity
     */
    private void loadQuestionAndAnswers() {

        try {
        JSONArray jsonArray= new JSONArray(quizData);
        JSONObject json_data = jsonArray.getJSONObject(questionNumber);

        String question = json_data.getString("question");
        String answerOne = json_data.getString("answer_one");
        String answerTwo = json_data.getString("answer_two");
        String answerThree = json_data.getString("answer_three");
        String answerFour = json_data.getString("answer_four");

        //making this value global in this activity for being use in others methods(showcorrectAnswer)
        correctAnswer = json_data.getString("correct_answer");

        loadUI(question, answerOne, answerTwo, answerThree, answerFour);
        loadDefaultColors();

        } catch (JSONException e) {
        e.printStackTrace();
        }
         //increasing question number, every time the user click on "Next" will jump to next question
         questionNumber++;

    }

    //load views with data from the json array
    private void loadUI(String question, String answerOne, String answerTwo, String answerThree, String answerFour){

        textViewQuestionName.setText(question);
        textViewAnswerOne.setText(answerOne);
        textViewAnswerTwo.setText(answerTwo);
        textViewAnswerThree.setText(answerThree);
        textViewAnswerFour.setText(answerFour);
    }


    //draw green the constraintLayout background and the answer icon to show the correct answer
    private void showCorrectAnswer() {
        switch (correctAnswer) {
            case "one":
                constraintLayoutAnswerOne.setBackgroundColor(Color.parseColor("#1D00C853"));
                break;
            case "two":
                constraintLayoutAnswerTwo.setBackgroundColor(Color.parseColor("#1D00C853"));
                break;
            case "three":
                constraintLayoutAnswerThree.setBackgroundColor(Color.parseColor("#1D00C853"));
                break;
            case "four":
                constraintLayoutAnswerFour.setBackgroundColor(Color.parseColor("#1D00C853"));
                break;
        }
    }

    //method called in order to update progressBar every time a new question is loaded
   private void updateQuizProgressBar()
   {

       if(progressStatus<quizData.size()) {
           progressStatus++;
           progressBarQuiz.setProgress(progressStatus);
       }
       else{
           Toast.makeText(QuizActivity.this,"That was the last question", Toast.LENGTH_LONG).show();
       }
   }

    //loading default colors of both, constraintLayout background and icon number
    private void loadDefaultColors(){

        //set default colors every time user press Next
        constraintLayoutAnswerOne.setBackground(OriginalBackgroundColor);
        constraintLayoutAnswerTwo.setBackground(OriginalBackgroundColor);
        constraintLayoutAnswerThree.setBackground(OriginalBackgroundColor);
        constraintLayoutAnswerFour.setBackground(OriginalBackgroundColor);

    }

    //disabling click event of answers after choosing one, prevents multiple clicks
    private void disableMultipleClicks(){

        textViewAnswerOne.setClickable(false);
        textViewAnswerTwo.setClickable(false);
        textViewAnswerThree.setClickable(false);
        textViewAnswerFour.setClickable(false);
    }

    //enabling click events of answers after pressing in Next
    private void enableSingleClick(){

        textViewAnswerOne.setClickable(true);
        textViewAnswerTwo.setClickable(true);
        textViewAnswerThree.setClickable(true);
        textViewAnswerFour.setClickable(true);
    }


}
