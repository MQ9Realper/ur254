package com.patriot.ur254.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.patriot.ur254.R;
import com.patriot.ur254.adapters.MainTabAdapter;
import com.patriot.ur254.fragments.LoginFragment;
import com.patriot.ur254.fragments.SignUpFragment;
import com.patriot.ur254.utils.SharedPreference;
import com.patriot.ur254.views.SlidingTabLayout;

public class StartActivity extends AppCompatActivity {
    private CharSequence[] tabTitles = {"SIGN IN","SIGN UP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        InitToolbar();

        SignUpFragment signUpFragment = new SignUpFragment();
        LoginFragment loginFragment = new LoginFragment();
        Fragment[] fragments = new Fragment[]{loginFragment, signUpFragment};

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SlidingTabLayout slidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayoutMain);

        MainTabAdapter mainTabAdapter = new MainTabAdapter(getSupportFragmentManager(), tabTitles, 2, fragments);
        viewPager.setAdapter(mainTabAdapter);

        slidingTabLayout.setDistributeEvenly(true);

        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }
        });
        slidingTabLayout.setViewPager(viewPager);
    }

    private void InitToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarStart);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        setSupportActionBar(toolbar);
    }

}
