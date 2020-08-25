package com.example.orientation.Attendance;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.orientation.R;
import com.example.orientation.model.SubjectData;

import java.util.List;

public class ManagerFragment extends Fragment {
    Button addsub;
    AttendanceAdapter adapter;
    public AttendViewModel viewModel;
    ProgressBar progressBar;
    TextView proset,overall;
    List<SubjectData> subjectDataList;

    public ManagerFragment() {
        // Required empty public constructor
    }

    public static ManagerFragment newInstance() {
        ManagerFragment fragment = new ManagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager, container, false);
        viewModel = new ViewModelProvider(this).get(AttendViewModel.class);
        fetch();
        addsub = (Button) view.findViewById(R.id.add);
        progressBar = (ProgressBar) view.findViewById(R.id.progress);
        proset = (TextView) view.findViewById(R.id.showProg);
        overall = (TextView) view.findViewById(R.id.overall);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.attrecycler);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        adapter = new AttendanceAdapter(viewModel,getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogClass dialogClass = new DialogClass(getContext(),viewModel);
                dialogClass.show();
            }
        });
    }

    private void fetch(){
        viewModel.getAllWords().observe(getViewLifecycleOwner(), data -> {
            // Update the cached copy
            adapter.setDatta(data);
            subjectDataList = data;
            set(data);
        });
    }

    public void set(List<SubjectData> data){
        int tot = 0;
        int att = 0;
        for (SubjectData subjectData : data) {
            tot += subjectData.getTotalClass();
            att += subjectData.getAttendnum();
        }
        Log.d("frag",""+tot);
        Log.d("frag",""+att);
        float per = ((float)att/tot)*100;
        String pershow = "" + (int)per + "%";
        progressBar.setProgress((int) per);
        proset.setText(pershow);
        overall.setText(pershow);
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            viewModel.delete(subjectDataList.get(pos));
        }
    };
}
