package com.driverlicense.tests.activities.states;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.driverlicense.tests.R;
import com.driverlicense.tests.activities.core.App;
import com.driverlicense.tests.activities.menu.MainMenuActivity;
import com.driverlicense.tests.adapters.StatesAdapter;
import com.driverlicense.tests.models.States;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class StatesListActivity extends AppCompatActivity implements StatesAdapter.ItemClickListener {

    RecyclerView recyclerViewStates;
    private StatesAdapter statesAdapter;
    final List<States> dataStates = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_states);

        configureToolbar();

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

        Intent i = new Intent(StatesListActivity.this, MainMenuActivity.class);

        SharedPreferences sharedPref = getSharedPreferences("states_prefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("state_quiz_name", dataStates.get(position).getState_quiz_name());
        editor.apply();

        startActivity(i);
        finish();
    }

    private void configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        TextView textViewToolBarTitle = toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if(actionbar != null) {
            actionbar.setDisplayShowTitleEnabled(false);
        }
        textViewToolBarTitle.setText(R.string.select_state);
    }


}
