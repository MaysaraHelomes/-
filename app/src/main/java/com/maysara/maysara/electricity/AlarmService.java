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
import android.support.annotation.Nullable;

/**
 * Created by maysara on 12/27/16.
 */

public class AlarmService extends Service {


    public final String  ACTION_STOP_SERVICE = "ACTION_STOP_SERVICE";
    public final int NOTIFCATION_ID = 10 ;

    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
    MediaPlayer mp;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mp  = MediaPlayer.create(getApplicationContext(), notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NotificationManager manager = (NotificationManager)this.getSystemService(this.NOTIFICATION_SERVICE);

        if (ACTION_STOP_SERVICE.equals(intent.getAction())) {
            manager.cancel(NOTIFCATION_ID);
            stopSelf();
        }
        else {
            Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
            builder.setContentTitle("الكهربا اجت");
            builder.setContentText("الكهربا اجت");
            builder.setSmallIcon(R.drawable.icon);
            Intent stopSelf = new Intent(this, AlarmService.class);
            stopSelf.setAction(this.ACTION_STOP_SERVICE);
            PendingIntent pStopSelf = PendingIntent.getService(this, 0, stopSelf, PendingIntent.FLAG_CANCEL_CURRENT);
            builder.addAction(R.color.secondaryText, "أوقف المنبه", pStopSelf);
            Notification n = builder.build();
            manager.notify(NOTIFCATION_ID, n);
            playMusic();
            startForeground(NOTIFCATION_ID, n);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

    private void playMusic() {
            mp.setLooping(true);
            mp.start();
    }

}

