package com.example.orientation.Food;

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
import com.example.orientation.Dbhelper.FoodDB;
import com.example.orientation.Departments.DepartActivity;
import com.example.orientation.Features.FeatureActivity;
import com.example.orientation.R;
import com.example.orientation.Schedule.Sched_Activity;
import com.example.orientation.Settings.SettingsActivity;
import com.example.orientation.Sports.SportsActivity;
import com.example.orientation.model.Food;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

public class FoodActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mode = getPref();
        if(mode == null){
            setTheme(R.style.AppTheme);
        }
        else if(mode.equals("Dark")){
            setTheme(R.style.AppTheme);
        }
        else if(mode.equals("Light")){
            setTheme(R.style.lightTheme);
        }
        setContentView(R.layout.activity_food);
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.foodrecycle);
        FoodAdapter foodAdapter = new FoodAdapter(this, null);
        recyclerView.setAdapter(foodAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        FoodDB db = new FoodDB(this);
        foodAdapter.swapCursor(db.showData());
    }
    private String getPref(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("Mode", null);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.schd:
                i = new Intent(FoodActivity.this, Sched_Activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.depts:
                i = new Intent(FoodActivity.this, DepartActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.sports:
                i = new Intent(FoodActivity.this, SportsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.help:
                i = new Intent(FoodActivity.this, FeatureActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.settings:
                i = new Intent(FoodActivity.this, SettingsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                break;
            case R.id.attend:
                i = new Intent(FoodActivity.this, AttendanceActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        }
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
