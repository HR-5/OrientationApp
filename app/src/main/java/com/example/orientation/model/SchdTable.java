package com.example.orientation.model;

import android.provider.BaseColumns;

public class SchdTable {
    public SchdTable() {
    }

    public static final class SchdEntry implements BaseColumns{
        public static final String TABLE_NAME = "schdlist";
        public static final String COLUMN_EVENT = "eve";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_DESC = "descr";
        public static final String COLUMN_LOC = "loc";
        public static final String COLUMN_LURL = "lurl";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_IMG = "img";
    }
}
