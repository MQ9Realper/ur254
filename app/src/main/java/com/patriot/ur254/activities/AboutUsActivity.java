package com.patriot.ur254.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.patriot.ur254.R;

import java.util.Calendar;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getVersionInfo();
        getCopyrightInfo();
        initToolBar();
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about_us);
        toolbar.setTitle("About Us");
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_keyboard_arrow_left_white_24dp));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutUsActivity.this, TimelineActivity.class);
                startActivity(intent);
                finish();
            }
        });
        setSupportActionBar(toolbar);
    }

    private void getVersionInfo() {
        String versionName = "";
        int versionCode = -1;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView textViewVersionInfo = (TextView) findViewById(R.id.textViewVersion);
        textViewVersionInfo.setText("Version " + versionName);
    }

    private void getCopyrightInfo(){
        String copyright = getResources().getString(R.string.copyright);
        String appName = getResources().getString(R.string.app_name);
        String currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

        TextView textViewCopyright = (TextView) findViewById(R.id.textViewCopyright);
        textViewCopyright.setText(copyright  + currentYear + " " + appName + "." + " All rights reserved");
    }

}
