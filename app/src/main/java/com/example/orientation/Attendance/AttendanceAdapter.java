package com.example.orientation.Attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orientation.R;
import com.example.orientation.model.SubjectData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder>{

    List<SubjectData> data;
    AttendViewModel viewModel;
    Context mc;
    ArrayList<String> d = new ArrayList<>();
    ArrayList<String> attendance = new ArrayList<>();



    public AttendanceAdapter(AttendViewModel viewModel, Context context) {
        this.viewModel = viewModel;
        mc = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_card, parent, false);
        return new AttendanceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String sub = data.get(position).getSubjectName();
        holder.subjectData = data.get(position);
        holder.name.setText(sub);
        int tot = data.get(position).getTotalClass();
        int att = data.get(position).getAttendnum();
        String attend = "" + att +"/" + tot;
        float per = ((float)att/tot)*100;
        String pershow = "" + (int)per + "%";
        holder.progressBar.setProgress((int) per);
        holder.show.setText(pershow);
        holder.attend.setText(attend);
        holder.yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                good(sub,tot,att,position);
            }
        });
        holder.no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bad(sub,tot,att,position);
            }
        });
        holder.graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStats(sub);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(data!=null)
            return data.size();
        return 0;
    }

    private void showStats(String sub){
        Async async = new Async(sub,viewModel.application,mc);
        async.execute();
    }

    private void good(String sub,int tot,int att,int pos){
        tot++;
        att++;
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy");;
        String date;
        Calendar calendar = Calendar.getInstance();
        date = dateFormat.format(calendar.getTime());
        d.clear();
        d.addAll(data.get(pos).getDates());
        d.add(date);
        attendance.clear();
        attendance.addAll(data.get(pos).getAttend());
        attendance.add("true");
        SubjectData value = new SubjectData(sub,tot,att,d,attendance);
        viewModel.update(value);
    }

    private void bad(String sub,int tot,int att,int pos){
        tot++;
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy");;
        String date;
        Calendar calendar = Calendar.getInstance();
        date = dateFormat.format(calendar.getTime());
        d.clear();
        d.addAll(data.get(pos).getDates());
        d.add(date);
        attendance.clear();
        attendance.addAll(data.get(pos).getAttend());
        attendance.add("false");
        SubjectData value = new SubjectData(sub,tot,att,d,attendance);
        viewModel.update(value);
    }

    public void setDatta(List<SubjectData> d){
        data = d;
        notifyDataSetChanged();
    }

    public SubjectData get(int pos){
        SubjectData subjectData = data.get(pos);
        return subjectData;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name,show;
        private TextView attend;
        ImageView yes,no,graph;
        ProgressBar progressBar;
        public SubjectData subjectData;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.course);
            attend = (TextView) itemView.findViewById(R.id.attend);
            yes = (ImageView) itemView.findViewById(R.id.yes);
            no = (ImageView) itemView.findViewById(R.id.no);
            graph = (ImageView) itemView.findViewById(R.id.graph);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressSet);
            show = (TextView) itemView.findViewById(R.id.showProg);
            subjectData = new SubjectData("dummy",0,0,d,attendance);
        }
    }
}
