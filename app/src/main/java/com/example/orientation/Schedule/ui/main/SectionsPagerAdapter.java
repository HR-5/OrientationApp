package com.example.orientation.Schedule.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.example.orientation.Dbhelper.SchdDB;
import com.example.orientation.R;
import com.example.orientation.model.SchdTable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.Collections;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4
            , R.string.tab_text_5};
    private final Context mContext;
    ArrayList<String> dates;

    public SectionsPagerAdapter(Context context, FragmentManager fm,ArrayList<String> dates) {
        super(fm);
        this.dates = new ArrayList<>();
        mContext = context;
        this.dates = dates;
        Collections.sort(dates);
    }

    @Override
    public Fragment getItem(int position) {
        String date;

        switch (position) {
            case 0:
                date = dates.get(0);
                List list = new List(mContext, date);
                return list;

            case 1:
                date = dates.get(1);
                List list1 = new List(mContext, date);
                return list1;

            case 2:
                date = dates.get(2);
                List list2 = new List(mContext, date);
                return list2;
            case 3:
                date = dates.get(3);
                List list3 = new List(mContext, date);
                return list3;
            case 4:
                date = dates.get(4);
                List list4 = new List(mContext, date);
                return list4;
        }

        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 5;
    }
}