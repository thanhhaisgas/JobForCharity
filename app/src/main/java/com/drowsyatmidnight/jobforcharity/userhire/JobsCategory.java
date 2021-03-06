package com.drowsyatmidnight.jobforcharity.userhire;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.drowsyatmidnight.jobforcharity.R;
import com.lapism.searchview.SearchView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class JobsCategory extends AppCompatActivity {

    @BindView(R.id.lvJobsCategory)
    RecyclerView lvJobsCategory;
    @BindView(R.id.txtJobNameCategory)
    TextView txtJobNameCategory;
    @BindView(R.id.txtJobCountCategory)
    TextView txtJobCountCategory;
    @BindView(R.id.imgJobCategory)
    ImageView imgJobCategory;
    @BindView(R.id.lnJobCatagory)
    LinearLayout lnJobCatagory;
    @BindView(R.id.toolbarJobsCategory)
    Toolbar toolbarJobsCategory;
    @BindView(R.id.bgActivityJobsCategory)
    CoordinatorLayout bgActivityJobsCategory;
    @BindView(R.id.searchViewCategory)
    SearchView searchViewCategory;
    private String NameCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_category);
        ButterKnife.bind(this);
        searchViewCategory.bringToFront();
        searchViewCategory.setVersion(1001);
        setUpView();
        setUpToolBar();
        setUpListJobs();
        addEvents();
    }

    private void addEvents() {
        searchViewCategory.setHint("Search job...");
        searchViewCategory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (getIntent().getStringExtra("searchType").compareTo(KeyValueFirebase.SEARCHTYPE_CATEGORY)==0){
                    DataFirebase.searchJobOrderCategory(NameCategory,lvJobsCategory,JobsCategory.this,JobsCategory.this, query, txtJobCountCategory);
                    searchViewCategory.close(true);
                }
                if (getIntent().getStringExtra("searchType").compareTo(KeyValueFirebase.SEARCHTYPE_ALL)==0){
                    DataFirebase.getAllJob(query,lvJobsCategory,JobsCategory.this,JobsCategory.this, txtJobCountCategory);
                    searchViewCategory.close(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setUpListJobs() {
        if (getIntent().getStringExtra("searchType").compareTo(KeyValueFirebase.SEARCHTYPE_CATEGORY)==0){
            DataFirebase.getJobsCategory(NameCategory,lvJobsCategory,this,JobsCategory.this, txtJobCountCategory);
        }
        if (getIntent().getStringExtra("searchType").compareTo(KeyValueFirebase.SEARCHTYPE_ALL)==0){
            DataFirebase.getAllJob(getIntent().getStringExtra("query"),lvJobsCategory,this,JobsCategory.this, txtJobCountCategory);
        }

    }

    private void setUpToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarJobsCategory.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_user_hire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_find:
                searchViewCategory.open(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpView() {
        if (getIntent().getStringExtra("searchType").compareTo(KeyValueFirebase.SEARCHTYPE_CATEGORY)==0){
            NameCategory = getIntent().getStringExtra("NameCategory");
            String categoryColor = getIntent().getStringExtra("categoryColor");
            int idImgCategory = getIntent().getIntExtra("idImgCategory", R.drawable.icon_category_home);
            int position = getIntent().getIntExtra("position", 0);
            List<String> categoryColorLight = Arrays.asList(getResources().getStringArray(R.array.list_light_color_category));
            List<String> categoryColorDark = Arrays.asList(getResources().getStringArray(R.array.list_dark_color_category));
            txtJobNameCategory.setText(NameCategory);
            imgJobCategory.setImageResource(idImgCategory);
            lnJobCatagory.setBackgroundColor(Color.parseColor(categoryColor));
            setSupportActionBar(toolbarJobsCategory);
            toolbarJobsCategory.setBackgroundColor(Color.parseColor(categoryColor));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.parseColor(categoryColorDark.get(position)));
            }
            bgActivityJobsCategory.setBackgroundColor(Color.parseColor(categoryColorLight.get(position)));
        }
        if (getIntent().getStringExtra("searchType").compareTo(KeyValueFirebase.SEARCHTYPE_ALL)==0){
            NameCategory = "Searching";
            setSupportActionBar(toolbarJobsCategory);
            txtJobNameCategory.setText(NameCategory);
            imgJobCategory.setImageResource(R.drawable.icon_category_search);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
