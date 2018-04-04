package com.example.nick.medminder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.nick.medminder.data.AlarmReminderDbHelper;
import com.example.nick.medminder.data.ViewReminderContract;
import com.example.nick.medminder.data.ViewReminderProvider;

public class viewReminder extends AppCompatActivity {
    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    AlarmReminderDbHelper alarmReminderDbHelper;
    Cursor cursor;
    ViewReminderAdapter viewReminderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);
        listView = (ListView)findViewById(R.id.viewreminderlist);
        viewReminderAdapter = new ViewReminderAdapter(getApplicationContext(),
                R.layout.viewreminder_items);

       listView.setAdapter(viewReminderAdapter);

        alarmReminderDbHelper = new AlarmReminderDbHelper(getApplicationContext());
        sqLiteDatabase = alarmReminderDbHelper.getReadableDatabase();
        cursor = alarmReminderDbHelper.getInformations(sqLiteDatabase);

        if(cursor.moveToFirst()){

            do{
                int titleColumnIndex = cursor.getColumnIndex(ViewReminderContract.ViewReminderEntry.KEY_TITLE);
                int dateColumnIndex = cursor.getColumnIndex(ViewReminderContract.ViewReminderEntry.KEY_DATE);
                int timeColumnIndex = cursor.getColumnIndex(ViewReminderContract.ViewReminderEntry.KEY_TIME);
                int repeatColumnIndex = cursor.getColumnIndex(ViewReminderContract.ViewReminderEntry.KEY_REPEAT);
                int repeatNoColumnIndex = cursor.getColumnIndex(ViewReminderContract.ViewReminderEntry.KEY_REPEAT_NO);
                int repeatTypeColumnIndex = cursor.getColumnIndex(ViewReminderContract.ViewReminderEntry.KEY_REPEAT_TYPE);
                int activeColumnIndex = cursor.getColumnIndex(ViewReminderContract.ViewReminderEntry.KEY_ACTIVE);


                String title = cursor.getString(titleColumnIndex);
                String date = cursor.getString(dateColumnIndex);
                String time = cursor.getString(timeColumnIndex);
                String repeat = cursor.getString(repeatColumnIndex);
                String repeatNo = cursor.getString(repeatNoColumnIndex);
                String repeatType = cursor.getString(repeatTypeColumnIndex);
                String active = cursor.getString(activeColumnIndex);

                ViewReminderProvider viewReminderProvider = new ViewReminderProvider(title,
                        date,time,repeat,repeatNo,repeatType,active);

                viewReminderAdapter.add(viewReminderProvider);

            }while (cursor.moveToNext());
        }
    }

}
