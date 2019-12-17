package com.newagemedia.rtoexam.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
       recyclerViewLevels.setLayoutManager(new LinearLayoutManager(this));
       levelsAdapter = new LevelsAdapter(this,dataLevels);
       recyclerViewLevels.setHasFixedSize(true);
       levelsAdapter.setClickListener(this);

       findLevels();
    }
    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + levelsAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
        //Intent i = new Intent(LevelsListActivity.this, LevelsQuizActivity.class);

        //i.putExtra("LEVEL_NAME", dataLevels.get(position).getLevel());
        //i.putStringArrayListExtra("LEVEL_INFO", (ArrayList<String>) dataLevels.get(position).getInformation());
        //startActivity(i);


    }
    public void findLevels() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Levels");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> listLevels, ParseException e) {

                if (e == null) {

                    for (int i = 0; i < listLevels.size(); i++) {

                        Levels levels = new Levels();

                        levels.level = listLevels.get(i).getString("level");


                       // levels.information = listLevels.get(i).getList("information");

                        String name = listLevels.get(i).getString("level");
                        Log.e(TAG, "Title: " + name);

                        //send listLevels data to adapter->recyclerView

                        dataLevels.add(levels);

                    }


                } else {
                    // something went wrong
                }
                recyclerViewLevels.setAdapter(levelsAdapter);
            }
        });

    }





}
