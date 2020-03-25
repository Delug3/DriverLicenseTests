package com.driverlicense.tests.activities.reading;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.driverlicense.tests.R;
import com.driverlicense.tests.adapters.ReadingAdapter;
import com.driverlicense.tests.models.Reading;
import com.driverlicense.tests.models.TrafficSigns;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReadingListActivity extends AppCompatActivity {

    private String queryLanguage;
    private RecyclerView recyclerViewReading;
    SearchView searchViewReading;
    ReadingAdapter readingAdapter;
    private List<Reading> readingList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        initViews();
        configureToolbar();
        setUpSearchView();
        setUpRecyclerView();
        getQueryLanguage();
        getSigns(queryLanguage);

    }

    private void setUpSearchView() {

        searchViewReading.setQueryHint("Search for traffic sign answer");
        searchViewReading.setIconifiedByDefault(false);
        searchViewReading.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                readingAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private void setUpRecyclerView() {

        recyclerViewReading = findViewById(R.id.recycler_view_reading);
        recyclerViewReading.setHasFixedSize(true);
        recyclerViewReading.setLayoutManager(new LinearLayoutManager (this));
        readingList = new ArrayList<>();

        readingAdapter = new ReadingAdapter(this,readingList);
    }

    private void getQueryLanguage()
    {

        Locale.getDefault().getDisplayLanguage();
        if(Locale.getDefault().getLanguage().equals("en"))
        {
            queryLanguage="Reading_EN";
        }
        if(Locale.getDefault().getLanguage().equals("hi"))
        {
            queryLanguage="Reading_HI";
        }
        else
        {
            queryLanguage="Reading_EN";
        }
    }

    public void getSigns(String queryLanguage) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(queryLanguage);
        query.findInBackground((result, e) -> {

            if (e == null) {

                for (int i = 0; i < result.size(); i++) {

                    Reading reading = new Reading();
                    reading.question = result.get(i).getString("question");
                    reading.correctAnswer = result.get(i).getString("correct_answer");
                    reading.imageUrl = result.get(i).getString("image_url");

                    readingList.add(reading);

                    readingAdapter.notifyDataSetChanged();

                    recyclerViewReading.setAdapter(readingAdapter);
                }

            } else {
                //something went wrong
            }
        });
    }

    private void initViews() {
        recyclerViewReading = findViewById(R.id.recycler_view_reading);
        searchViewReading = findViewById(R.id.search_view_reading);
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
        textViewToolBarTitle.setText(R.string.reading_main_menu);
    }
}
