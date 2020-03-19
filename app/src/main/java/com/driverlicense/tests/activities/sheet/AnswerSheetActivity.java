package com.driverlicense.tests.activities.sheet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.driverlicense.tests.models.SavedAnswers;

import java.util.List;

public class AnswerSheetActivity extends AppCompatActivity {
    private List<SavedAnswers> savedAnswersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                savedAnswersList = null;
            } else {
                savedAnswersList = extras.getParcelableArrayList("SAVED_ANSWERS");

            }

        }
        //SharedPreferences sharedPref = getSharedPreferences("test_prefs", Activity.MODE_PRIVATE);
        //String saved = sharedPref.getStringSet("SAVED_ANSWERS", null);
    }

}
