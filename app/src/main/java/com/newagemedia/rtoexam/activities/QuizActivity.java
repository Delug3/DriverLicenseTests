package com.newagemedia.rtoexam.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.newagemedia.rtoexam.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView textViewQuestion;
    private TextView textViewAnswerA;
    private TextView textViewAnswerB;
    private TextView textViewAnswerC;
    private TextView textViewAnswerD;
    private ImageView imageViewQuestionImageUrl;
    private ConstraintLayout constraintLayoutMain;
    private ConstraintLayout constraintLayoutAnswerA;
    private ConstraintLayout constraintLayoutAnswerB;
    private ConstraintLayout constraintLayoutAnswerC;
    private ConstraintLayout constraintLayoutAnswerD;
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

        constraintLayoutMain = findViewById(R.id.constraint_layout_quiz_main);
        TextView textViewLevelNumber = findViewById(R.id.text_view_quiz_level_number);
        TextView textViewLevelName = findViewById(R.id.text_view_quiz_level_name);
        ConstraintLayout constraintLayoutNextQuestion = findViewById(R.id.constraint_layout_quiz_next_question);
        textViewQuestion = findViewById(R.id.text_view_quiz_question);
        textViewAnswerA = findViewById(R.id.text_view_quiz_answer_a);
        textViewAnswerB = findViewById(R.id.text_view_quiz_answer_b);
        textViewAnswerC = findViewById(R.id.text_view_quiz_answer_c);
        textViewAnswerD = findViewById(R.id.text_view_quiz_answer_d);
        imageViewQuestionImageUrl = findViewById(R.id.image_view_quiz_image_url);
        constraintLayoutAnswerA = findViewById(R.id.constraint_layout_quiz_answer_a);
        constraintLayoutAnswerB = findViewById(R.id.constraint_layout_quiz_answer_b);
        constraintLayoutAnswerC = findViewById(R.id.constraint_layout_quiz_answer_c);
        constraintLayoutAnswerD = findViewById(R.id.constraint_layout_quiz_answer_d);
        progressBarQuiz = findViewById(R.id.progress_bar_quiz);


        String levelName;
        Integer levelNumber;
       // List<String> quizData = null;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                levelName = null;
                levelNumber = null;
                //quizData = null;

            } else {
                levelNumber = extras.getInt("LEVEL_NUMBER");
                levelName = extras.getString("LEVEL_NAME");
                textViewLevelNumber.setText((String.valueOf(levelNumber)));
                textViewLevelName.setText(levelName);
                quizData = getIntent().getStringArrayListExtra("LEVEL_QUIZ");
            }
        }

        OriginalBackgroundColor = textViewQuestion.getBackground();
        //set max value of progress bar depending on quizData size
        progressBarQuiz.setMax(quizData.size());

        loadQuestionAndAnswers();
        updateQuizProgressBar();

        //compare correct answer with answer number, if its the same, then green, not the same, red and method call
        //green:#FF00C853
        //red:#FFD50000
        textViewAnswerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("a")) {
                    constraintLayoutAnswerA.setBackgroundColor(Color.parseColor("#1D00C853"));
                }
                else{
                    constraintLayoutAnswerA.setBackgroundColor(Color.parseColor("#23D50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("b")) {
                    constraintLayoutAnswerB.setBackgroundColor(Color.parseColor("#1D00C853"));
                }
                else{
                    constraintLayoutAnswerB.setBackgroundColor(Color.parseColor("#23D50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("c")) {
                    constraintLayoutAnswerC.setBackgroundColor(Color.parseColor("#1D00C853"));
                }
                else{
                    constraintLayoutAnswerC.setBackgroundColor(Color.parseColor("#23D50000"));
                    showCorrectAnswer();
                }
                disableMultipleClicks();
            }
        });

        textViewAnswerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correctAnswer.equals("d")) {
                    constraintLayoutAnswerD.setBackgroundColor(Color.parseColor("#1D00C853"));
                }
                else{
                    constraintLayoutAnswerD.setBackgroundColor(Color.parseColor("#23D50000"));
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
        String answerA = json_data.getString("answer_a");
        String answerB = json_data.getString("answer_b");
        String answerC = json_data.getString("answer_c");
        String answerD = json_data.getString("answer_d");
        String imageUrl = json_data.getString("image_url");
        //making this value global in this activity for being use in others methods(showcorrectAnswer)
        correctAnswer = json_data.getString("correct_answer");

        loadUI(question, answerA, answerB, answerC,answerD,imageUrl);
        loadDefaultColors();

        } catch (JSONException e) {
        e.printStackTrace();
        }
         //increasing question number, every time the user click on "Next" will jump to next question
         questionNumber++;
    }

    //load views with data from the json array
    private void loadUI(String question, String answerA, String answerB, String answerC, String answerD,String imageUrl){

        textViewQuestion.setText(question);
        Picasso.get().load(imageUrl).into(imageViewQuestionImageUrl);
        textViewAnswerA.setText(answerA);
        textViewAnswerB.setText(answerB);
        textViewAnswerC.setText(answerC);
        //if null dont set, make layout invisible
        textViewAnswerD.setText(answerD);

    }

    //draw green the constraintLayout background and the answer icon to show the correct answer
    private void showCorrectAnswer() {
        switch (correctAnswer) {
            case "a":
                constraintLayoutAnswerA.setBackgroundColor(Color.parseColor("#1D00C853"));
                break;
            case "b":
                constraintLayoutAnswerB.setBackgroundColor(Color.parseColor("#1D00C853"));
                break;
            case "c":
                constraintLayoutAnswerC.setBackgroundColor(Color.parseColor("#1D00C853"));
                break;
            case "d":
                constraintLayoutAnswerD.setBackgroundColor(Color.parseColor("#1D00C853"));
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
           Snackbar snackbar = Snackbar.make(constraintLayoutMain,"All Questions Answered!", Snackbar.LENGTH_LONG);
           snackbar.getView().setBackgroundColor(ContextCompat.getColor(QuizActivity.this, R.color.blue));
           snackbar.show();
       }
   }

    //loading default colors of both, constraintLayout background and icon number
    private void loadDefaultColors(){

        //set default colors every time user press Next
        constraintLayoutAnswerA.setBackground(OriginalBackgroundColor);
        constraintLayoutAnswerB.setBackground(OriginalBackgroundColor);
        constraintLayoutAnswerC.setBackground(OriginalBackgroundColor);
        constraintLayoutAnswerD.setBackground(OriginalBackgroundColor);
    }

    //disabling click event of answers after choosing one, prevents multiple clicks
    private void disableMultipleClicks(){

        textViewAnswerA.setClickable(false);
        textViewAnswerB.setClickable(false);
        textViewAnswerC.setClickable(false);
        textViewAnswerD.setClickable(false);
    }

    //enabling click events of answers after pressing in Next
    private void enableSingleClick(){

        textViewAnswerA.setClickable(true);
        textViewAnswerB.setClickable(true);
        textViewAnswerC.setClickable(true);
        textViewAnswerD.setClickable(true);
    }


}
