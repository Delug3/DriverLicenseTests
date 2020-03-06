package com.driverlicense.tests.activities.signs;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.driverlicense.tests.R;
import com.squareup.picasso.Picasso;

public class TrafficSignsDetailsActivity extends AppCompatActivity {

    private TextView textViewTrafficSignsDetailsName, textViewTrafficSignsDetailsDefinition;
    private ImageView imageViewTrafficSignsDetailsUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_signs_details);

        initViews();

        String name, definition, imageUrl;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                name = null;
                definition = null;
                imageUrl = null;
            } else {

                name = extras.getString("TRAFFIC_SIGNS_NAME");
                definition = extras.getString("TRAFFIC_SIGNS_DEFINITION");
                imageUrl = extras.getString("TRAFFIC_SIGNS_IMAGE");

                setContent(name, definition, imageUrl);

            }

        }
    }

    private void initViews(){
        textViewTrafficSignsDetailsName = findViewById(R.id.text_view_traffic_sign_details_name);
        imageViewTrafficSignsDetailsUrl = findViewById(R.id.image_view_traffic_sign_details_image_url);
        textViewTrafficSignsDetailsDefinition = findViewById(R.id.text_view_traffic_sign_details_definition);

    }

    private void setContent(String name, String definition, String imageUrl){
        textViewTrafficSignsDetailsName.setText(name);
        textViewTrafficSignsDetailsDefinition.setText(definition);
        Picasso.get().load(imageUrl).into(imageViewTrafficSignsDetailsUrl);
    }

}
