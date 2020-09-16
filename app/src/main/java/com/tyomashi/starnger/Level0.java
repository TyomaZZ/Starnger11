package com.tyomashi.starnger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;



public class Level0 extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    Dialog dialogPlayer;
    Dialog dialogSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level0);
        ImageView image = (ImageView)findViewById(R.id.image_in_city);
        image.setImageResource(R.drawable.menu_anim2);
        AnimationDrawable animDraw = (AnimationDrawable) image.getDrawable();
        animDraw.start();
        Window w = getWindow();
        w.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        w.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mediaPlayer = MediaPlayer.create(this, R.raw.level0);
        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        TextView text = (TextView)findViewById(R.id.player_name);
        text.setText("" + Settings.music);
        test();
        mediaPlayer.setLooping(true);
        if (!Settings.music) {
            mediaPlayer.pause();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Settings.music = true) {
            mediaPlayer.start();
        }
    }

    public void test() {
        TimerTask timerTask = new Updater(this);
        Timer timer = new Timer("LOLUPDATE");
        timer.scheduleAtFixedRate(timerTask, 1000, 100);
    }

    public void dialogPlayer(View view) { //dialog player (hp? items? skills?);
        TextView text = (TextView)findViewById(R.id.player_name);
        text.setText("tyomashi");
        dialogPlayer = new Dialog(this);
        dialogPlayer.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogPlayer.setContentView(R.layout.player_dialog_level0);
        dialogPlayer.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //dialogAbout.setCancelable(false);
        dialogPlayer.show();
        final ImageView image2 = (ImageView)dialogPlayer.findViewById(R.id.player_dial_image);
        image2.setImageResource(R.drawable.player_dial_anim);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image2.setImageResource(R.drawable.click_player_dial_anim);
                AnimationDrawable animDraw3 = (AnimationDrawable) image2.getDrawable();
                animDraw3.start();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Ты нашёл гусеничку!", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        AnimationDrawable animDraw2 = (AnimationDrawable) image2.getDrawable();
        animDraw2.start();
    }

    public void exit(View view) {
        finish();
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
        Toast toast = Toast.makeText(getApplicationContext(),
                "В разработке!", Toast.LENGTH_LONG);
        toast.show();
    }
}
