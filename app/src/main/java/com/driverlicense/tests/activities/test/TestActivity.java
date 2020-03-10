package com.driverlicense.tests.activities.test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.driverlicense.tests.R;
import com.driverlicense.tests.models.States;
import com.driverlicense.tests.models.Test;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestActivity extends AppCompatActivity {

    Random rand = new Random();
    final List<Test> testList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getRandomQuestions();


    }

    public void getRandomQuestions() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Test_EN");
        query.setLimit(200);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < 5; i++) {

                        int randomIndex = rand.nextInt(result.size());

                        Test test = new Test();
                        test.question = result.get(randomIndex).getString("question");

                        testList.add(test);
                    }

                } else {
                    // something went wrong
                }

            }
        });
    }
}
