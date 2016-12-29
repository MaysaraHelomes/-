package com.maysara.maysara.electricity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;

/**
 * Created by maysara on 12/27/16.
 */

public class AlarmService extends Service {


    public final String  ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";
    public final int NOTIFCATION_ID = 10 ;

    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    MediaPlayer mp;
    Vibrator vibrator ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp  = MediaPlayer.create(getApplicationContext(), notification);
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager manager = (NotificationManager)this.getSystemService(this.NOTIFICATION_SERVICE);

        if (ACTION_STOP_SERVICE.equals(intent.getAction())) {
            manager.cancel(NOTIFCATION_ID);
            stopSelf();
        }
        else {
            playMusic();
            vibrate();
            startForeground(NOTIFCATION_ID,startNotification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
        vibrator.cancel();
    }

    private void playMusic() {
            mp.setLooping(true);
            mp.start();
    }

    private void vibrate()
    {
        long[] pattern = {0,1000,100};
        vibrator.vibrate(pattern,0);
    }

    private Notification startNotification()
    {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle(getString(R.string.electricityOn));
        builder.setContentText(getString(R.string.electricityOn));
        builder.setSmallIcon(R.drawable.icon);
        Intent stopService = new Intent(this,AlarmService.class);
        stopService.setAction(ACTION_STOP_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,stopService,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.addAction(R.color.text_icons,this.getString(R.string.stopAlarm),pendingIntent);
        return builder.build();
    }

}

