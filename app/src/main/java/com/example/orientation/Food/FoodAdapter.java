package com.example.orientation.Food;

import android.app.Activity;
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
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.orientation.Dbhelper.FoodDB;
import com.example.orientation.R;
import com.example.orientation.model.FoodTable;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    Cursor cursor;
    ArrayList<Boolean> show;

    public FoodAdapter(Context c, Cursor mcursor) {
        context = c;
        cursor = mcursor;
        show = new ArrayList<Boolean>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_loc, parent, false);
        return new FoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }
        show.add(position, false);
        String name = cursor.getString(cursor.getColumnIndex(FoodTable.FoodEntry.COLUMN_NAME));
        String iurl = cursor.getString(cursor.getColumnIndex(FoodTable.FoodEntry.COLUMN_IURL));
        final String lurl = cursor.getString(cursor.getColumnIndex(FoodTable.FoodEntry.COLUMN_LURL));
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
        holder.foodstall.setText(name);
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

    public void swapCursor(Cursor newCursor) {
        if (cursor != null) {
            cursor.close();
        }
        cursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }

    public void direct(String lurl) {
        Uri uri = Uri.parse(lurl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView foodstall;
        private ImageView img;
        ImageButton maps;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodstall = (TextView) itemView.findViewById(R.id.name);
            img = (ImageView) itemView.findViewById(R.id.img);
            maps = (ImageButton) itemView.findViewById(R.id.maps);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String stallname = (String) foodstall.getText();
                    FoodDB db = new FoodDB(context);
                    FoodBottomSheet bottomSheet = new FoodBottomSheet(db.showDescr(stallname));
                    bottomSheet.show(((AppCompatActivity) context).getSupportFragmentManager(), "BottomSheet");
                }
            });
        }
    }
}
