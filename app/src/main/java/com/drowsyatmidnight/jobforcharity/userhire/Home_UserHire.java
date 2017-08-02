package com.drowsyatmidnight.jobforcharity.userhire;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.model.User_Model;
import com.drowsyatmidnight.jobforcharity.userhire.fragment_item.FmCategory;
import com.drowsyatmidnight.jobforcharity.userhire.fragment_item.FmJobInHistory;
import com.drowsyatmidnight.jobforcharity.userhire.fragment_item.FmJobInProgress;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.lapism.searchview.SearchView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home_UserHire extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.searchViewHome)
    SearchView searchViewHome;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user_hire);
        ButterKnife.bind(this);
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_category);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        searchViewHome.bringToFront();
        navigationView.bringToFront();
        FirebaseCrash.setCrashCollectionEnabled(false);
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_home_user_hire, navigationView, false);
        navigationView.addHeaderView(headerView);
        ImageView profile_imageNav = (ImageView)headerView.findViewById(R.id.profile_imageNav);
        final TextView profile_NameNav = (TextView) headerView.findViewById(R.id.profile_NameNav);
        profile_imageNav.setImageResource(R.drawable.test);
        DataFirebase.getUserInfo(KeyValueFirebase.UID, new DataFirebase.OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                User_Model user_model = data.getValue(User_Model.class);
                profile_NameNav.setText(user_model.getLName()+" "+user_model.getFName());
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
        setUpSearchView();
    }

    private void setUpSearchView() {
        searchViewHome.setTheme(3002);
        searchViewHome.setOnMenuClickListener(new SearchView.OnMenuClickListener() {
            @Override
            public void onMenuClick() {
                drawer.openDrawer(Gravity.START);
            }
        });
        searchViewHome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent goSearch = new Intent(Home_UserHire.this, JobsCategory.class);
                goSearch.putExtra("searchType", KeyValueFirebase.SEARCHTYPE_ALL);
                goSearch.putExtra("query", query);
                startActivity(goSearch);
                searchViewHome.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_user_hire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_find:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_category) {
            setTitle(getResources().getString(R.string.categories));
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.containerUserHire, new FmCategory());
            transaction.commit();
        } else if (id == R.id.nav_inprogress) {
            setTitle(getResources().getString(R.string.work_in_progress));
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.containerUserHire, new FmJobInProgress());
            transaction.commit();
        } else if (id == R.id.nav_history) {
            setTitle(getResources().getString(R.string.history));
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.containerUserHire, new FmJobInHistory());
            transaction.commit();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
