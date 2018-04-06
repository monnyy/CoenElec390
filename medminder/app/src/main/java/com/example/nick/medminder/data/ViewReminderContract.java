package com.example.nick.medminder.data;

import android.database.Cursor;
import android.provider.BaseColumns;

public class ViewReminderContract {

    private ViewReminderContract() {}



    public static final class ViewReminderEntry implements BaseColumns {



        public final static String TABLE_NAME = "vehicles";

        public final static String _ID = BaseColumns._ID;

        public static final String KEY_TITLE = "title";
        public static final String KEY_DATE = "date";
        public static final String KEY_TIME = "time";
        public static final String KEY_REPEAT = "repeat";
        public static final String KEY_REPEAT_NO = "repeat_no";
        public static final String KEY_REPEAT_TYPE = "repeat_type";
        public static final String KEY_ACTIVE = "active";

    }

}
