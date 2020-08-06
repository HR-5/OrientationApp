package com.example.orientation.model;

import android.provider.BaseColumns;

public class FoodTable {
    public FoodTable() {
    }
    public static final class FoodEntry implements BaseColumns {
        public static final String TABLE_NAME = "stalllist";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LURL = "lurl";
        public static final String COLUMN_IURL = "iurl";
        public static final String COLUMN_DESC = "descr";
    }
}
