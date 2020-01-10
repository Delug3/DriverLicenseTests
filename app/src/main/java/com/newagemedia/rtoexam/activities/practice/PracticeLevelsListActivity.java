package com.newagemedia.rtoexam.activities.practice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newagemedia.rtoexam.R;
import com.newagemedia.rtoexam.adapters.PracticeLevelsAdapter;
import com.newagemedia.rtoexam.models.Practice;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.util.ArrayList;

import java.util.List;
import java.util.Locale;


public class PracticeLevelsListActivity extends AppCompatActivity implements PracticeLevelsAdapter.ItemClickListener {

    RecyclerView recyclerViewPracticeLevels;
    private PracticeLevelsAdapter practiceLevelsAdapter;
    private static final String TAG = "RTO";
    private ImageView imageViewLevelLeftArrow;
    private String queryLanguage;
    private String stateQuizName;
    final List<Practice> practiceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        imageViewLevelLeftArrow = findViewById(R.id.image_view_levels_left_arrow);

        //obtain state/capital from extras(activity where user select state) and then pass that to findLevels
       //String stateQuizNameValue="andhra_pradesh_quiz";
       Bundle extras = getIntent().getExtras();
       if(extras!=null){
           stateQuizName = extras.getString("STATE_QUIZ_NAME");
       }

       recyclerViewPracticeLevels = findViewById(R.id.recycler_view_practice_levels);
       recyclerViewPracticeLevels.setLayoutManager(new LinearLayoutManager(this));
       recyclerViewPracticeLevels.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
       practiceLevelsAdapter = new PracticeLevelsAdapter(this, practiceList);
       recyclerViewPracticeLevels.setHasFixedSize(true);
       practiceLevelsAdapter.setClickListener(this);

       imageViewLevelLeftArrow.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });

       //this method get data class with the device language
       getQueryLanguage();

       //this method load all levels in the recyclerView with the selected state
       findLevels(stateQuizName,queryLanguage);
    }


    /** Values are placed in Json Object.
     * Json Array inside Json Object
     * Need to retrieve object position from "result" json object parsing it.
     */
    public void findLevels(final String stateQuizName,String queryLanguage) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(queryLanguage);
        //sorting object, ordering it by level number
        query.orderByAscending("level_number");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < result.size(); i++) {

                        Practice practice = new Practice();
                        practice.level_number = result.get(i).getNumber("level_number");
                        practice.level_name = result.get(i).getString("level_name");
                        practice.level_color = result.get(i).getString("level_color");
                        //retrieving array of quiz using the state name
                        practice.quiz = result.get(i).getList(stateQuizName);

                        //send result data to adapter->recyclerView
                        practiceList.add(practice);
                    }

                } else {
                    // something went wrong
                }
                recyclerViewPracticeLevels.setAdapter(practiceLevelsAdapter);
            }
        });

    }

    //Obtaining language of device to load query with the appropriate data
    //class Practice: EN,HI....
    private void getQueryLanguage()
    {

        Locale.getDefault().getDisplayLanguage();
        if(Locale.getDefault().getLanguage().equals("en"))
        {
            queryLanguage="Practice_EN";
        }
        if(Locale.getDefault().getLanguage().equals("hi"))
        {
            queryLanguage="Practice_HI";
        }
        else
        {
            queryLanguage="Practice_EN";
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + practiceLevelsAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(PracticeLevelsListActivity.this, PracticeQuizActivity.class);
        i.putExtra("LEVEL_NAME", practiceList.get(position).getLevel_name());
        i.putExtra("LEVEL_NUMBER", practiceList.get(position).getLevel_number());
        i.putStringArrayListExtra("LEVEL_QUIZ", (ArrayList<String>) practiceList.get(position).getQuiz());
        startActivity(i);
    }


    @Override
    public void onBackPressed() {

        finish();
    }


}
