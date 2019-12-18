package com.newagemedia.rtoexam.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.newagemedia.rtoexam.R;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        TextView textViewLevelName = findViewById(R.id.text_view_quiz_level_name);

        String levelName;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                levelName = null;


            } else {
                levelName = extras.getString("LEVEL_NAME");
                textViewLevelName.setText(levelName);

            }


        }
    }
}
