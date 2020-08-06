package com.example.orientation.model;

import android.provider.BaseColumns;

public class DepartTable {
    public DepartTable() {
    }

    public static final class DepartEntry implements BaseColumns {
        public static final String TABLE_NAME = "departlist";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LURL = "lurl";
        public static final String COLUMN_IURL = "iurl";
        public static final String COLUMN_DESC = "descr";
    }
}
