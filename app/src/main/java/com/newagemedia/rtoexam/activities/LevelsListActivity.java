package com.newagemedia.rtoexam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newagemedia.rtoexam.R;
import com.newagemedia.rtoexam.adapters.LevelsAdapter;
import com.newagemedia.rtoexam.models.Levels;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.util.ArrayList;
import java.util.List;


public class LevelsListActivity extends AppCompatActivity implements LevelsAdapter.ItemClickListener {


    RecyclerView recyclerViewLevels;
    private LevelsAdapter levelsAdapter;
    private static final String TAG = "RTO";

    final List<Levels> dataLevels = new ArrayList<>();

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        //Access to parse, where the levels (questions and answers) is stored
       Parse.initialize(new Parse.Configuration.Builder(this)
               .applicationId("lmj5p9coFxtlfHx5EY6ZMZEmZwZkB6UqSM7tP5vj")
               .clientKey("1lD4V2Nbw6KwnG8FUbUAM56KsvwMYlxEVK89Py6d")
               .server("https://parseapi.back4app.com")
               .build()
       );

       recyclerViewLevels = findViewById(R.id.recycler_view_levels);
       recyclerViewLevels.setLayoutManager(new LinearLayoutManager(this));
       recyclerViewLevels.addItemDecoration(new DividerItemDecoration(this,
               DividerItemDecoration.VERTICAL));
       levelsAdapter = new LevelsAdapter(this,dataLevels);
       recyclerViewLevels.setHasFixedSize(true);
       levelsAdapter.setClickListener(this);

       findLevels();
    }

    public void findLevels() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Levels");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < result.size(); i++) {

                        Levels levels = new Levels();

                        levels.level = result.get(i).getString("level");
                        levels.quiz = result.get(i).getList("quiz");
 /*
                        levels.question = result.get(i).getString("question");
                        levels.answer_one = result.get(i).getString("answer_one");
                        levels.answer_two = result.get(i).getString("answer_two");
                        levels.answer_three = result.get(i).getString("answer_three");
                        levels.answer_four = result.get(i).getString("answer_four");
                        levels.correct_answer = result.get(i).getString("correct_answer");
                        */


                        String name = result.get(i).getString("level");
                        Log.e(TAG, "Title: " + name);

                        //send result data to adapter->recyclerView

                        dataLevels.add(levels);

                    }


                } else {
                    // something went wrong
                }
                recyclerViewLevels.setAdapter(levelsAdapter);
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + levelsAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LevelsListActivity.this, QuizActivity.class);
        i.putExtra("LEVEL_NAME", dataLevels.get(position).getLevel());
        i.putStringArrayListExtra("LEVEL_QUIZ", (ArrayList<String>) dataLevels.get(position).getQuiz());
        startActivity(i);

    }



}
