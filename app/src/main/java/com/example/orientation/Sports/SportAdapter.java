package com.example.orientation.Sports;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.orientation.R;
import com.example.orientation.Schedule.Maps;
import com.example.orientation.model.SportsTable;

import java.util.ArrayList;

public class SportAdapter extends RecyclerView.Adapter<SportAdapter.ViewHolder> {
    private Context context;
    Cursor cursor;
    ArrayList<Boolean> show;

    public SportAdapter(Context c, Cursor mcursor) {
        context = c;
        cursor = mcursor;
        show = new ArrayList<Boolean>();
    }

    @NonNull
    @Override
    public SportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_loc, parent, false);
        return new SportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SportAdapter.ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        show.add(position, false);
        String name = cursor.getString(cursor.getColumnIndex(SportsTable.SportsEntry.COLUMN_NAME));
        String iurl = cursor.getString(cursor.getColumnIndex(SportsTable.SportsEntry.COLUMN_IURL));
        final String lurl = cursor.getString(cursor.getColumnIndex(SportsTable.SportsEntry.COLUMN_LURL));
        String lati = cursor.getString(cursor.getColumnIndex(SportsTable.SportsEntry.COLUMN_LAT));
        String longi = cursor.getString(cursor.getColumnIndex(SportsTable.SportsEntry.COLUMN_LONG));
        if(iurl.equals("null")) {
            String uri = "@drawable/null_img";
            int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
            Drawable res = context.getResources().getDrawable(imageResource);
            holder.img.setImageDrawable(res);
        }
        else {
            Glide.with(context)
                    .load(iurl)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img);
        }

        holder.sportc.setText(name);
        holder.maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                direct(lati,longi,name);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }


    public void direct(String lat,String longi,String pname) {
        Maps maps = new Maps(lat,longi,pname);
        maps.show(((AppCompatActivity) context).getSupportFragmentManager(), "BottomSheet");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView sportc;
        private ImageView img;
        ImageButton maps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sportc = (TextView) itemView.findViewById(R.id.name);
            img = (ImageView) itemView.findViewById(R.id.img);
            maps = (ImageButton) itemView.findViewById(R.id.maps);
        }
    }
}

