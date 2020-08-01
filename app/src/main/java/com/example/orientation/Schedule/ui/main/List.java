package com.example.orientation.Schedule.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orientation.Dbhelper.SchdDB;
import com.example.orientation.R;
import com.example.orientation.Schedule.DescAdapter;

public class List extends Fragment {
    private RecyclerView recyclerView;
    private DescAdapter descAdapter;
    Context context;
    String date;
    TextView dateset;

    public List(Context c,String d) {
        context = c;
        date = d;
        descAdapter = new DescAdapter(context,null);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_list,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycle);
         dateset = (TextView) view.findViewById(R.id.date);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String datei = "DATE:" + date + "/08/2020";
        dateset.setText(datei);
        recyclerView.setAdapter(descAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
        recyclerView.setLayoutManager(layoutManager);
        SchdDB db = new SchdDB(context);
        descAdapter.swapCursor(db.showData(date));
    }
}
