package com.driverlicense.tests.activities.sheet;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.driverlicense.tests.R;
import com.driverlicense.tests.adapters.AnswerSheetAdapter;
import com.driverlicense.tests.models.Sheet;

import java.util.ArrayList;
import java.util.List;

public class AnswerSheetActivity extends AppCompatActivity{
    RecyclerView recyclerViewAnswerSheet;
    private AnswerSheetAdapter answerSheetAdapter;
    private List<Sheet> sheetList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_sheet);
       if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                sheetList = null;
            } else {
                sheetList = extras.getParcelableArrayList("SAVED_ANSWERS");
            }

        }
        initViews();
        setUpRecyclerView();

        //SharedPreferences sharedPref = getSharedPreferences("test_prefs", Activity.MODE_PRIVATE);
        //String saved = sharedPref.getStringSet("SAVED_ANSWERS", null);
    }

    private void initViews() {
        recyclerViewAnswerSheet = findViewById(R.id.recycler_view_answer_sheet);
    }


    private void setUpRecyclerView() {
        recyclerViewAnswerSheet.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAnswerSheet.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerViewAnswerSheet.setHasFixedSize(true);
        answerSheetAdapter = new AnswerSheetAdapter(this, sheetList);
        recyclerViewAnswerSheet.setAdapter(answerSheetAdapter);

    }
}
