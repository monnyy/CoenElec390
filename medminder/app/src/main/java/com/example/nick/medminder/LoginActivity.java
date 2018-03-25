package com.example.nick.medminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {

    protected static final String TAG = "Login Activity"; //to log the activity for debugging
    protected Button loginButton = null;
    protected EditText userName, passWord = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //alarmSet();
        Log.d(TAG, "The onCreate() event");
        setupUI();
    }

    protected void setupUI(){

        loginButton = (Button) findViewById(R.id.login_button);

        int[] maxLengths = {5,4};
        InputFilter[] FilterArrayID = new InputFilter[1];
        InputFilter[] FilterArrayAge = new InputFilter[1];


        userName = (EditText) findViewById(R.id.user_name);
        FilterArrayAge[0] = new InputFilter.LengthFilter(maxLengths[0]);
        userName.setFilters(FilterArrayAge);

        passWord = (EditText) findViewById(R.id.user_pwd);
        FilterArrayID[0] = new InputFilter.LengthFilter(maxLengths[1]);
        passWord.setFilters(FilterArrayID);


        loginButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View V)
            {
                //get the inputed values
                String nameValue = userName.getText().toString();
                String passValue = passWord.getText().toString();

                Log.d(TAG, "The onClick() LOGGED IN event");

                Toast toast = null;

                if (nameValue.isEmpty() || !nameValue.equals("admin"))
                {
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Username ", Toast.LENGTH_LONG);
                    toast.show();
                }

                else if (passValue.isEmpty() || !passValue.equals("1234"))
                {
                    toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Password ", Toast.LENGTH_LONG);
                    toast.show();
                }

                else if (nameValue.equals("admin") && passValue.equals("1234"))
                {
                    Intent intent = new Intent(LoginActivity.this, splash.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void alarmSet()
    {
        Intent intent = new Intent(this, NotificationTrigger.class);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        PendingIntent pIntent = PendingIntent.getService(this, 0, intent, 0);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.add(Calendar.DAY_OF_MONTH, 1);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 1000*60*60*24, pIntent);
        Toast.makeText(LoginActivity.this, "Alarm Set", Toast.LENGTH_LONG).show();
    }
}