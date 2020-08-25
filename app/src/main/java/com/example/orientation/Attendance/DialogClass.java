package com.example.orientation.Attendance;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.orientation.R;
import com.example.orientation.model.SubjectData;

public class DialogClass extends Dialog implements android.view.View.OnClickListener {
    Button add;
    EditText subname;
    Context context;
    AttendViewModel viewModel;

    public String getName() {
        return name;
    }

    public String name;

    public DialogClass(@NonNull Context context,AttendViewModel viewModel) {
        super(context);
        this.context = context;
        this.viewModel = viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);
        subname = (EditText) findViewById(R.id.subjname);
        add = (Button) findViewById(R.id.addsub);
        add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addsub:
                //add subject
                if(TextUtils.isEmpty(subname.getText())){
                    Toast.makeText(context,"Enter Valid Data",Toast.LENGTH_SHORT).show();
                }
                else {
                    name = subname.getText().toString().toUpperCase();
                    SubjectData data = new SubjectData(name,0,0);
                    viewModel.insert(data);
                }
                break;
        }
        dismiss();
    }
}
