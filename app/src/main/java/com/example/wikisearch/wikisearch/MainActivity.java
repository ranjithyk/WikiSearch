package com.example.wikisearch.wikisearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        initializeViews();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case  R.id.search_button: {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void initializeViews() {

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(this);
    }
}
