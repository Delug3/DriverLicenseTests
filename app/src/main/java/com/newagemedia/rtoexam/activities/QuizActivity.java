package com.newagemedia.rtoexam.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.newagemedia.rtoexam.R;
import com.newagemedia.rtoexam.models.Levels;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class QuizActivity extends AppCompatActivity {

    final List<Levels> questionsAndAnswers = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        TextView textViewLevelName = findViewById(R.id.text_view_quiz_level_name);

        String levelName;
        List<String> quiz = null;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                levelName = null;
                quiz = null;

            } else {
                levelName = extras.getString("LEVEL_NAME");
                quiz = getIntent().getStringArrayListExtra("LEVEL_QUIZ");
                textViewLevelName.setText(levelName);
            }
        }

        loadQuestionAndAnswers(quiz);
    }

    //each time we call this method, will load the next question & answers
    private void loadQuestionAndAnswers(List<String>quiz)
    {
        for (int i = 0; i < quiz.size(); i++) {

            Levels levels = new Levels();
            levels.question = getString("question");







        }

    }



}
