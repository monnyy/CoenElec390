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
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
 * Created by delaroy on 9/22/17.
 */

public class ReminderAlarmService extends IntentService {
    private static final String TAG = ReminderAlarmService.class.getSimpleName();
    private static final String id = "nick_channel_1";
    private static final String name = "nick_1";
    private static final int NOTIFICATION_ID = 42;
    private static final int FIVE_MIN = 300000;
    private static final int TEN_MIN = 600000;
    private static final String DEVICE_NAME = "SH-HC-08";
    private Boolean open = false;
    private String openData;
    private BluetoothArduinoHelper btArduino;

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket socket;
    BluetoothDevice device;
    OutputStream oStream;
    InputStream iStream;
    Thread t;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

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

        btArduino = BluetoothArduinoHelper.getInstance(DEVICE_NAME);
        try {
            btArduino.Connect();
        } catch(Exception ex) {
            openData = "FUCK";
        }
        Stopwatch s = new Stopwatch();
        Notification notification = builder.build();
        manager.notify(NOTIFICATION_ID, notification);
        int notif_counter_A = 0, notif_counter_B = 0;
        while(true) {

            //openData = btArduino.getLastMessage();

            if(open) {
                builder.setContentText("good job");
                notification = builder.build();
                manager.notify(NOTIFICATION_ID + 1, notification);
                try {
                    btArduino.Disconnect();
                } catch (IOException ex) {}
                break;
            }
            if(!open && s.elapsedTime() > 10000) {
                for(; notif_counter_A < 1; notif_counter_A++) {
                    builder.setContentText("2nd chance!   " + openData);
                    notification = builder.build();
                    manager.notify(NOTIFICATION_ID + 2, notification);
                }
            }
            if(!open && s.elapsedTime() > 20000) {
                for(; notif_counter_B < 1; notif_counter_B++) {
                    builder.setContentText("EMERGENCY");
                    notification = builder.build();
                    manager.notify(NOTIFICATION_ID + 3, notification);
                }
                notif_counter_A = 0;
                notif_counter_B = 0;
                try {
                    btArduino.Disconnect();
                } catch (IOException ex) {}
                break;
            }
        }
    }

    void findBT() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null) {
            Toast.makeText(this, "No BT detected", Toast.LENGTH_SHORT).show();
        }
        if(!bluetoothAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBT);
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0) {
            for(BluetoothDevice btDevice: pairedDevices) {
                if(btDevice.getName().equals("SH-HC-08")) {
                    device = btDevice;
                    break;
                }
            }
        }
    }

    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
        socket = device.createRfcommSocketToServiceRecord(uuid);
        socket.connect();
        oStream = socket.getOutputStream();
        iStream = socket.getInputStream();
        listenForData();
    }

    void listenForData() {
        final Handler handler = new Handler();
        final byte delimiter = 10;
        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        t = new Thread(new Runnable() {
            public void run() {
                while(!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvail = iStream.available();
                        if(bytesAvail > 0) {
                           byte[] packetBytes = new byte[bytesAvail];
                           iStream.read(packetBytes);
                           for(int i = 0; i < bytesAvail; i++) {
                               byte b = packetBytes[i];
                               if (b == delimiter) {
                                   byte[] encodedBytes = new byte[readBufferPosition];
                                   System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                   final String data = new String(encodedBytes, "US-ASCII");
                                   readBufferPosition = 0;
                                   handler.post(new Runnable() {
                                       public void run() {
                                           openData = data;
                                       }
                                   });
                               }
                               else {
                                   readBuffer[readBufferPosition++] = b;
                               }
                           }
                        }
                    } catch(IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });
        t.start();
    }
}

class Stopwatch {

    private final long start;

    public Stopwatch() {
        start = System.currentTimeMillis();
    }

    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start);
    }
}