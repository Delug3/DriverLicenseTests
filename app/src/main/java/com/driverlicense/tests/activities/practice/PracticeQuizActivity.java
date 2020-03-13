package com.driverlicense.tests.activities.practice;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.driverlicense.tests.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PracticeQuizActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    private TextView textViewQuestion, textViewAnswerA, textViewAnswerB, textViewAnswerC, textViewAnswerD , textViewQuestionNumber;
    private ImageView imageViewQuestionImageUrl, imageViewNextQuestion, imageViewLetterA, imageViewLetterB , imageViewLetterC, imageViewLetterD;
    private ConstraintLayout constraintLayoutMain, constraintLayoutAnswerA, constraintLayoutAnswerB, constraintLayoutAnswerC, constraintLayoutAnswerD;
    private ProgressBar progressBarQuiz;
    private Dialog resultDialog;
    private Drawable OriginalBackgroundColor;
    private String levelId, levelName, queryLanguage, correctAnswer;
    private Integer levelNumber;
    private boolean allQuestionsCompleted = false;
    private int totalNumberCorrectAnswers = 0;
    private int totalNumberIncorrectAnswers = 0;
    private boolean shouldRepeatAnimation = true;
    private int progressStatus = 0;
    //variable to move to the next question
    private int questionNumber = 0;
    //variable to know total number of questions, to set progressbar max value
    private List<String> quizList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initViews();
        setListeners();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                levelId = null;
                levelNumber = null;
                levelName = null;
                queryLanguage = null;
            } else {
                queryLanguage = extras.getString("QUERY_LANGUAGE");
                levelId = extras.getString("LEVEL_ID");
                levelNumber = extras.getInt("LEVEL_NUMBER");
                levelName = extras.getString("LEVEL_NAME");
                quizList = getIntent().getStringArrayListExtra("LEVEL_QUIZ");
            }

            configureToolbar();
        }

        OriginalBackgroundColor = textViewQuestion.getBackground();

        setProgressBarSize();
        hideNextQuestionView();
        loadQuestionAndAnswers();
        updateQuizProgressBar();
        showQuestionNumber();
    }

    private void setProgressBarSize() {
        //set max value of progress bar depending on quizList size
        progressBarQuiz.setMax(quizList.size());
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

    private void initViews() {
        constraintLayoutMain = findViewById(R.id.constraint_layout_quiz_main);
        textViewQuestion = findViewById(R.id.text_view_quiz_question);
        textViewQuestionNumber = findViewById(R.id.text_view_quiz_question_number);
        textViewAnswerA = findViewById(R.id.text_view_quiz_answer_a);
        textViewAnswerB = findViewById(R.id.text_view_quiz_answer_b);
        textViewAnswerC = findViewById(R.id.text_view_quiz_answer_c);
        textViewAnswerD = findViewById(R.id.text_view_quiz_answer_d);
        imageViewQuestionImageUrl = findViewById(R.id.image_view_quiz_image_url);
        imageViewNextQuestion = findViewById(R.id.image_view_quiz_next_question);
        imageViewLetterA = findViewById(R.id.image_view_quiz_letter_a);
        imageViewLetterB = findViewById(R.id.image_view_quiz_letter_b);
        imageViewLetterC = findViewById(R.id.image_view_quiz_letter_c);
        imageViewLetterD = findViewById(R.id.image_view_quiz_letter_d);
        constraintLayoutAnswerA = findViewById(R.id.constraint_layout_quiz_answer_a);
        constraintLayoutAnswerB = findViewById(R.id.constraint_layout_quiz_answer_b);
        constraintLayoutAnswerC = findViewById(R.id.constraint_layout_quiz_answer_c);
        constraintLayoutAnswerD = findViewById(R.id.constraint_layout_quiz_answer_d);
        progressBarQuiz = findViewById(R.id.progress_bar_quiz);
    }

    //compare correct answer with answer number, if its the same, then green, not the same, red and method call
    //green:#FF00C853
    //red:#FFD50000
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.image_view_quiz_letter_a:

            case R.id.text_view_quiz_answer_a:

                if (correctAnswer.equals("A")) {
                    constraintLayoutAnswerA.setBackgroundColor(Color.parseColor("#1D00C853"));
                    imageViewLetterA.setImageResource(R.drawable.ic_green_answer_a);
                    totalNumberCorrectAnswers++;
                }
                else{
                    constraintLayoutAnswerA.setBackgroundColor(Color.parseColor("#23D50000"));
                    imageViewLetterA.setImageResource(R.drawable.ic_red_answer_a);
                    totalNumberIncorrectAnswers++;
                    showCorrectAnswer();
                }
                disableMultipleClicks();
                unHideNextQuestionView();
                enableNextQuestionViewClick();
                startAnimationNextQuestion();
                break;


            case R.id.image_view_quiz_letter_b:

            case R.id.text_view_quiz_answer_b:

                if (correctAnswer.equals("B")) {
                    constraintLayoutAnswerB.setBackgroundColor(Color.parseColor("#1D00C853"));
                    imageViewLetterB.setImageResource(R.drawable.ic_green_answer_b);
                    totalNumberCorrectAnswers++;
                }
                else{
                    constraintLayoutAnswerB.setBackgroundColor(Color.parseColor("#23D50000"));
                    imageViewLetterB.setImageResource(R.drawable.ic_red_answer_b);
                    totalNumberIncorrectAnswers++;
                    showCorrectAnswer();
                }
                disableMultipleClicks();
                unHideNextQuestionView();
                enableNextQuestionViewClick();
                startAnimationNextQuestion();
                break;


            case R.id.image_view_quiz_letter_c:

            case R.id.text_view_quiz_answer_c:

                if (correctAnswer.equals("C")) {
                    constraintLayoutAnswerC.setBackgroundColor(Color.parseColor("#1D00C853"));
                    imageViewLetterC.setImageResource(R.drawable.ic_green_answer_c);
                    totalNumberCorrectAnswers++;
                }
                else{
                    constraintLayoutAnswerC.setBackgroundColor(Color.parseColor("#23D50000"));
                    imageViewLetterC.setImageResource(R.drawable.ic_red_answer_c);
                    totalNumberIncorrectAnswers++;
                    showCorrectAnswer();
                }
                disableMultipleClicks();
                unHideNextQuestionView();
                enableNextQuestionViewClick();
                startAnimationNextQuestion();
                break;


            case R.id.image_view_quiz_letter_d:

            case R.id.text_view_quiz_answer_d:

                if (correctAnswer.equals("D")) {
                    constraintLayoutAnswerD.setBackgroundColor(Color.parseColor("#1D00C853"));
                    imageViewLetterD.setImageResource(R.drawable.ic_green_answer_d);
                    totalNumberCorrectAnswers++;
                }
                else{
                    constraintLayoutAnswerD.setBackgroundColor(Color.parseColor("#23D50000"));
                    imageViewLetterD.setImageResource(R.drawable.ic_red_answer_d);
                    totalNumberIncorrectAnswers++;
                    showCorrectAnswer();
                }
                disableMultipleClicks();
                unHideNextQuestionView();
                enableNextQuestionViewClick();
                startAnimationNextQuestion();
                break;


            case R.id.image_view_quiz_next_question:

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

    /** Values are placed in Json Array.
     * Json Objects inside Json Array
     * Need to retrieve object position from "quizList" json array via getJSONArray.
     * This method is the responsible for loading new questions in the activity
     */
    private void loadQuestionAndAnswers() {

        try {
        JSONArray jsonArray= new JSONArray(quizList);
        JSONObject json_data = jsonArray.getJSONObject(questionNumber);

        String question = json_data.getString("question");
        String answerA = json_data.getString("answer_a");
        String answerB = json_data.getString("answer_b");
        String answerC = json_data.getString("answer_c");
        String answerD = json_data.getString("answer_d");
        String imageUrl = json_data.getString("image_url");

        //making this value global in this activity for being use in others methods(showcorrectAnswer)
        correctAnswer = json_data.getString("correct_answer");

        //method to know if answer D includes a value, if it's null(no answer), then hide the view
        checkValueAnswerD(answerD);
        loadUI(question, answerA, answerB, answerC,answerD,imageUrl);
        loadDefaultColors();

        disableNextQuestionViewClick();

        } catch (JSONException e) {
        e.printStackTrace();
        }
         //increasing question number, every time the user click on "Next" will jump to next question
         questionNumber++;
    }

    //load views with data from the json array
    private void loadUI(String question, String answerA, String answerB, String answerC, String answerD,String imageUrl){

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

    //draw green the constraintLayout background and the answer icon to show the correct answer
    private void showCorrectAnswer() {
        switch (correctAnswer) {
            case "A":
                constraintLayoutAnswerA.setBackgroundColor(Color.parseColor("#1D00C853"));
                imageViewLetterA.setImageResource(R.drawable.ic_green_answer_a);
                break;
            case "B":
                constraintLayoutAnswerB.setBackgroundColor(Color.parseColor("#1D00C853"));
                imageViewLetterB.setImageResource(R.drawable.ic_green_answer_b);
                break;
            case "C":
                constraintLayoutAnswerC.setBackgroundColor(Color.parseColor("#1D00C853"));
                imageViewLetterC.setImageResource(R.drawable.ic_green_answer_c);
                break;
            case "D":
                constraintLayoutAnswerD.setBackgroundColor(Color.parseColor("#1D00C853"));
                imageViewLetterD.setImageResource(R.drawable.ic_green_answer_d);
                break;
        }
    }

    //method called in order to update progressBar every time a new question is loaded
   private void updateQuizProgressBar()
   {
       if(progressStatus < quizList.size()) {
           progressStatus++;
           progressBarQuiz.setProgress(progressStatus);
       }
       else{
           disableMultipleClicks();
           allQuestionsCompleted = true;

           levelPassed();

           showQuizResults();
       }

   }


   private void showQuestionNumber()
   {

    String actualQuestionNumberAndTotals = progressStatus + "/" + quizList.size();
    textViewQuestionNumber.setText(actualQuestionNumberAndTotals);

   }

   private void showQuizResults()
   {
       resultDialog = new Dialog(this);
       resultDialog.setContentView(R.layout.activity_quiz_results);

       TextView textViewLevelName = resultDialog.findViewById(R.id.text_view_result_level_name);
       TextView textViewClose = resultDialog.findViewById(R.id.text_view_result_close);
       TextView textViewCorrectAnswers = resultDialog.findViewById(R.id.text_view_result_correct_answers);
       TextView textViewIncorrectAnswers = resultDialog.findViewById(R.id.text_view_result_incorrect_answers);
       TextView textViewTotalAnswers = resultDialog.findViewById(R.id.text_view_result_total_answers);

       textViewClose.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               resultDialog.dismiss();
               Intent returnIntent = new Intent();
               setResult(RESULT_OK, returnIntent);
               finish();
           }
       });

       textViewLevelName.setText(levelName);
       textViewCorrectAnswers.setText(String.valueOf(totalNumberCorrectAnswers));
       textViewIncorrectAnswers.setText(String.valueOf(totalNumberIncorrectAnswers));
       textViewTotalAnswers.setText(String.valueOf(quizList.size()));


       resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       resultDialog.setCancelable(false);
       resultDialog.show();


   }

   private void levelPassed()
   {
       ParseQuery<ParseObject> query = ParseQuery.getQuery(queryLanguage);

       // Retrieve the object by id
       query.getInBackground(levelId, new GetCallback<ParseObject>() {
           public void done(ParseObject entity, ParseException e) {
               if (e == null) {
                   // Update the fields we want to
                   entity.put("level_passed", true);
                   // All other fields will remain the same
                   entity.saveInBackground();
               }
           }
       });
   }

   private void startAnimationNextQuestion()
   {

       if(progressStatus < quizList.size())
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

    private void stopAnimationNextQuestion()
    {
        shouldRepeatAnimation = false;
        imageViewNextQuestion.clearAnimation();
    }


    //loading default colors of both, constraintLayout background and icon number
    private void loadDefaultColors(){
        //set default colors every time user press Next
        constraintLayoutAnswerA.setBackground(OriginalBackgroundColor);
        constraintLayoutAnswerB.setBackground(OriginalBackgroundColor);
        constraintLayoutAnswerC.setBackground(OriginalBackgroundColor);
        constraintLayoutAnswerD.setBackground(OriginalBackgroundColor);
        imageViewLetterA.setImageResource(R.drawable.ic_white_answer_a);
        imageViewLetterB.setImageResource(R.drawable.ic_white_answer_b);
        imageViewLetterC.setImageResource(R.drawable.ic_white_answer_c);
        imageViewLetterD.setImageResource(R.drawable.ic_white_answer_d);
    }

    //disabling click event of answers after choosing one, prevents multiple clicks
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

    //enabling click events of answers after pressing in Next
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


    private void enableNextQuestionViewClick()
    {
        imageViewNextQuestion.setClickable(true);
    }

    private void disableNextQuestionViewClick()
    {
        imageViewNextQuestion.setClickable(false);
    }

    private void checkValueAnswerD(String answerD)
    {
        if (answerD.equals("null"))
        {
            hideAnswer();
        }
        else
        {
            unHideAnswer();
        }
    }


    private void hideAnswer(){

        constraintLayoutAnswerD.setVisibility(View.GONE);
        imageViewLetterD.setVisibility(View.GONE);
        textViewAnswerD.setVisibility(View.GONE);
    }

    private void unHideAnswer()
    {
        constraintLayoutAnswerD.setVisibility(View.VISIBLE);
        imageViewLetterD.setVisibility(View.VISIBLE);
        textViewAnswerD.setVisibility(View.VISIBLE);
    }

    private void hideQuestionImageUrl()
    {
        imageViewQuestionImageUrl.setVisibility(View.GONE);
    }

    private void unHideQuestionImageUrl()
    {
        imageViewQuestionImageUrl.setVisibility(View.VISIBLE);
    }

    private void hideNextQuestionView()
    {
        imageViewNextQuestion.setVisibility(View.GONE);
    }

    private void unHideNextQuestionView()
    {
        imageViewNextQuestion.setVisibility(View.VISIBLE);
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

    @Override
    public void onBackPressed() {
        if(!allQuestionsCompleted)
        {
            showDialog();
        }
        else
        {
            finish();
        }
    }

    private void showDialog()
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent returnIntent = new Intent();
                        setResult(RESULT_OK, returnIntent);
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Alert");
        builder.setMessage("Do you really want to exit practice Level " + levelNumber + "?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                if(!allQuestionsCompleted)
                {
                    showDialog();
                }
                else
                {
                    finish();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        TextView textViewToolBarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowTitleEnabled(false);
        }

        String levelNameAndNumber = levelName;
        textViewToolBarTitle.setText(levelNameAndNumber);

    }
}
