package com.maysara.maysara.electricity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by maysara on 12/29/16.
 */

public class StopAlarmReceiver extends BroadcastReceiver {

    public final String  ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent stopService = new Intent(context,AlarmService.class);
        stopService.setAction(ACTION_STOP_SERVICE);
        context.startService(stopService);
    }

}
