package com.example.orientation.Schedule;

import android.content.Intent;
import android.os.Bundle;

import com.example.orientation.Departments.DepartActivity;
import com.example.orientation.Features.FeatureActivity;
import com.example.orientation.Food.FoodActivity;
import com.example.orientation.R;
import com.example.orientation.Sports.SportsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;

import com.example.orientation.Schedule.ui.main.SectionsPagerAdapter;

public class Sched_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sched_);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView =findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setActionView(R.layout.arrow);
        navigationView.getMenu().getItem(1).setActionView(R.layout.arrow);
        navigationView.getMenu().getItem(2).setActionView(R.layout.arrow);
        navigationView.getMenu().getItem(3).setActionView(R.layout.arrow);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.food:
                i = new Intent(Sched_Activity.this, FoodActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.depts:
                i = new Intent(Sched_Activity.this, DepartActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.sports:
                i = new Intent(Sched_Activity.this, SportsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
            case R.id.help:
                i = new Intent(Sched_Activity.this, FeatureActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}