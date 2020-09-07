package com.example.orientation.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.orientation.API.GetDataService;
import com.example.orientation.Dbhelper.DepartDB;
import com.example.orientation.Dbhelper.FoodDB;
import com.example.orientation.Dbhelper.SchdDB;
import com.example.orientation.Dbhelper.SportsDB;
import com.example.orientation.Login.LoginActivity;
import com.example.orientation.R;
import com.example.orientation.Schedule.Sched_Activity;
import com.example.orientation.Settings.Notificationsetter;
import com.example.orientation.model.DepartTable;
import com.example.orientation.model.Department;
import com.example.orientation.model.Event;
import com.example.orientation.model.Food;
import com.example.orientation.model.FoodTable;
import com.example.orientation.model.SchdTable;
import com.example.orientation.model.DetailResponse;
import com.example.orientation.model.Schedule;
import com.example.orientation.model.Sports;
import com.example.orientation.model.SportsTable;
import com.example.orientation.network.RetrofitClientInstance;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase mDatabase;
    Context context;
    ArrayList<Schedule> schedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ImageView iv = (ImageView) findViewById(R.id.imageView);
        final TextView title = (TextView) findViewById(R.id.title);
        title.setVisibility(View.INVISIBLE);
        if (isConnected()) {
            YoYo.with(Techniques.Landing)
                    .duration(2000)
                    .playOn(iv);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    title.setVisibility(View.VISIBLE);
                    anim(title);

                }
            },1000);
            call();
        } else {
            Toast.makeText(this, "Failed to Update: Check Internet Connection", Toast.LENGTH_SHORT).show();
            callSchedule();
        }
    }
    private void anim(TextView title){
        YoYo.with(Techniques.FlipInX)
                .duration(1500)
                .playOn(title);
    }


    public void call() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<DetailResponse> detailcall = service.listdetail();
        detailcall.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                DetailResponse detailResponse = response.body();
                schedules = detailResponse.getSchedule();
                ArrayList<Department> departments = detailResponse.getDepartment();
                ArrayList<Sports> sports = detailResponse.getSports();
                ArrayList<Food> foods = detailResponse.getFood();
                clearTable();
                addtable(schedules, departments, sports, foods);
                if(new Notificationsetter().checkNotification(getPref()))
                    new SetRemainder(context);
                callSchedule();
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                finishAffinity();

            }
        });
    }
    private String getPref() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("Notification", null);
    }

    private void addtable(ArrayList<Schedule> schedules, ArrayList<Department> departments, ArrayList<Sports> sports, ArrayList<Food> foods) {
        ContentValues cv = new ContentValues();
        ContentValues cv1 = new ContentValues();
        ContentValues cv2 = new ContentValues();
        ContentValues cv3 = new ContentValues();
        final SchdDB dbHelper = new SchdDB(context);
        mDatabase = dbHelper.getWritableDatabase();
        for (int i = 0; i < schedules.size(); i++) {
            for (int j = 0; j < schedules.get(i).getEvents().size(); j++) {
                Event eve = schedules.get(i).getEvents().get(j);
                String time = eve.getStime() + "-" + eve.getEtime();
                cv.put(SchdTable.SchdEntry.COLUMN_EVENT, eve.getName());
                cv.put(SchdTable.SchdEntry.COLUMN_TIME, time);
                cv.put(SchdTable.SchdEntry.COLUMN_DESC, eve.getDescription());
                cv.put(SchdTable.SchdEntry.COLUMN_LOC, eve.getLocation());
                cv.put(SchdTable.SchdEntry.COLUMN_LURL, eve.getLocurl());
                cv.put(SchdTable.SchdEntry.COLUMN_IMG, eve.getimgname());
                cv.put(SchdTable.SchdEntry.COLUMN_DATE, schedules.get(i).getDate());
                cv.put(SchdTable.SchdEntry.COLUMN_LAT, eve.getLatitude());
                cv.put(SchdTable.SchdEntry.COLUMN_LONG, eve.getLongitude());
                mDatabase.insert(SchdTable.SchdEntry.TABLE_NAME, null, cv);
            }
        }
        final DepartDB dbHelper1 = new DepartDB(context);
        mDatabase = dbHelper1.getWritableDatabase();
        for (int i = 0; i < departments.size(); i++) {
            Department department = departments.get(i);
            cv1.put(DepartTable.DepartEntry.COLUMN_NAME, department.getName());
            cv1.put(DepartTable.DepartEntry.COLUMN_LURL, department.getLocurl());
            cv1.put(DepartTable.DepartEntry.COLUMN_IURL, department.getImgurl());
            cv1.put(DepartTable.DepartEntry.COLUMN_DESC, department.getDescription());
            cv1.put(DepartTable.DepartEntry.COLUMN_LAT, department.getLatitude());
            cv1.put(DepartTable.DepartEntry.COLUMN_LONG, department.getLongitude());
            mDatabase.insert(DepartTable.DepartEntry.TABLE_NAME, null, cv1);
        }
        final SportsDB dbHelper2 = new SportsDB(context);
        mDatabase = dbHelper2.getWritableDatabase();
        for (int i = 0; i < sports.size(); i++) {
            Sports sp = sports.get(i);
            cv2.put(SportsTable.SportsEntry.COLUMN_NAME, sp.getName());
            cv2.put(SportsTable.SportsEntry.COLUMN_LURL, sp.getLocurl());
            cv2.put(SportsTable.SportsEntry.COLUMN_IURL, sp.getImgurl());
            cv2.put(SportsTable.SportsEntry.COLUMN_DESC, sp.getDescription());
            cv2.put(SportsTable.SportsEntry.COLUMN_LAT, sp.getLatitude());
            cv2.put(SportsTable.SportsEntry.COLUMN_LONG, sp.getLongitude());
            mDatabase.insert(SportsTable.SportsEntry.TABLE_NAME, null, cv2);
        }
        final FoodDB dbHelper3 = new FoodDB(context);
        mDatabase = dbHelper3.getWritableDatabase();
        for (int i = 0; i < foods.size(); i++) {
            Food food = foods.get(i);
            cv3.put(FoodTable.FoodEntry.COLUMN_NAME, food.getName());
            cv3.put(FoodTable.FoodEntry.COLUMN_LURL, food.getLocurl());
            cv3.put(FoodTable.FoodEntry.COLUMN_IURL, food.getImgurl());
            cv3.put(FoodTable.FoodEntry.COLUMN_DESC, food.getDescription());
            cv3.put(FoodTable.FoodEntry.COLUMN_LAT, food.getLatitude());
            cv3.put(FoodTable.FoodEntry.COLUMN_LONG, food.getLongitude());
            mDatabase.insert(FoodTable.FoodEntry.TABLE_NAME, null, cv3);
        }
    }

    private void callSchedule() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    private void clearTable() {
        this.deleteDatabase("schd.db");
        this.deleteDatabase("food.db");
        this.deleteDatabase("depart.db");
        this.deleteDatabase("sports.db");
    }

    public boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}
