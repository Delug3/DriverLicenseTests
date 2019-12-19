package com.newagemedia.rtoexam.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.newagemedia.rtoexam.R;
import com.newagemedia.rtoexam.models.Levels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    final List<Levels> dataQuiz = new ArrayList<>();
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

        textViewAnswerOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textViewAnswerTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer_number="2";

                if (answer_number.equals(correctAnswer)) {

                }
                else{

                }
            }
        });

        textViewAnswerThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textViewAnswerFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        cardViewNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextQuestion();
            }
        });


    }

    //each time we call this method, will load the next question & answers
    private void loadNextQuestion() {

        /** Values are placed in Json Array.
         * Json Objects inside Json Array
         * Need to retrieve object position from "quiz" json array via getJSONArray.
         */
        List<String> quiz = getIntent().getStringArrayListExtra("LEVEL_QUIZ");
         try {
        JSONArray jsonArray= new JSONArray(quiz);

                  Levels levels = new Levels();

                  JSONObject json_data = jsonArray.getJSONObject(questionNumber);
                  /*
                  levels.question = json_data.getString("question");
                  levels.answer_one = json_data.getString("answer_one");
                  levels.answer_two = json_data.getString("answer_two");
                  levels.answer_three = json_data.getString("answer_three");
                  levels.answer_four = json_data.getString("answer_four");
                  levels.correctAnswer = json_data.getString("correctAnswer");
                  */

                  String question = json_data.getString("question");
                  String answerOne = json_data.getString("answer_one");
                  String answerTwo = json_data.getString("answer_two");
                  String answerThree = json_data.getString("answer_three");
                  String answerFour = json_data.getString("answer_four");

                  correctAnswer = json_data.getString("correctAnswer");


                  textViewQuestionName.setText(question);
                  textViewAnswerOne.setText(answerOne);
                  textViewAnswerTwo.setText(answerTwo);
                  textViewAnswerThree.setText(answerThree);
                  textViewAnswerFour.setText(answerFour);


        } catch (JSONException e) {
        e.printStackTrace();
        }

         //increasing question number, every time the user click on "Next" will jump to next question
         questionNumber++;
    }






}
