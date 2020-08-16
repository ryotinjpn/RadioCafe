package jp.co.se.android.RadioCafe;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class Livestreaming extends Service {
    private static final int HELLO_ID = 1;
    private MediaPlayer mediaPlayer;
    private String path = "http://live.radiocafe.jp/live.mp3";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepareAsync();
            mediaPlayer
                    .setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
        } catch (Exception e) {
        }
    }

    public int onStartCommand(Intent intent, int flags,int startID) {
        super.onStartCommand(intent, flags, startID);
        final Intent notificationIntent = new Intent(this,
                MainActivity.class);
        final PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);
        // Notification
        if (Build.VERSION.SDK_INT <= 10) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification(
                    R.drawable.icon_notification, "Listening to FM797 RadioCafe LIVE",
                    System.currentTimeMillis());
            notification.setLatestEventInfo(getApplicationContext(),
                    "RadioCafe", "Listening to FM797 LIVE", contentIntent);
            mNotificationManager.notify(HELLO_ID, notification);
            Toast.makeText(this, "再生まで30秒程お待ち下さい", Toast.LENGTH_LONG).show();
        } else if (Build.VERSION.SDK_INT >= 11) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext());
            builder.setTicker("Listening to FM797 RadioCafe LIVE");
            builder.setContentTitle("RadioCafe");
            builder.setContentText("Listening to FM797 RadioCafe LIVE");
            builder.setSmallIcon(R.drawable.icon_notification);
            builder.setContentIntent(contentIntent);
            builder.setWhen(System.currentTimeMillis());
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(HELLO_ID, builder.build());

            Toast.makeText(this, "NOW LOADING…", Toast.LENGTH_LONG).show();
        }
        try {
            mediaPlayer.start();
        } catch (Exception e) {
        }
        //return Service.START_STICKY_COMPATIBILITY;
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(HELLO_ID);
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
