package com.newagemedia.rtoexam.activities.core;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.newagemedia.rtoexam.R;
import com.newagemedia.rtoexam.activities.LevelsListActivity;

public class MainActivity extends AppCompatActivity {


    //main activity including practice, test, reading and settings
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewPractice = findViewById(R.id.text_view_practice);

        textViewPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LevelsListActivity.class));
            }
        });

    }
}
