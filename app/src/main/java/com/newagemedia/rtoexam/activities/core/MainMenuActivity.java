package com.newagemedia.rtoexam.activities.core;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.newagemedia.rtoexam.R;
import com.newagemedia.rtoexam.activities.practice.PracticeLevelsListActivity;

/**
 * Created by Manuel Fernandez Garcia.  Email(mfgarcia87@gmail.com) on 15/12/2019.
 */
public class MainMenuActivity extends AppCompatActivity {

    private String stateQuizNameValue;
    //main activity including practice, test, reading and settings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            stateQuizNameValue = extras.getString("STATE_QUIZ_NAME");
        }

        TextView textViewPractice = findViewById(R.id.text_view_practice);

        textViewPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, PracticeLevelsListActivity.class);
                i.putExtra("STATE_QUIZ_NAME",stateQuizNameValue);
                startActivity(i);
            }
        });

    }
}
