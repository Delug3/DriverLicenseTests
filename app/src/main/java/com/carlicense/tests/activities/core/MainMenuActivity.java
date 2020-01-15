package com.carlicense.tests.activities.core;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.carlicense.tests.R;
import com.carlicense.tests.activities.practice.PracticeLevelsListActivity;

/**
 * Created by Manuel Fernandez Garcia.  Email(mfgarcia87@gmail.com) on 15/12/2019.
 */
public class MainMenuActivity extends AppCompatActivity {

    private String stateQuizNameValue;
    private ConstraintLayout constraintLayoutMainMenuPractice;
    //main activity including practice, test, reading and settings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            stateQuizNameValue = extras.getString("STATE_QUIZ_NAME");
        }

        constraintLayoutMainMenuPractice = findViewById(R.id.constraint_layout_main_menu_practice);

        constraintLayoutMainMenuPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, PracticeLevelsListActivity.class);
                i.putExtra("STATE_QUIZ_NAME",stateQuizNameValue);
                startActivity(i);
            }
        });

    }
}
