package com.patriot.ur254.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.patriot.ur254.R;
import com.patriot.ur254.utils.SharedPreference;

public class MainActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreference sharedPreference = new SharedPreference(this);

        if (sharedPreference.getIsLoggedIn()) {
            Intent intentTimeline = new Intent(MainActivity.this, TimelineActivity.class);
            startActivity(intentTimeline);
            finish();
        }
        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        addSlide(AppIntroFragment.newInstance("Unity", "A combination or ordering of parts in a literary or artistic production that constitutes a whole or promotes an undivided total effect.", R.drawable.jubilee_logo, getResources().getColor(R.color.jubileePrimaryColor)));

        addSlide(AppIntroFragment.newInstance("Integrity", "Consistency of actions, values, methods, measures, principles, expectations, and outcomes.", R.drawable.jubilee_one, getResources().getColor(R.color.jubileePrimaryColor)));

        addSlide(AppIntroFragment.newInstance("Peace", "A state or period of mutual concord between governments", R.drawable.jubilee_two, getResources().getColor(R.color.jubileePrimaryColor)));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(getResources().getColor(R.color.jubileeYellow));
        setSeparatorColor(getResources().getColor(R.color.jubileeGreen));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setDoneTextTypeface("fonts/SourceSansPro-Regular.ttf");
        setSkipTextTypeface("fonts/SourceSansPro-Regular.ttf");

        setImageNextButton(getResources().getDrawable(R.drawable.ic_keyboard_arrow_right_white_24dp));
        setDepthAnimation();

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

        Intent intentStart = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intentStart);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);

        Intent intentStart = new Intent(MainActivity.this, StartActivity.class);
        startActivity(intentStart);

    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
    }
}
