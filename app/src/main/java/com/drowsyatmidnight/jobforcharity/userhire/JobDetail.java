package com.drowsyatmidnight.jobforcharity.userhire;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.drowsyatmidnight.jobforcharity.userhire.adapter.TabsJobDetailPagerAdapter;
import com.drowsyatmidnight.jobforcharity.userhire.fragment_item.FmJobDetail;
import com.drowsyatmidnight.jobforcharity.userhire.fragment_item.FmUserReview;
import com.drowsyatmidnight.jobforcharity.woker.View.Utils.DataFirebase;

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

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imgBackground.setColorFilter(filter);
        profile_image.setImageResource(R.drawable.test);
        imgBackground.setImageResource(R.drawable.test);
        DataFirebase.getUserInfo(txtTenDetail,txtPhoneNumDetail,txtEmailDetail);
        rateBar.setRating(4.5f);
        txtCountRate.setText("(177 reviews)");

        setSupportActionBar(toolbarJobDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setText(R.string.details);
        tabs.getTabAt(1).setText(R.string.reviews);
    }

    private void setupViewPager(ViewPager vpJobDetail) {
        adapter = new TabsJobDetailPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FmJobDetail().newInstance(getIntent().getStringExtra("workName"), getIntent().getStringExtra("description"), getIntent().getSerializableExtra("shiftWorks")));
        adapter.addFragment(new FmUserReview());
        vpJobDetail.setAdapter(adapter);
    }
}
