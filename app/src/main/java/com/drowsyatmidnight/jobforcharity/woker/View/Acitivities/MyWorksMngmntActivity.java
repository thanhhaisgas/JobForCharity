package com.drowsyatmidnight.jobforcharity.woker.View.Acitivities;

import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.login.Authority;
import com.drowsyatmidnight.jobforcharity.woker.View.Adapters.CustomPagerAdapter;
import com.drowsyatmidnight.jobforcharity.woker.View.Fragments.AllNeededWorksFragment;
import com.drowsyatmidnight.jobforcharity.woker.View.Fragments.CreateWorkBottomSheetDialogFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by davidtran on 7/23/17.
 */

public class MyWorksMngmntActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final int ALL_WORK_VIEW_MODE = 2;
    final int FEATURE_WORK_VIEW_MODE = 1;
    final int PROGRESSING_WORK_VIEW_MODE = 0;
    final int HISTORY_VIEW_MODE = -1;
    @BindView(R.id.nav_view_worker)
    NavigationView navViewWorker;

    @BindView(R.id.tvViewMode)
    TextView tvViewMode;

    @BindView(R.id.imgWorker)
    CircleImageView imgWorker;
    @BindView(R.id.mywork_container)
    FrameLayout myword_container;
    @BindView(R.id.fab_addJob)
    android.support.design.widget.FloatingActionButton fab_addJob;
    CreateWorkBottomSheetDialogFragment bottomSheetDialogFragment = new CreateWorkBottomSheetDialogFragment();
    String workerUID = "";
    String workerPhotoURL = "";

    FragmentManager manager = getSupportFragmentManager();


    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    int icon[];

    CustomPagerAdapter customPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_worker);
        ButterKnife.bind(this);
        icon = new int[]{
                R.drawable.ic_list_white_24dp,
                R.drawable.ic_playlist_play_white_24dp,
                R.drawable.ic_playlist_finished_white_24dp,
                R.drawable.ic_failed_white_24dp};

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_worker);
        navigationView.setNavigationItemSelectedListener(this);


        loadWorkerInfo();
        setupAllWorkFragment();
        setupListener();
        //setupTabLayout();
    }

    private void loadWorkerInfo() {
        workerUID = Authority.sFirebaseAuth.getCurrentUser().getUid();
        if (Authority.sFirebaseAuth.getCurrentUser().getPhotoUrl() != null) {
            Glide.with(MyWorksMngmntActivity.this)
                    .load(Authority.sFirebaseAuth.getCurrentUser().getPhotoUrl())
                    .into(imgWorker);
        }
    }

    private void setupListener() {

        //final Intent mIntent = new Intent(getApplicationContext(),CreateWorkActivity.class);
        fab_addJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //show it
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
    }

    private void setupAllWorkFragment() {
        Fragment mAllNeededWorksFragment = AllNeededWorksFragment.newInstance(ALL_WORK_VIEW_MODE);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mywork_container, mAllNeededWorksFragment);
        transaction.commit();
    }

    private void setupProccessingWorkFragment() {
        Fragment mAllNeededWorksFragment = AllNeededWorksFragment.newInstance(PROGRESSING_WORK_VIEW_MODE);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mywork_container, mAllNeededWorksFragment);
        transaction.commit();
    }

    private void setupFutureWorkFragment() {
        Fragment mAllNeededWorksFragment = AllNeededWorksFragment.newInstance(FEATURE_WORK_VIEW_MODE);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mywork_container, mAllNeededWorksFragment);
        transaction.commit();
    }

    private void setupHistoryWorkFragment() {
        Fragment mAllNeededWorksFragment = AllNeededWorksFragment.newInstance(HISTORY_VIEW_MODE);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mywork_container, mAllNeededWorksFragment);
        transaction.commit();
    }

    /*@Override
    public void onDateTimeChanged(Work work, ShiftWork_Model shiftWork_model) {
        *//*if(shiftWork_model==null){
            Toast.makeText(this,"Null work",Toast.LENGTH_SHORT);
        }else{
            showNotificationWhoGotWork(work,shiftWork_model);
        }*//*

    }
    *//*private void showNotificationWhoGotWork(Work work, ShiftWork_Model shiftWork_model){
        AlertDialog.Builder builder;

            builder = new AlertDialog.Builder(MyWorksMngmntActivity.this);

        builder.setTitle("Notification")
                .setMessage(work.getWorkName() + "has hired by " + shiftWork_model.getHirerUID())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }*//*


    *//*private void setupTabLayout() {
        fragmentArrayList.add(mAllNeededWorksFragment);
        fragmentArrayList.add(mPerformingNeededWorksFragment);
        fragmentArrayList.add(mFinishedNeededWorksFragment);
        fragmentArrayList.add(mExpiredNeededWorksFragment);

        customPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        viewPager.setAdapter(customPagerAdapter);

        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < 4; i++) {
            tabLayout.getTabAt(i).setIcon(icon[i]);
        }
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawerwork, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.all_work_item_menu) {
            Toast.makeText(this,"fsdfgsdgdf",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all_work_item_menu) {
            Toast.makeText(this,"fsdfgsdgdf",Toast.LENGTH_SHORT).show();
            tvViewMode.setText(R.string.all_work_label);
            setupAllWorkFragment();
        } else if (id == R.id.feature_works_item_menu) {
            tvViewMode.setText(R.string.future_work_label);
            setupFutureWorkFragment();
        } else if (id == R.id.proccessing_works_item_menu) {
            tvViewMode.setText(R.string.progressing_work_label);
            setupProccessingWorkFragment();
        } else if (id == R.id.history_item_menu) {
            tvViewMode.setText(R.string.history_work_label);
            setupHistoryWorkFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
