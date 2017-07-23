package com.patriot.ur254.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.patriot.ur254.R;
import com.patriot.ur254.utils.SharedPreference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreference sharedPreference = new SharedPreference(this);

        if (sharedPreference.getIsLoggedIn()) {
            Intent intentTimeline = new Intent(SplashActivity.this, TimelineActivity.class);
            startActivity(intentTimeline);
            finish();
        }

        RelativeLayout layoutGetStarted = (RelativeLayout) findViewById(R.id.layoutGetStarted);
        layoutGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStart = new Intent(SplashActivity.this, StartActivity.class);
                startActivity(intentStart);
            }
        });
    }
}
