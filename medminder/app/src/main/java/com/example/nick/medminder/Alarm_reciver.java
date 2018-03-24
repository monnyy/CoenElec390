package com.example.nick.medminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zichenghe on 2018-03-23.
 */

public class Alarm_reciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String getstring = intent.getExtras().getString("Extra");
        Intent serives_intent = new Intent(context,RingtonePlayserives.class);
        serives_intent.putExtra("Extra",getstring);
        context.startService(serives_intent);

    }


}
