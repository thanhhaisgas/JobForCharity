package com.drowsyatmidnight.jobforcharity.userhire;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.model.User_Model;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.TabsJobDetailPagerAdapter;
import com.drowsyatmidnight.jobforcharity.userhire.fragment_item.FmJobDetail;
import com.drowsyatmidnight.jobforcharity.userhire.fragment_item.FmReviews;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class JobDetail extends AppCompatActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vpJobDetail)
    ViewPager vpJobDetail;
    @BindView(R.id.toolbarJobDetail)
    Toolbar toolbarJobDetail;
    @BindView(R.id.imgBackground)
    ImageView imgBackground;
    @BindView(R.id.profile_image)
    ImageView profile_image;
    @BindView(R.id.txtTenDetail)
    TextView txtTenDetail;
    @BindView(R.id.txtPhoneNumDetail)
    TextView txtPhoneNumDetail;
    @BindView(R.id.txtEmailDetail)
    TextView txtEmailDetail;
    @BindView(R.id.rateBar)
    RatingBar rateBar;
    @BindView(R.id.txtCountRate)
    TextView txtCountRate;
    @BindView(R.id.appBarJobDetail)
    AppBarLayout appBarJobDetail;
    @BindView(R.id.collapsingToolbarDetail)
    CollapsingToolbarLayout collapsingToolbarDetail;
    @BindView(R.id.fabButtonDetail)
    FabSpeedDial fabButtonDetail;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.imgBlackTrans)
    ImageView imgBlackTrans;

    private TabsJobDetailPagerAdapter adapter;
    private OnMenuFabSelected onMenuFabSelected;
    private Intent call_message;

    public interface OnMenuFabSelected {
        void onSelected(int i);
    }

    public void setOnMenuFabSelected(OnMenuFabSelected onMenuFabSelected) {
        this.onMenuFabSelected = onMenuFabSelected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
        setUpView();
        addEvents();
    }

    private void setUpView() {
        setupViewPager(vpJobDetail);
        tabs.setupWithViewPager(vpJobDetail);
        setupTabIcons();
        setupAppBar();

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imgBackground.setColorFilter(filter);
        profile_image.setImageResource(R.drawable.test);
        imgBackground.setImageResource(R.drawable.test);
        DataFirebase.getUserInfo(getIntent().getStringExtra("workerUID"), new DataFirebase.OnGetDataListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(DataSnapshot data) {
                User_Model user_model = data.getValue(User_Model.class);
                txtTenDetail.setText(user_model.getLName() + " " + user_model.getFName());
                txtPhoneNumDetail.setText(user_model.getMobilePhone());
                txtEmailDetail.setText(user_model.getEmail());
                rateBar.setRating(Float.parseFloat(user_model.getRate())/(Float.parseFloat(user_model.getCountRate())));
                txtCountRate.setText(user_model.getCountRate() + " reviews");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
        Log.d("sdt", txtTenDetail.getText().toString());
        setSupportActionBar(toolbarJobDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarJobDetail.bringToFront();

    }

    private void addEvents() {
        fabButtonDetail.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                if (getIntent().getStringExtra("view_type").compareTo(KeyValueFirebase.VIEW_JOBDETAILS) == 0) {
                    navigationMenu.findItem(R.id.option_done).setVisible(false);
                    navigationMenu.findItem(R.id.option_cancel).setVisible(false);
                }
                if (getIntent().getStringExtra("view_type").compareTo(KeyValueFirebase.VIEW_JOBINPROGRESS) == 0) {
                    navigationMenu.findItem(R.id.option_hire).setVisible(false);
                }
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                int itemid = menuItem.getItemId();
                switch (itemid) {
                    case R.id.option_call:
                        DataFirebase.getUserInfo(getIntent().getStringExtra("workerUID"), new DataFirebase.OnGetDataListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(DataSnapshot data) {
                                User_Model user_model = data.getValue(User_Model.class);
                                call_message = new Intent(Intent.ACTION_CALL);
                                call_message.setData(Uri.parse("tel:" + user_model.getMobilePhone()));
                                if (ActivityCompat.checkSelfPermission(JobDetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(JobDetail.this, new String[]{Manifest.permission.CALL_PHONE},1);
                                }else{
                                    startActivity(call_message);
                                }
                            }

                            @Override
                            public void onFailed(DatabaseError databaseError) {

                            }
                        });
                        break;
                    case R.id.option_text:
                        DataFirebase.getUserInfo(getIntent().getStringExtra("workerUID"), new DataFirebase.OnGetDataListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(DataSnapshot data) {
                                User_Model user_model = data.getValue(User_Model.class);
                                call_message = new Intent(Intent.ACTION_VIEW, Uri.parse( "sms:" + user_model.getMobilePhone()));
                                startActivity(call_message);
                            }

                            @Override
                            public void onFailed(DatabaseError databaseError) {

                            }
                        });
                        break;
                    case R.id.option_hire:
                        onMenuFabSelected.onSelected(1);
                        break;
                    case R.id.option_done:
                        onMenuFabSelected.onSelected(2);
                        break;
                    case R.id.option_cancel:
                        onMenuFabSelected.onSelected(3);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(call_message);
                } else {
                    Toast.makeText(JobDetail.this, "Permission denied to to call_message your employee", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    private void setupAppBar() {
        appBarJobDetail.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset<=-635){
                    imgBackground.setVisibility(View.GONE);
                    profile_image.setVisibility(View.GONE);
                    txtTenDetail.setVisibility(View.GONE);
                    txtPhoneNumDetail.setVisibility(View.GONE);
                    txtEmailDetail.setVisibility(View.GONE);
                    rateBar.setVisibility(View.GONE);
                    txtCountRate.setVisibility(View.GONE);
                    //imgBlackTrans.setBackgroundColor(Color.TRANSPARENT);
                    collapsingToolbarDetail.setTitle(getString(R.string.job_detail));
                    collapsingToolbarDetail.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
                }else {
                    imgBackground.setVisibility(View.VISIBLE);
                    profile_image.setVisibility(View.VISIBLE);
                    txtTenDetail.setVisibility(View.VISIBLE);
                    txtPhoneNumDetail.setVisibility(View.VISIBLE);
                    txtEmailDetail.setVisibility(View.VISIBLE);
                    rateBar.setVisibility(View.VISIBLE);
                    txtCountRate.setVisibility(View.VISIBLE);
                    //imgBlackTrans.setBackgroundColor(getResources().getColor(R.color.verydark_transparent));
                    collapsingToolbarDetail.setTitle("");
                }
            }
        });
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setText(R.string.details);
        tabs.getTabAt(1).setText(R.string.reviews);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemid = item.getItemId();
        switch (itemid){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void setupViewPager(final ViewPager vpJobDetail) {
        adapter = new TabsJobDetailPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FmJobDetail().newInstance(getIntent().getStringExtra("workName"), getIntent().getStringExtra("description"),
                getIntent().getSerializableExtra("shiftWorks"), getIntent().getStringExtra("JobID"),getIntent().getStringExtra("category"), getIntent().getStringExtra("view_type"), getIntent().getStringExtra("workerID")));
        adapter.addFragment(new FmReviews().newInstance(getIntent().getStringExtra("workerUID")));
        vpJobDetail.setAdapter(adapter);
    }
}
