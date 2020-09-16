package com.tyomashi.starnger;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import java.util.TimerTask;

public class Updater extends TimerTask implements Runnable {
    Context context1;
    Updater(Context context) {
        context1 = context;
    }
    TextView text;
    @Override
    public void run() {
        ((Activity)context1).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text = ((Activity)context1).findViewById(R.id.player_name);
                text.setText("" + Settings.music);
            }
        });
    }
}
