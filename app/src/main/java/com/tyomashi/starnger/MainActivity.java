package com.tyomashi.starnger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Dialog dialogAbout;
    Dialog dialogSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Settings.music = true;
        ImageView image = (ImageView)findViewById(R.id.imageView);
        image.setImageResource(R.drawable.menu_anim);
        AnimationDrawable animDraw = (AnimationDrawable) image.getDrawable();
        animDraw.start();
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        w.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        LinearLayout LL1 = (LinearLayout)findViewById(R.id.LL1);
        LL1.setClipToOutline(true);
        mediaPlayer = MediaPlayer.create(this, R.raw.level0);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        Thread updater = new Thread(new Runnable() {
            public void run() {
                try {
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   update1();
                               }
                           });
                        }
                    };
                    Timer timer = new Timer("Updater");
                    timer.scheduleAtFixedRate(timerTask, 0, 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        updater.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }
    public void update1() {
        TextView text = (TextView)findViewById(R.id.text);
       /* if (Settings.music) {
               mediaPlayer.start();
          }
          else mediaPlayer.pause();
       */
    }
    public void asd1(View view) {
        Button btn = (Button)findViewById(R.id.button);
        btn
        .animate().translationY(-100)
        .rotationBy(360f)
        .setDuration(9000)
        .alpha(0.8f)
        .start();
        TextView text = (TextView)findViewById(R.id.text);
        text
        .animate().translationY(100)
        .rotationBy(-360f)
        .setDuration(9000)
        .alpha(0.6f)
        .start();
    }
    public void exit(View view) {
        finish();
        mediaPlayer.stop();
    }
    public void about(View view) {
        dialogAbout = new Dialog(this);
        dialogAbout.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAbout.setContentView(R.layout.about_dialog);
        dialogAbout.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialogAbout.setCancelable(false);
        dialogAbout.show();
    }
    public void settings(View view) {
        dialogSettings = new Dialog(this);
        dialogSettings.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSettings.setContentView(R.layout.settings);
        dialogSettings.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSettings.setCancelable(false);
        dialogSettings.show();
        TextView btnClose = (TextView)dialogSettings.findViewById(R.id.exitBtn);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dialogSettings.cancel();
                }catch (Exception e) {
                    //
                }
            }
        });
        Switch musicSwitch = (Switch)dialogSettings.findViewById(R.id.musicSwitch);
        musicSwitch.setChecked(mediaPlayer.isPlaying());
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Settings.music = true;
                    mediaPlayer.start();
                }else {
                    Settings.music = false;
                    mediaPlayer.pause();
                }
            }
        });
    }
    public void start(View view) {
        mediaPlayer.stop();
        Intent intent = new Intent(this, Level0.class);
        startActivity(intent);
        finish();
    }
}