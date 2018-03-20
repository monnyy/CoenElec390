package com.example.nick.medminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
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

        alarmSet();
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





            loginButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View V){

                    //get the inputed values
                    String nameValue = userName.getText().toString();
                    String passValue = passWord.getText().toString();
                    createNotification();
                    Log.d(TAG, "The onClick() LOGGED IN event");
                    Toast toast = null;

                    if (nameValue.isEmpty()) {
                        toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Username ", Toast.LENGTH_LONG);
                        toast.show();
                    }

                    else if (passValue.isEmpty()) {
                        toast = Toast.makeText(getApplicationContext(), ":( Invalid Input: Password ", Toast.LENGTH_LONG);
                        //show when the save button has been clicked
                        toast.show();
                    }

                    else if (nameValue.equals("admin") && passValue.equals("1234")) {


                        Intent intent = new Intent(V.getContext(), CreateUserProfileActivity.class);
                        startActivity(intent);
                    }
                }
            });
    }

    private void alarmSet() {
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
    private NotificationManager notifManager;
    public void createNotification() {
        final int NOTIFY_ID = 1002;

        // There are hardcoding only for show it's just strings
        String name = "my_package_channel";
        String id = "my_package_channel_1"; // The user-visible name of the channel.
        String description = "my_package_first_channel"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notifManager == null) {
            notifManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = notifManager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                notifManager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);

            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle("Hello")  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(this.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("Hello")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(this);

            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle("Hello")                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(this.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("Hello")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        notifManager.notify(NOTIFY_ID, notification);
    }
}
