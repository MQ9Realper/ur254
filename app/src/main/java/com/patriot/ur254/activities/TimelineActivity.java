package com.patriot.ur254.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Telephony;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.patriot.ur254.R;
import com.patriot.ur254.fragments.GalleryFragment;
import com.patriot.ur254.fragments.HomeFragment;
import com.patriot.ur254.fragments.ProjectsFragment;
import com.patriot.ur254.fragments.RecruitFragment;
import com.patriot.ur254.utils.UniversalUtils;

public class TimelineActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private UniversalUtils universalUtils;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FirebaseAuth firebaseAuth;

    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PROJECTS = "projects";
    private static final String TAG_RECRUIT = "recruit";
    private static final String TAG_GALLERY = "gallery";
    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        universalUtils = new UniversalUtils(this);
        firebaseAuth = FirebaseAuth.getInstance();
        mHandler = new Handler();
        activityTitles = new String[]{"Timeline", "Projects", "Recruit", "Gallery"};
        InitToolbar();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        InitLogoutView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    private void InitToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarTimeline);
        toolbar.setTitle(getResources().getString(R.string.activity_timeline));
        toolbar.setTitleTextColor(Color.WHITE);
        universalUtils.centerToolbarTitle(toolbar);
        setSupportActionBar(toolbar);
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = TimelineActivity.this.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    /**
     * Return respective fragments
     **/
    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new HomeFragment();
            case 1:
                return new ProjectsFragment();
            case 2:
                return new RecruitFragment();
            case 3:
                return new GalleryFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            System.exit(1);
        } else {
            System.exit(1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update_status) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_home:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case R.id.nav_projects:
                navItemIndex = 1;
                CURRENT_TAG = TAG_PROJECTS;
                break;
            case R.id.nav_recruit:
                navItemIndex = 2;
                CURRENT_TAG = TAG_RECRUIT;
                break;
            case R.id.nav_gallery:
                navItemIndex = 3;
                CURRENT_TAG = TAG_GALLERY;
                break;
            case R.id.nav_privacy:
                startActivity(new Intent(TimelineActivity.this, PrivacyPolicyActivity.class));
                break;
            case R.id.nav_about_us:
                startActivity(new Intent(TimelineActivity.this, AboutUsActivity.class));
                break;
            case R.id.nav_terms_of_use:
                startActivity(new Intent(TimelineActivity.this, TermsOfUseActivity.class));
                break;
            default:
                break;
        }

        if (item.isChecked()) {
            item.setChecked(false);
        } else {
            item.setChecked(true);
        }
        item.setChecked(true);

        loadHomeFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void InitLogoutView() {
        RelativeLayout layoutLogout = (RelativeLayout) navigationView.findViewById(R.id.layoutButtonLogout);
        layoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                universalUtils.ShowLogoutConfirmation("Are you sure you want to logout?");
            }
        });
    }

}
