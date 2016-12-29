package com.maysara.maysara.electricity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by maysara on 12/27/16.
 */

public class startAlarmReceiver extends BroadcastReceiver {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor ;
    @Override
    public void onReceive(Context context, Intent intent) {
        sharedPreferences = context.getSharedPreferences("alarmstate",Context.MODE_PRIVATE);

        if (getState())
            context.startService( new Intent(context, AlarmService.class));

    }

    private  boolean getState()
    {
        return sharedPreferences.getBoolean("state",false);
    }

    private void setState(boolean s)
    {
        editor = sharedPreferences.edit();
        editor.putBoolean("state",s);
        editor.apply();
        editor.commit();
    }

}
