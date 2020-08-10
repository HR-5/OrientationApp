package com.example.orientation.Departments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.core.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orientation.R;
import com.example.orientation.model.DepartTable;
import com.example.orientation.model.Department;

import java.util.ArrayList;

public class DepartAdapter extends RecyclerView.Adapter<DepartAdapter.ViewHolder> {
    private Context context;
    Cursor cursor;
    ArrayList<Boolean> show;
    ArrayList<String> names;

    public DepartAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
        show = new ArrayList<Boolean>();
        names = new ArrayList<String>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_loc, parent, false);
        return new DepartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        show.add(position,false);
        String name = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_NAME));
        names.add(name);
        String iurl = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_IURL));
        final String lurl = cursor.getString(cursor.getColumnIndex(DepartTable.DepartEntry.COLUMN_LURL));
        String uri = "@drawable/"+iurl;
        int imageResource = context.getResources().getIdentifier(uri, null, context.getPackageName());
        Drawable res = context.getResources().getDrawable(imageResource);

        holder.depart.setText(name);
        holder.img.setImageDrawable(res);
        holder.maps.setOnClickListener(new View.OnClickListener() {
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
        private TextView depart;
        private ImageView img;
        private View v;
        ImageButton maps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            depart = (TextView) itemView.findViewById(R.id.name);
            img = (ImageView) itemView.findViewById(R.id.img);
            maps = (ImageButton) itemView.findViewById(R.id.maps);
            v = itemView;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String name = names.get(pos);
                    Intent intent = new Intent(context,DepartMotto.class);
                    intent.putExtra("name",name);
                    Pair[] pair = new Pair[2];
                    pair[0]= Pair.create((View) depart,"name");
                    pair[1]= Pair.create((View) img,"imageShare");
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,pair);
                    context.startActivity(intent,options.toBundle());
                }
            });
        }
    }
}
