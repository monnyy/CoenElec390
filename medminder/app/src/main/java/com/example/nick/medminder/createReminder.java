package com.example.nick.medminder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Year;
import java.time.YearMonth;
import java.util.Calendar;


public class createReminder extends AppCompatActivity {
    TextView textView_repeat_text_day;
    TextView textView_repeat_text_hour;
    TextView textView_repeat_text_min;
    TextView textView_time;
    TextView textView_date;
    TextView textView_repeat;
    EditText repeat_day;
    EditText repeat_hour;
    EditText repeat_min;
    Button create_btn;
    Button repeat_btn;
    Switch mSwitch;
    private int hour, min, day, month_a, year_a;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder2);
        textView_time = (TextView) findViewById(R.id.create_time);
        textView_date = (TextView) findViewById(R.id.create_date);
        textView_repeat_text_day = (TextView) findViewById(R.id.repeat_text_day);
        textView_repeat_text_hour = (TextView) findViewById(R.id.repeat_text_hour);
        textView_repeat_text_min = (TextView) findViewById(R.id.repeat_text_min);
        mSwitch = (Switch) findViewById(R.id.repeat_switch);
        textView_repeat = (TextView) findViewById(R.id.create_repeat);
        repeat_btn = (Button) findViewById(R.id.repeat_btn);
        repeat_day = (EditText) findViewById(R.id.repeat_day);
        repeat_hour = (EditText) findViewById(R.id.repeat_hour);
        repeat_min = (EditText) findViewById(R.id.repeat_min);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] dateSpinner = new String[]{
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        };

        mSwitch.isChecked();

        Spinner s = (Spinner) findViewById(R.id.create_spinner_day);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dateSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        create_btn = (Button) findViewById(R.id.create_btn);
        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(createReminder.this, splash.class);
                Toast toast = Toast.makeText(getApplicationContext(), "PLACEHOLDER! Reminder creation not yet implemented!", Toast.LENGTH_LONG);
                toast.show();
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setdate(View view) {
        Toast toast = Toast.makeText(getApplicationContext(),"You are clicking this",Toast.LENGTH_LONG);
        toast.show();
        final Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month_a = calendar.get(Calendar.MONTH);
        year_a = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textView_date.setText(dayOfMonth + "/" + (month+1) + "/" + year);
            }
        },day,month_a,year_a);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public void settime(View view) {
        Toast toast = Toast.makeText(getApplicationContext(),"You are clicking this",Toast.LENGTH_LONG);
        toast.show();
        final Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hourstring, minstring;
                if (hourOfDay < 10)
                    hourstring = "0" + hourOfDay;
                else
                    hourstring = "" +hourOfDay;

                if (minute < 10)
                    minstring = "0" + minute;
                else
                    minstring = "" +minute;

                textView_time.setText(hourstring + ":" + minstring);
            }
        },hour,min,false);
        timePickerDialog.show();
    }

    public void setRepeat(View view){
        boolean on = ((Switch) view).isChecked();
        if (on) {
            repeat_day.setVisibility(View.VISIBLE);
            repeat_hour.setVisibility(View.VISIBLE);
            repeat_min.setVisibility(View.VISIBLE);
            textView_repeat_text_day.setVisibility(View.VISIBLE);
            textView_repeat_text_hour.setVisibility(View.VISIBLE);
            textView_repeat_text_min.setVisibility(View.VISIBLE);
            repeat_btn.setVisibility(View.VISIBLE);

        }
        else {
            repeat_day.setVisibility(View.INVISIBLE);
            repeat_hour.setVisibility(View.INVISIBLE);
            repeat_min.setVisibility(View.INVISIBLE);
            textView_repeat_text_day.setVisibility(View.INVISIBLE);
            textView_repeat_text_hour.setVisibility(View.INVISIBLE);
            textView_repeat_text_min.setVisibility(View.INVISIBLE);
            repeat_btn.setVisibility(View.INVISIBLE);
        }
    }

}

