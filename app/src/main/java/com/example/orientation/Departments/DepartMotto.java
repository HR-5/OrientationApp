package com.example.orientation.Departments;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.orientation.Dbhelper.DepartDB;
import com.example.orientation.R;
import com.example.orientation.model.DepartTable;

import java.util.ArrayList;

public class DepartMotto extends AppCompatActivity {
    String lurl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.depart_detail);
        Bundle bundle = getIntent().getExtras();
        String departName = bundle.getString("name");
        ImageView img = (ImageView) findViewById(R.id.img);
        ImageView imgback = (ImageView) findViewById(R.id.mapback);
        TextView getdirect = (TextView) findViewById(R.id.direct);
        TextView nameset = (TextView) findViewById(R.id.name);
        TextView visionset = (TextView) findViewById(R.id.vision_set);
        DepartDB db = new DepartDB(this);
        Cursor cursor = db.showDatabyName(departName);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_NAME));
        String iurl = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_IURL));
        lurl = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_LURL));
        String desc = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_DESC));
        String[] descr = desc.split("\r\n\r\n");
        String[] list = descr[1].split("\r\n");
        String vision = descr[0], mission = descr[1];
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.ll);
        DepartmoAdapter departmoAdapter = new DepartmoAdapter();
        recyclerView.setAdapter(departmoAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        departmoAdapter.notify(list);
        nameset.setText(name);
        String uri = "@drawable/" + iurl;
        int imageResource = this.getResources().getIdentifier(uri, null, this.getPackageName());
        Drawable res = this.getResources().getDrawable(imageResource);
        img.setBackground(res);
        visionset.setText(vision);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct(null);
            }
        });
        getdirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct(null);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    public void direct(View view){
        Uri uri = Uri.parse(lurl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        this.startActivity(intent);
    }
}
