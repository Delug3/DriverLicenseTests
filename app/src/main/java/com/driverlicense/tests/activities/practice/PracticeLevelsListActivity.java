package com.driverlicense.tests.activities.practice;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.driverlicense.tests.R;
import com.driverlicense.tests.adapters.PracticeLevelsAdapter;
import com.driverlicense.tests.models.Practice;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;

import java.util.List;
import java.util.Locale;


public class PracticeLevelsListActivity extends AppCompatActivity implements PracticeLevelsAdapter.ItemClickListener {

    RecyclerView recyclerViewPracticeLevels;
    private PracticeLevelsAdapter practiceLevelsAdapter;
    private String queryLanguage;
    final List<Practice> practiceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        configureToolbar();

        //obtain state/capital from shared preferences(activity where user select state) and then pass that to findLevels
       //String stateQuizNameValue="andhra_pradesh_quiz";

        SharedPreferences sharedPref = getSharedPreferences("states_prefs", Activity.MODE_PRIVATE);
        String stateQuizNameSharedPref = sharedPref.getString("state_quiz_name", "state");

        recyclerViewPracticeLevels = findViewById(R.id.recycler_view_practice_levels);
        recyclerViewPracticeLevels.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPracticeLevels.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        practiceLevelsAdapter = new PracticeLevelsAdapter(this, practiceList);
        recyclerViewPracticeLevels.setHasFixedSize(true);
        practiceLevelsAdapter.setClickListener(this);



       //this method get data class with the device language
       getQueryLanguage();

       //this method load all levels in the recyclerView with the selected state
       findLevels(stateQuizNameSharedPref,queryLanguage);
    }


    /** Values are placed in Json Object.
     * Json Array inside Json Object
     * Need to retrieve object position from "result" json object parsing it.
     */
    public void findLevels(final String stateQuizNameSharedPref,String queryLanguage) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(queryLanguage);
        //sorting object, ordering it by level number
        query.orderByAscending("level_number");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < result.size(); i++) {

                        Practice practice = new Practice();
                        practice.level_id = result.get(i).getObjectId();
                        practice.level_number = result.get(i).getNumber("level_number");
                        practice.level_name = result.get(i).getString("level_name");
                        practice.level_color = result.get(i).getString("level_color");
                        practice.level_passed = result.get(i).getBoolean("level_passed");
                        //retrieving array of quiz using the state name
                        practice.quiz = result.get(i).getList(stateQuizNameSharedPref);

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
        Intent i = new Intent(PracticeLevelsListActivity.this, PracticeQuizActivity.class);
        i.putExtra("LEVEL_ID", practiceList.get(position).getLevel_id());
        i.putExtra("LEVEL_NAME", practiceList.get(position).getLevel_name());
        i.putExtra("LEVEL_NUMBER", practiceList.get(position).getLevel_number());
        i.putExtra("QUERY_LANGUAGE", queryLanguage);
        i.putStringArrayListExtra("LEVEL_QUIZ", (ArrayList<String>) practiceList.get(position).getQuiz());
        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Intent refresh = new Intent(this, PracticeLevelsListActivity.class);
            startActivity(refresh);
            this.finish();
        }
    }

    @Override
    public void onBackPressed() {

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                finish();

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
        textViewToolBarTitle.setText(R.string.practice);
    }

}
