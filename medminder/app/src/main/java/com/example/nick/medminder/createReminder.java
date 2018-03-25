package com.example.nick.medminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class createReminder extends AppCompatActivity {

    Button create_btn;
    TextView date_view, time_view;
    EditText med_name, repeat_day;
    AlarmManager alarmManager;
    Switch repeat_switch;
    PendingIntent pIntent;
    private boolean repeat;
    private int hour, min, day, month, year, rDays;
    private String strRepeat;
    final private int dayInMillis = 1000*60*60*24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);
        create_btn = (Button) findViewById(R.id.createReminderBTN);
        date_view = (TextView) findViewById(R.id.create_date);
        time_view = (TextView) findViewById(R.id.create_time);
        med_name = (EditText) findViewById(R.id.medName);
        repeat_day = (EditText) findViewById(R.id.repeat_days);
        repeat_switch = (Switch) findViewById(R.id.repeat_switch);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        repeat = false;
        final Calendar calendar = Calendar.getInstance();
        final Intent intent = new Intent(createReminder.this, NotificationTrigger.class);

        repeat_switch.isChecked();

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pIntent = PendingIntent.getService(createReminder.this, 0, intent, 0);
                calendar.set(year, month, day, hour, min);
                if (repeat) {
                    strRepeat = repeat_day.getText().toString();
                    rDays = Integer.parseInt(strRepeat);
                    rDays = rDays * dayInMillis;
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), rDays, pIntent);
                    Toast.makeText(createReminder.this, "Repeating Reminder Set!", Toast.LENGTH_SHORT).show();
                }
                else {
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
                    Toast.makeText(createReminder.this, "Reminder Set!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        DatePickerDialog dpd = new DatePickerDialog(createReminder.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                date_view.setText(m + " / " + d + ", " + y);
            }
        }, day, month, year);
        dpd.getDatePicker().setMinDate(System.currentTimeMillis());
        dpd.show();
    }

    public void setTime(View view) {
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        TimePickerDialog tpd = new TimePickerDialog(createReminder.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int min) {
                String hString, mString;
                if (hour < 10)
                    hString = "0" + hour;
                else
                    hString = "" + hour;
                if (min < 10)
                    mString = "0" + min;
                else
                    mString = "" + min;
                time_view.setText(hString + " : " + mString);
            }
        }, hour, min, false);
        tpd.show();
    }

    public void setRepeat(View view) {
        boolean on = ((Switch) view).isChecked();
        if (on) {
            repeat = true;
            repeat_day.setVisibility(View.VISIBLE);
        }
        else {
            repeat = false;
            repeat_day.setText(null);
            repeat_day.setVisibility(View.INVISIBLE);
        }
    }
}