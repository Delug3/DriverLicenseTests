package com.driverlicense.tests.activities.signs;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.driverlicense.tests.R;
import com.driverlicense.tests.adapters.TrafficSignsAdapter;
import com.driverlicense.tests.models.TrafficSigns;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrafficSignsListActivity extends AppCompatActivity implements TrafficSignsAdapter.ItemClickListener {

    private String queryLanguage;
    RecyclerView recyclerViewTrafficSigns;
    TrafficSignsAdapter trafficSignsAdapter;
    SearchView searchViewTrafficSigns;
    List<TrafficSigns> trafficSignsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_signs);

        configureToolbar();

        setUpSearchView();

        setUpRecyclerView();

        getQueryLanguage();

        getTrafficSigns(queryLanguage);
    }

    private void setUpSearchView() {

        searchViewTrafficSigns = findViewById(R.id.search_view_traffic_signs);
        searchViewTrafficSigns.setQueryHint("Search for traffic sign");
        searchViewTrafficSigns.setIconifiedByDefault(false);
        searchViewTrafficSigns.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                trafficSignsAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private void setUpRecyclerView() {

        recyclerViewTrafficSigns = findViewById(R.id.recycler_view_traffic_signs);
        recyclerViewTrafficSigns.setHasFixedSize(true);
        recyclerViewTrafficSigns.setLayoutManager(new GridLayoutManager(this,2));

        trafficSignsList = new ArrayList<>();

        trafficSignsAdapter = new TrafficSignsAdapter(this,trafficSignsList,this);

        trafficSignsAdapter.setClickListener(this);
    }

    public void getTrafficSigns(String queryLanguage) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(queryLanguage);
        //sorting object, ordering it by level number
        query.orderByAscending("traffic_sign_name");
        query.findInBackground((result, e) -> {

            if (e == null) {

                for (int i = 0; i < result.size(); i++) {

                    TrafficSigns trafficSigns = new TrafficSigns();
                    trafficSigns.trafficSignName = result.get(i).getString("traffic_sign_name");
                    trafficSigns.trafficSignDefinition = result.get(i).getString("traffic_sign_definition");
                    trafficSigns.trafficSignImageUrl = result.get(i).getString("traffic_sign_image_url");

                    trafficSignsList.add(trafficSigns);

                    trafficSignsAdapter.notifyDataSetChanged();

                    recyclerViewTrafficSigns.setAdapter(trafficSignsAdapter);
                }

            } else {
              //something went wrong
            }
        });
    }

    //Obtaining language of device to load query with the appropriate data
    //class Traffic Signs: EN,HI...
    private void getQueryLanguage()
    {

        Locale.getDefault().getDisplayLanguage();
        if(Locale.getDefault().getLanguage().equals("en"))
        {
            queryLanguage="Traffic_Signs_EN";
        }
        if(Locale.getDefault().getLanguage().equals("hi"))
        {
            queryLanguage="Traffic_Signs_HI";
        }
        else
        {
            queryLanguage="Traffic_Signs_EN";
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        TrafficSigns trafficSigns = trafficSignsAdapter.getFilteredItem(position);

        Intent intent = new Intent(TrafficSignsListActivity.this,TrafficSignsDetailsActivity.class);
        intent.putExtra("TRAFFIC_SIGNS_NAME", trafficSigns.getTrafficSignName());
        intent.putExtra("TRAFFIC_SIGNS_DEFINITION", trafficSigns.getTrafficSignDefinition());
        intent.putExtra("TRAFFIC_SIGNS_IMAGE", trafficSigns.getTrafficSignImageUrl());
        startActivity(intent);
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
        textViewToolBarTitle.setText(R.string.traffic_sign_main_menu);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchViewTrafficSigns = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchViewTrafficSigns.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchViewTrafficSigns.setMaxWidth(Integer.MAX_VALUE);

        searchViewTrafficSigns.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                trafficSignsAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchViewTrafficSigns.isIconified()) {
            searchViewTrafficSigns.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
    */

}
