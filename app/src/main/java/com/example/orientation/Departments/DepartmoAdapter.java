package com.example.orientation.Departments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orientation.R;

public class DepartmoAdapter extends RecyclerView.Adapter<DepartmoAdapter.ViewHolder> {
    String[] deta;

    public DepartmoAdapter() {
        deta = new String[4];
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new DepartmoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.detail.setText(deta[position]);
    }

    @Override
    public int getItemCount() {
        return deta.length;
    }

    public void notify(String[] details) {
        deta = details;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detail = (TextView) itemView.findViewById(R.id.deta);
        }
    }
}
