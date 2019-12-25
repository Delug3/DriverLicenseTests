package com.newagemedia.rtoexam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newagemedia.rtoexam.R;
import com.newagemedia.rtoexam.activities.core.MainMenuActivity;
import com.newagemedia.rtoexam.adapters.LevelsAdapter;
import com.newagemedia.rtoexam.adapters.StatesAdapter;
import com.newagemedia.rtoexam.models.Levels;
import com.newagemedia.rtoexam.models.States;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


import java.util.ArrayList;
import java.util.List;


public class StatesListActivity extends AppCompatActivity implements StatesAdapter.ItemClickListener {

    RecyclerView recyclerViewStates;
    private StatesAdapter statesAdapter;
    private static final String TAG = "RTO";
    final List<States> dataStates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("lmj5p9coFxtlfHx5EY6ZMZEmZwZkB6UqSM7tP5vj")
                .clientKey("1lD4V2Nbw6KwnG8FUbUAM56KsvwMYlxEVK89Py6d")
                .server("https://parseapi.back4app.com")
                .build()
        );

        recyclerViewStates = findViewById(R.id.recycler_view_states);
        recyclerViewStates.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewStates.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewStates.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        statesAdapter = new StatesAdapter(this,dataStates);
        recyclerViewStates.setHasFixedSize(true);
        statesAdapter.setClickListener(this);

        findStates();
    }

    public void findStates() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("States");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> result, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < result.size(); i++) {

                        States states = new States();
                        states.state_name = result.get(i).getString("state_name");
                        states.capital_name = result.get(i).getString("capital_name");
                        states.state_quiz_name = result.get(i).getString("state_quiz_name");
                        dataStates.add(states);
                    }

                } else {
                    // something went wrong
                }
                recyclerViewStates.setAdapter(statesAdapter);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + statesAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(StatesListActivity.this, MainMenuActivity.class);
        i.putExtra("STATE_QUIZ_NAME", dataStates.get(position).getState_quiz_name());
        startActivity(i);


    }



}
