package com.newagemedia.rtoexam.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.newagemedia.rtoexam.R;
import com.parse.Parse;


public class LevelsListActivity extends AppCompatActivity {

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


    }
}
