package com.yoyi.android.countdowntimer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    // Creating objects and Variables to link Views and store values
    TextView timer, message;
    private EditText hour, minute, second;
    private Button startAndPause, reset;
    private LinearLayout llUserInput, llChronometer;
    private RelativeLayout rlBackground;
    private MediaPlayer mp;
    WorkTimer mTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigning views
        llUserInput = findViewById(R.id.llUserInput);
        llChronometer = findViewById(R.id.llChronometer);
        rlBackground = findViewById(R.id.frame);
        llUserInput.setVisibility(View.VISIBLE);
        llChronometer.setVisibility(View.GONE);
        timer = findViewById(R.id.chronometer);
        message = findViewById(R.id.txtMessage);
        startAndPause = findViewById(R.id.start_button);
        reset = (findViewById(R.id.reset_button));
        hour = findViewById(R.id.hour_input);
        minute = findViewById(R.id.minute_input);
        second = findViewById(R.id.second_input);
        mp = MediaPlayer.create(this, R.raw.alert);

        //Disable reset button on Create
        reset.setEnabled(false);


        mTimer = new WorkTimer();
        mTimer.setTimer(mp, hour, minute, second, timer, message, startAndPause, llUserInput, llChronometer, rlBackground, reset);

        // Setting on click listener to start/pause button object
        startAndPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                    mTimer.startOrPauseTimer();
            }
        });

        // Setting on click listener to reset button object
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimer.resetTimer();
            }
        });
    }
}