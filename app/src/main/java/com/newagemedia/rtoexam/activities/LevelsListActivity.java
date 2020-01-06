package com.newagemedia.rtoexam.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
import java.util.Locale;


public class LevelsListActivity extends AppCompatActivity implements LevelsAdapter.ItemClickListener {

    RecyclerView recyclerViewLevels;
    private LevelsAdapter levelsAdapter;
    private static final String TAG = "RTO";
    private String queryLanguage;
    private String stateQuizName;
    final List<Levels> dataLevels = new ArrayList<>();

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        //obtain state/capital from extras(activity where user select state) and then pass that to findLevels
       //String stateQuizNameValue="andhra_pradesh_quiz";
       Bundle extras = getIntent().getExtras();
       if(extras!=null){
           stateQuizName = extras.getString("STATE_QUIZ_NAME");
       }
       Parse.initialize(new Parse.Configuration.Builder(this)
               .applicationId(getString(R.string.back4app_app_id))
               .clientKey(getString(R.string.back4app_client_key))
               .server(getString(R.string.back4app_server_url))
               .build()
       );
       recyclerViewLevels = findViewById(R.id.recycler_view_levels);
       recyclerViewLevels.setLayoutManager(new LinearLayoutManager(this));
       recyclerViewLevels.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
       levelsAdapter = new LevelsAdapter(this,dataLevels);
       recyclerViewLevels.setHasFixedSize(true);
       levelsAdapter.setClickListener(this);

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
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < result.size(); i++) {

                        Levels levels = new Levels();
                        levels.level_number = result.get(i).getNumber("level_number");
                        levels.level_name = result.get(i).getString("level_name");
                        //retrieving array of quiz using the state name
                        levels.quiz = result.get(i).getList(stateQuizName);

                        String name = result.get(i).getString("level_name");
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

    //Obtaining language of device to load query with the appropriate data
    //class Levels: EN,HI....
    private void getQueryLanguage()
    {

        Locale.getDefault().getDisplayLanguage();
        if(Locale.getDefault().getLanguage().equals("en"))
        {
            queryLanguage="Levels_EN";
        }
        if(Locale.getDefault().getLanguage().equals("hi"))
        {
            queryLanguage="Levels_HI";
        }
        else
        {
            queryLanguage="Levels_EN";
        }
    }


    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + levelsAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(LevelsListActivity.this, QuizActivity.class);
        i.putExtra("LEVEL_NAME", dataLevels.get(position).getLevel_name());
        i.putExtra("LEVEL_NUMBER",dataLevels.get(position).getLevel_number());
        i.putStringArrayListExtra("LEVEL_QUIZ", (ArrayList<String>) dataLevels.get(position).getQuiz());
        startActivity(i);

    }


    @Override
    public void onBackPressed() {

        finish();
    }


}
