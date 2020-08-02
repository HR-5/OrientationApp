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
import android.os.Bundle;
import android.view.MenuItem;

import com.example.orientation.Dbhelper.DepartDB;
import com.example.orientation.Dbhelper.FoodDB;
import com.example.orientation.Food.FoodActivity;
import com.example.orientation.Food.FoodAdapter;
import com.example.orientation.R;
import com.example.orientation.Schedule.Sched_Activity;
import com.example.orientation.Sports.SportsActivity;
import com.google.android.material.navigation.NavigationView;

public class DepartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart);
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

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.departrecycle);
        DepartAdapter departAdapter = new DepartAdapter(this,null);
        recyclerView.setAdapter(departAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        DepartDB db = new DepartDB(this);
        departAdapter.swapCursor(db.showData());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.food:
                i = new Intent(DepartActivity.this, FoodActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up,R.anim.stay);
                break;
            case R.id.schd:
                i = new Intent(DepartActivity.this, Sched_Activity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up,R.anim.stay);
                break;
            case R.id.sports:
                i = new Intent(DepartActivity.this, SportsActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_up,R.anim.stay);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
