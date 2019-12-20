package com.newagemedia.rtoexam.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
        textViewAnswerOne = findViewById(R.id.text_view_quiz_answer_1);
        textViewAnswerTwo = findViewById(R.id.text_view_quiz_answer_2);
        textViewAnswerThree = findViewById(R.id.text_view_quiz_answer_3);
        textViewAnswerFour = findViewById(R.id.text_view_quiz_answer_4);

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

        loadNextQuestion();

        //compare correct answer with answer number, if its the same, then green, not the same, red and method call
        //green:#FF00C853
        //red:#FFD50000
        textViewAnswerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("one")) {
                    textViewAnswerOne.setTextColor(Color.parseColor("#FF00C853"));
                }
                else{
                    textViewAnswerOne.setTextColor(Color.parseColor("#FFD50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("two")) {
                    textViewAnswerTwo.setTextColor(Color.parseColor("#FF00C853"));
                }
                else{
                    textViewAnswerTwo.setTextColor(Color.parseColor("#FFD50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("three")) {
                    textViewAnswerThree.setTextColor(Color.parseColor("#FF00C853"));
                }
                else{
                    textViewAnswerThree.setTextColor(Color.parseColor("#FFD50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("four")) {
                    textViewAnswerFour.setTextColor(Color.parseColor("#FF00C853"));
                }
                else{
                    textViewAnswerFour.setTextColor(Color.parseColor("#FFD50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });


        cardViewNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextQuestion();
                enableSingleClick();
            }
        });


    }

    /** Values are placed in Json Array.
     * Json Objects inside Json Array
     * Need to retrieve object position from "quiz" json array via getJSONArray.
     * This method is the responsible for loading new questions in the activity
     */
    private void loadNextQuestion() {

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


    //draw green the background and the answer icon to show the correct answer
    private void showCorrectAnswer() {
        switch (correctAnswer) {
            case "one":
                textViewAnswerOne.setTextColor(Color.parseColor("#FF00C853"));
                break;
            case "two":
                textViewAnswerTwo.setTextColor(Color.parseColor("#FF00C853"));
                break;
            case "three":
                textViewAnswerThree.setTextColor(Color.parseColor("#FF00C853"));
                break;
            case "four":
                textViewAnswerFour.setTextColor(Color.parseColor("#FF00C853"));
                break;
        }
    }

    //loading default colors of both, background and icon number
    private void loadDefaultColors(){

        //set default colors every time user press Next
        textViewAnswerOne.setTextColor(Color.BLACK);
        textViewAnswerTwo.setTextColor(Color.BLACK);
        textViewAnswerThree.setTextColor(Color.BLACK);
        textViewAnswerFour.setTextColor(Color.BLACK);



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
