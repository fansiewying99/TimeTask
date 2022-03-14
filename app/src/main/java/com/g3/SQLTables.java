package com.g3;

import android.provider.BaseColumns;

public class SQLTables {
    /*
    *  This is where all the table columns are declared for different tables.
    *  Each abstract class represents a table.
    */
    public abstract class AppSettings implements BaseColumns {
        public static final String TABLE_NAME = "app_settings";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TIME_NOTIFY = "time_notify";
        public static final String COLUMN_TASK_NOTIFY = "task_notify";
        public static final String COLUMN_DARK_MODE = "dark_mode";
    }
}
