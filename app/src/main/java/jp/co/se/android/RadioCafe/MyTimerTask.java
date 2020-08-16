package jp.co.se.android.RadioCafe;

import android.content.Context;
import android.os.Handler;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {
    private Handler handler;
    private Context context;

    public MyTimerTask(Context context) {
        handler = new Handler();
        this.context = context;
    }

    @Override
    public void run() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                ((MainActivity)context).OnairName();
            }
        });
    }

}
