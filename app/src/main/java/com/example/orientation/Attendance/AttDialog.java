package com.example.orientation.Attendance;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orientation.R;
import com.example.orientation.model.SubjectData;

public class AttDialog extends Dialog {

    TextView sub;
    RecyclerView recyclerView;
    SubAdapter subAdapter;
    public SubjectData subjectData;
    Context context;

    public void setSubjectData(SubjectData subjectData) {
        this.subjectData = subjectData;
    }

    public AttDialog(@NonNull Context context, SubjectData subjectData) {
        super(context);
        this.subjectData = subjectData;
        this.context = context;
    }

    public AttDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_history);
        sub = findViewById(R.id.sub);
        recyclerView = (RecyclerView) findViewById(R.id.attre);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        subAdapter = new SubAdapter(context);
        recyclerView.setAdapter(subAdapter);
        sub.setText(subjectData.getSubjectName());
        subAdapter.change(subjectData);
    }
}
