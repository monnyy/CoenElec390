package com.example.nick.medminder.reminder;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.example.nick.medminder.AddReminderActivity;
import com.example.nick.medminder.LoginActivity;
import com.example.nick.medminder.R;
import com.example.nick.medminder.data.AlarmReminderContract;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Nick.
 */

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();
    private static final String id = "nick_channel_1";
    private static final String name = "nick_1";
    private static final int NOTIFICATION_ID = 42;
    private static final int FIVE_MIN = 300000;
    private static final int TEN_MIN = 600000;
    private static final String DEVICE_NAME = "HC-05";
    private Boolean open = false;
    private String openData;
    private BluetoothArduinoHelper btArduino;

    //This is a deep link intent, and needs the task stack
    public static PendingIntent getReminderPendingIntent(Context context, Uri uri) {
        Intent action = new Intent(context, ReminderAlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public ReminderAlarmService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder;
        PendingIntent pendingIntent;
        Uri uri = intent.getData();

        //Display a notification to view the task details
        Intent action = new Intent(this, AddReminderActivity.class);
        action.setData(uri);
        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Grab the task description
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        String description = "";
        try {
            if (cursor != null && cursor.moveToFirst()) {
                description = AlarmReminderContract.getColumnString(cursor, AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (manager == null) {
            manager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = manager.getNotificationChannel(id);
            if (mChannel == null) {
                mChannel = new NotificationChannel(id, name, importance);
                mChannel.setDescription(description);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                manager.createNotificationChannel(mChannel);
            }
            builder = new NotificationCompat.Builder(this, id);

            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(getString(R.string.reminder_title))  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(description)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("redMed Me!")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        } else {

            builder = new NotificationCompat.Builder(this);

            intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            builder.setContentTitle(getString(R.string.reminder_title))                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(description)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker("redMed Me!")
                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                    .setPriority(Notification.PRIORITY_HIGH);
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        Notification notification = builder.build();
        manager.notify(NOTIFICATION_ID, notification);
        btArduino = BluetoothArduinoHelper.getInstance(DEVICE_NAME);
        Stopwatch connectionfail = new Stopwatch();
        while(!btArduino.isConnected()) {
            if(connectionfail.elapsedTime() > 30000 && connectionfail.elapsedTime() < 40000) {
                builder.setContentText("Error! Please make sure the Bluetooth connection has been established to the box!");
                notification = builder.build();
                manager.notify(NOTIFICATION_ID, notification);
            }
            try {
                btArduino.Connect();
            } catch (Exception ex) {
                Log.d(TAG, "\t\tCannot make connection!");
            }
        }
        Stopwatch s = new Stopwatch();
        int notif_counter_A = 0, notif_counter_B = 0;
        MediaPlayer mediaPlayer;
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.start();
        while(true) {

            try {
                openData = btArduino.getStatus();
            } catch (IOException ex) {}

            if (openData.equals("0") && s.elapsedTime() > 5000) {
                mediaPlayer.stop();
                builder.setContentText("Box opened! Reminder dismissed.");
                notification = builder.build();
                manager.notify(NOTIFICATION_ID + 1, notification);
                break;
            }
            if (openData.equals("1") && s.elapsedTime() > 10000) {
                for (; notif_counter_A < 1; notif_counter_A++) {
                    builder.setContentText("Secondary Reminder.");
                    notification = builder.build();
                    manager.notify(NOTIFICATION_ID + 2, notification);
                }
            }
            if (openData.equals("1") && s.elapsedTime() > 20000) {
                mediaPlayer.stop();
                for (; notif_counter_B < 1; notif_counter_B++) {
                    builder.setContentText("Tertiary Reminder, Emergency contact will be notified.");
                    notification = builder.build();
                    manager.notify(NOTIFICATION_ID + 3, notification);
                }
                //send text
                notif_counter_A = 0;
                notif_counter_B = 0;
                break;
            }
        }
    }
}