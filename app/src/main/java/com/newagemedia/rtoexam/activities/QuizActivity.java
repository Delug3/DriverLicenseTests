package com.newagemedia.rtoexam.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.newagemedia.rtoexam.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class QuizActivity extends AppCompatActivity {


    private TextView textViewLevelName;
    private CardView cardViewNextQuestion;
    private TextView textViewQuestionName;
    private TextView textViewAnswerOne;
    private TextView textViewAnswerTwo;
    private TextView textViewAnswerThree;
    private TextView textViewAnswerFour;
    private ConstraintLayout constraintLayoutAnswerOne;
    private ConstraintLayout constraintLayoutAnswerTwo;
    private ConstraintLayout constraintLayoutAnswerThree;
    private ConstraintLayout constraintLayoutAnswerFour;
    private Drawable OriginalBackgroundColor;
    private String correctAnswer;
    //variable to move to the next question
    private int questionNumber = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        textViewLevelName = findViewById(R.id.text_view_quiz_level_name);
        cardViewNextQuestion = findViewById(R.id.card_view_quiz_next_question);
        textViewQuestionName = findViewById(R.id.text_view_quiz_question);
        textViewAnswerOne = findViewById(R.id.text_view_quiz_answer_one);
        textViewAnswerTwo = findViewById(R.id.text_view_quiz_answer_two);
        textViewAnswerThree = findViewById(R.id.text_view_quiz_answer_three);
        textViewAnswerFour = findViewById(R.id.text_view_quiz_answer_four);
        constraintLayoutAnswerOne = findViewById(R.id.constraint_layout_quiz_answer_one);
        constraintLayoutAnswerTwo = findViewById(R.id.constraint_layout_quiz_answer_two);
        constraintLayoutAnswerThree = findViewById(R.id.constraint_layout_quiz_answer_three);
        constraintLayoutAnswerFour = findViewById(R.id.constraint_layout_quiz_answer_four);

        OriginalBackgroundColor = textViewQuestionName.getBackground();

        String levelName;
       // List<String> quiz = null;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                levelName = null;
                //quiz = null;

            } else {
                levelName = extras.getString("LEVEL_NAME");
                //quiz = getIntent().getStringArrayListExtra("LEVEL_QUIZ");
                textViewLevelName.setText(levelName);
            }
        }
        obtainTotalNumberOfQuestions();
        loadQuestionAndAnswers();

        //compare correct answer with answer number, if its the same, then green, not the same, red and method call
        //green:#FF00C853
        //red:#FFD50000
        textViewAnswerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("one")) {
                    constraintLayoutAnswerOne.setBackgroundColor(Color.parseColor("#FF00C853"));
                }
                else{
                    constraintLayoutAnswerOne.setBackgroundColor(Color.parseColor("#FFD50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("two")) {
                    constraintLayoutAnswerTwo.setBackgroundColor(Color.parseColor("#FF00C853"));
                }
                else{
                    constraintLayoutAnswerTwo.setBackgroundColor(Color.parseColor("#FFD50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("three")) {
                    constraintLayoutAnswerThree.setBackgroundColor(Color.parseColor("#FF00C853"));
                }
                else{
                    constraintLayoutAnswerThree.setBackgroundColor(Color.parseColor("#FFD50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("four")) {
                    constraintLayoutAnswerFour.setBackgroundColor(Color.parseColor("#FF00C853"));
                }
                else{
                    constraintLayoutAnswerFour.setBackgroundColor(Color.parseColor("#FFD50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });


        cardViewNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadQuestionAndAnswers();
                enableSingleClick();

            }
        });


    }


    /** Values are placed in Json Array.
     * Json Objects inside Json Array
     * Need to retrieve object position from "quiz" json array via getJSONArray.
     * This method is the responsible for loading new questions in the activity
     */
    private void loadQuestionAndAnswers() {

        List<String> quiz = getIntent().getStringArrayListExtra("LEVEL_QUIZ");
        try {
        JSONArray jsonArray= new JSONArray(quiz);

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
                constraintLayoutAnswerOne.setBackgroundColor(Color.parseColor("#FF00C853"));
                break;
            case "two":
                constraintLayoutAnswerTwo.setBackgroundColor(Color.parseColor("#FF00C853"));
                break;
            case "three":
                constraintLayoutAnswerThree.setBackgroundColor(Color.parseColor("#FF00C853"));
                break;
            case "four":
                constraintLayoutAnswerFour.setBackgroundColor(Color.parseColor("#FF00C853"));
                break;
        }
    }

    //obtain total number of question for increasing loading bar
    private int obtainTotalNumberOfQuestions(){
        int totalNumberOfQuestions=2;
        return totalNumberOfQuestions;
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
