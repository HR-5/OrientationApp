package com.example.orientation.Schedule;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orientation.R;
import com.example.orientation.model.SchdTable;

import java.util.ArrayList;

public class DescAdapter extends RecyclerView.Adapter<DescAdapter.ViewHolder> {
    private Context context;
    Cursor cursor;
    ArrayList<Boolean> show;
    public DescAdapter(Context c, Cursor mcursor) {
        context = c;
        cursor = mcursor;
        show = new ArrayList<Boolean>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.descp, parent, false);
        return new DescAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        show.add(position,false);
        String event = cursor.getString(cursor.getColumnIndex(SchdTable.SchdEntry.COLUMN_EVENT));
        String time = cursor.getString(cursor.getColumnIndex(SchdTable.SchdEntry.COLUMN_TIME));
        String des = cursor.getString(cursor.getColumnIndex(SchdTable.SchdEntry.COLUMN_DESC));
        String loc = cursor.getString(cursor.getColumnIndex(SchdTable.SchdEntry.COLUMN_LOC));
        final String lurl = cursor.getString(cursor.getColumnIndex(SchdTable.SchdEntry.COLUMN_LURL));

        holder.eveset.setText(event);
        holder.timeset.setText(time);
        holder.desset.setText(des);
        holder.locset.setText(loc);
        holder.dir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct(lurl);
            }
        });
        holder.loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct(lurl);
            }
        });
        holder.locset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct(lurl);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
    public void direct(String lurl){
        Uri uri = Uri.parse(lurl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView date;
        private TextView eveset;
        private TextView locset;
        private ImageView loc;
        private TextView timeset;
        private TextView des;
        private TextView desset;
        private ImageButton dir;
        private CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            eveset = (TextView) itemView.findViewById(R.id.eventset);
            locset = (TextView) itemView.findViewById(R.id.locset);
            loc = (ImageView) itemView.findViewById(R.id.locimg);
            timeset = (TextView) itemView.findViewById(R.id.timeset);
            des = (TextView) itemView.findViewById(R.id.des);
            desset = (TextView) itemView.findViewById(R.id.descset);
            dir = (ImageButton) itemView.findViewById(R.id.location);
            card = (CardView) itemView.findViewById(R.id.card);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Boolean isShow = show.get(position);
                    if(isShow){
                        show.add(position,false);
                        ((CardView)view).findViewById(R.id.des).setVisibility(View.GONE);
                        ((CardView)view).findViewById(R.id.descset).setVisibility(View.GONE);
                        ((CardView)view).findViewById(R.id.location).setVisibility(View.GONE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            TransitionManager.beginDelayedTransition(card,new AutoTransition());
                        }
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            TransitionManager.beginDelayedTransition(card,new AutoTransition());
                        }
                        show.add(position,true);
                        ((CardView)view).findViewById(R.id.des).setVisibility(View.VISIBLE);
                        ((CardView)view).findViewById(R.id.descset).setVisibility(View.VISIBLE);
                        ((CardView)view).findViewById(R.id.location).setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}
