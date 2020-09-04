package com.example.orientation.Attendance;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orientation.R;
import com.example.orientation.model.SubjectData;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.ViewHolder> {

    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> bool = new ArrayList<>();
    Context context;

    public SubAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attcard, parent, false);
        return new SubAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dateshow.setText(date.get(position));
        String uri = "@drawable/tick";
        if (bool.get(position).equals("false"))
            uri = "@drawable/wrong";
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);
        holder.imgt.setImageDrawable(res);
    }

    void change(SubjectData subjectData) {
        date.clear();
        bool.clear();
        date.addAll(subjectData.getDates());
        bool.addAll(subjectData.getAttend());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return date.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateshow;
        ImageView imgt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateshow = (TextView) itemView.findViewById(R.id.date);
            imgt = (ImageView) itemView.findViewById(R.id.imgt);
        }
    }
}
