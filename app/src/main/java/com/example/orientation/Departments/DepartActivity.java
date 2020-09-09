package com.example.orientation.Departments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import com.example.orientation.Attendance.AttendanceActivity;
import com.example.orientation.Dbhelper.DepartDB;
import com.example.orientation.Features.FeatureActivity;
import com.example.orientation.Food.FoodActivity;
import com.example.orientation.R;
import com.example.orientation.Schedule.Sched_Activity;
import com.example.orientation.Settings.SettingsActivity;
import com.example.orientation.Sports.SportsActivity;
import com.example.orientation.model.Department;
import com.google.android.material.navigation.NavigationView;

public class DepartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPref();
        setContentView(R.layout.activity_depart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setActionView(R.layout.arrow);
        navigationView.getMenu().getItem(1).setActionView(R.layout.arrow);
        navigationView.getMenu().getItem(2).setActionView(R.layout.arrow);
        navigationView.getMenu().getItem(3).setActionView(R.layout.arrow);
        navigationView.getMenu().getItem(4).setActionView(R.layout.arrow);
        navigationView.getMenu().getItem(4).setActionView(R.layout.arrow);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.departrecycle);
        DepartAdapter departAdapter = new DepartAdapter(this, null,this);
        recyclerView.setAdapter(departAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        DepartDB db = new DepartDB(this);
        departAdapter.swapCursor(db.showData());
    }

    private void getPref() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String mode = preferences.getString("Mode", null);
        if (mode == null) {
            setTheme(R.style.AppTheme);
        } else if (mode.equals("Dark")) {
            setTheme(R.style.AppTheme);
        } else if (mode.equals("Light")) {
            setTheme(R.style.lightTheme);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.food:
                i = new Intent(DepartActivity.this, FoodActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.schd:
                i = new Intent(DepartActivity.this, Sched_Activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.sports:
                i = new Intent(DepartActivity.this, SportsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.help:
                i = new Intent(DepartActivity.this, FeatureActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.settings:
                i = new Intent(DepartActivity.this, SettingsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.attend:
                i = new Intent(DepartActivity.this, AttendanceActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        }
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
