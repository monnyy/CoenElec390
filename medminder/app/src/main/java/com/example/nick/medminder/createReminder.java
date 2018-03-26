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

import static java.lang.Integer.parseInt;

public class createReminder extends AppCompatActivity {

    Button create_btn,period_save_btn;
    TextView date_view, time_view,period_text_day,period_text_hour,period_text_min,repeat_day;
    EditText med_name, period_day,period_hour,period_min;
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
        period_save_btn = (Button)findViewById(R.id.period_save_btn);
        create_btn = (Button) findViewById(R.id.createReminderBTN);
        date_view = (TextView) findViewById(R.id.create_date);
        time_view = (TextView) findViewById(R.id.create_time);
        period_text_day = (TextView)findViewById(R.id.period_text_day);
        period_text_hour = (TextView)findViewById(R.id.period_text_hour);
        period_text_min = (TextView)findViewById(R.id.period_text_min);
        med_name = (EditText) findViewById(R.id.medName);
        repeat_day = (TextView) findViewById(R.id.repeat_days);
        period_day = (EditText)findViewById(R.id.period_day);
        period_hour = (EditText)findViewById(R.id.period_hour);
        period_min = (EditText)findViewById(R.id.period_min);
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

        period_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUI();
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
            period_day.setVisibility(View.VISIBLE);
            period_hour.setVisibility(View.VISIBLE);
            period_min.setVisibility(View.VISIBLE);
            period_text_day.setVisibility(View.VISIBLE);
            period_text_hour.setVisibility(View.VISIBLE);
            period_text_min.setVisibility(View.VISIBLE);
            period_save_btn.setVisibility(View.VISIBLE);
            repeat_day.setVisibility(View.INVISIBLE);
        }
        else {
            repeat = false;
            period_day.setText(null);
            period_hour.setText(null);
            period_min.setText(null);
            period_day.setVisibility(View.INVISIBLE);
            period_hour.setVisibility(View.INVISIBLE);
            period_min.setVisibility(View.INVISIBLE);
            period_text_day.setVisibility(View.INVISIBLE);
            period_text_hour.setVisibility(View.INVISIBLE);
            period_text_min.setVisibility(View.INVISIBLE);
            period_save_btn.setVisibility(View.INVISIBLE);
            repeat_day.setVisibility(View.INVISIBLE);
        }
    }
    public void setUI(){
        String d, h, min;
        d = period_day.getText().toString();
        h = period_hour.getText().toString();
        min = period_min.getText().toString();
        if (d.isEmpty() && h.isEmpty() && min.isEmpty()) {
            Toast toast = Toast.makeText(getApplicationContext(), "input repeat information", Toast.LENGTH_LONG);
            toast.show();
        }
        else if (parseInt (h) >23){
            Toast toast = Toast.makeText(getApplicationContext(), "repeat hours cannot be bigger than 23", Toast.LENGTH_LONG);
            toast.show();
        }

        else if (parseInt(min) > 59){
            Toast toast = Toast.makeText(getApplicationContext(), "repeat minutes cannot be bigger than 59", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            if(d.isEmpty()){
                repeat_day.setText("Every " + h + "h(s) " + min + "min(s)");
            }
            else if (d.isEmpty() && h.isEmpty()){
                repeat_day.setText("Every " + min + "min(s)");
            }
            else if (d.isEmpty() && min.isEmpty()){
                repeat_day.setText("Every " + h + "h(s) ");
            }
            else if (h.isEmpty() && min.isEmpty()){
                repeat_day.setText("Every " + d + "D(s) ");
            }
            else if (h.isEmpty()){
                repeat_day.setText("Every " + d + "D(s) " + min + "min(s)");
            }
            else if (min.isEmpty()){
                repeat_day.setText("Every " + d + "D(s) " + h + "h(s) ");
            }
            else {
                repeat_day.setText("Every " + d + "D(s) " + h + "h(s) " + min + "min(s)");
            }
            repeat_day.setVisibility(View.VISIBLE);
            period_day.setVisibility(View.INVISIBLE);
            period_hour.setVisibility(View.INVISIBLE);
            period_min.setVisibility(View.INVISIBLE);
            period_text_day.setVisibility(View.INVISIBLE);
            period_text_hour.setVisibility(View.INVISIBLE);
            period_text_min.setVisibility(View.INVISIBLE);
            period_save_btn.setVisibility(View.INVISIBLE);
        }
    }

}