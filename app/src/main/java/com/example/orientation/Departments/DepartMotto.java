package com.example.orientation.Departments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.orientation.Dbhelper.DepartDB;
import com.example.orientation.R;
import com.example.orientation.Schedule.Maps;
import com.example.orientation.model.DepartTable;

import java.util.ArrayList;

public class DepartMotto extends AppCompatActivity {
    String lurl;
    Context context;

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
        setContentView(R.layout.depart_detail);
        Bundle bundle = getIntent().getExtras();
        String departName = bundle.getString("name");
        ImageView imgback = (ImageView) findViewById(R.id.imageButton);
        TextView getdirect = (TextView) findViewById(R.id.direct);
        TextView nameset = (TextView) findViewById(R.id.name);
        TextView visionset = (TextView) findViewById(R.id.vision_set);
        DepartDB db = new DepartDB(this);
        Cursor cursor = db.showDatabyName(departName);
        cursor.moveToFirst();
        context = getApplicationContext();
        String name = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_NAME));
        String iurl = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_IURL));
        lurl = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_LURL));
        String desc = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_DESC));
        String[] descr = desc.split("\r\n\r\n");
        String[] list = descr[1].split("\r\n");
        String vision = descr[0], mission = descr[1];
        setAdapter(list);
        nameset.setText(name);
        setImage(iurl);
        visionset.setText(vision);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct();
            }
        });
        getdirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct();
            }
        });
    }

    private void setImage(String iurl){
        final ImageView img = (ImageView) findViewById(R.id.img);
        Glide.with(this).asBitmap().load(iurl).into(new SimpleTarget<Bitmap>(300, 300) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                Drawable drawable = new BitmapDrawable(getResources(), resource);
                img.setBackground(drawable);
            }
        });
    }

    private void setAdapter(String[] list){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ll);
        DepartmoAdapter departmoAdapter = new DepartmoAdapter();
        recyclerView.setAdapter(departmoAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        departmoAdapter.notify(list);
    }

    private String getPref(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("Mode", null);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, DepartActivity.class);
        ImageView img = (ImageView) findViewById(R.id.img);
        TextView nameset = (TextView) findViewById(R.id.name);
        Pair[] pair = new Pair[2];
        pair[0] = Pair.create((View) nameset, "name");
        pair[1] = Pair.create((View) img, "imageShare");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair);
        startActivity(intent);
    }

    public void direct() {
        Uri uri = Uri.parse(lurl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }

}
