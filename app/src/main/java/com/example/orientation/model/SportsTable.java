package com.example.orientation.model;

import android.provider.BaseColumns;

public class SportsTable {
    public SportsTable() {
    }
    public static final class SportsEntry implements BaseColumns {
        public static final String TABLE_NAME = "sportlist";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LURL = "lurl";
        public static final String COLUMN_IURL = "iurl";
        public static final String COLUMN_DESC = "descr";
    }
}
