package com.drowsyatmidnight.jobforcharity.userhire;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.TabsJobDetailPagerAdapter;
import com.drowsyatmidnight.jobforcharity.userhire.fragment_item.FmJobDetail;
import com.drowsyatmidnight.jobforcharity.userhire.fragment_item.FmReviews;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private TabsJobDetailPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        ButterKnife.bind(this);
        setUpView();
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
        DataFirebase.getUserInfo(getIntent().getStringExtra("workerUID"),txtTenDetail,txtPhoneNumDetail,txtEmailDetail,rateBar,txtCountRate);
        txtCountRate.setText(txtCountRate.getText()+" "+getResources().getString(R.string.reviews));
        setSupportActionBar(toolbarJobDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarJobDetail.bringToFront();
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
                    collapsingToolbarDetail.setTitle(getString(R.string.job_detail));
                    collapsingToolbarDetail.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
                }else {
                    Log.d("test", String.valueOf(verticalOffset));
                    imgBackground.setVisibility(View.VISIBLE);
                    profile_image.setVisibility(View.VISIBLE);
                    txtTenDetail.setVisibility(View.VISIBLE);
                    txtPhoneNumDetail.setVisibility(View.VISIBLE);
                    txtEmailDetail.setVisibility(View.VISIBLE);
                    rateBar.setVisibility(View.VISIBLE);
                    txtCountRate.setVisibility(View.VISIBLE);
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
