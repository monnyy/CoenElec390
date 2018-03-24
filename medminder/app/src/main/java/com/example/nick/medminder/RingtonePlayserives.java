package com.example.nick.medminder;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by zichenghe on 2018-03-23.
 */

public class RingtonePlayserives extends Service {


    MediaPlayer mediaPlayer;
    boolean isrunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalServices", "Received Start ID "+startId + " : " + intent);

        String state = intent.getExtras().getString("Extra");
        assert state != null;
        switch (state){
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }



        if(!this.isrunning && startId == 1){
            mediaPlayer = MediaPlayer.create(this,R.raw.sound);
            mediaPlayer.start();
            this.isrunning = true;
            startId = 0;
        }
        else if (this.isrunning && startId == 0){

            mediaPlayer.stop();
            mediaPlayer.reset();
            this.isrunning = false;
            startId = 0;
        }
        else if (!this.isrunning && startId ==0){

            this.isrunning = false;
            startId = 0;
        }
        else if (this.isrunning && startId ==1){
            this.isrunning = true;
            startId = 1;
        }
        else{
            Log.e("aaa","bbbb");
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"fuck you",Toast.LENGTH_SHORT).show();
    }
}
