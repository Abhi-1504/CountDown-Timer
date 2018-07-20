package com.yoyi.android.countdowntimer;

import android.graphics.Color;
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

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Creating objects and Variables to link Views and store values
    TextView timer, message, txtSessionName ,minute;
    private Button startAndPause, reset;
    private LinearLayout llUserInput, llChronometer;
    private RelativeLayout rlBackground;
    private MediaPlayer mp;
    WorkTimer mTimer;
    public ArrayList<String> sName = new ArrayList<>();

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
        minute = findViewById(R.id.minute_input);
        mp = MediaPlayer.create(this, R.raw.alert);
        txtSessionName = findViewById(R.id.sessionName);

        //Disable reset button on Create
        reset.setEnabled(false);

        startAndPause.setBackgroundColor(Color.parseColor("#1c1c1c"));
        startAndPause.setTextColor(Color.parseColor("#b8e3be"));

        reset.setBackgroundColor(Color.parseColor("#272626"));
        reset.setTextColor(Color.parseColor("#737473"));


        mTimer = new WorkTimer();

        sName.add("Udemy");
        sName.add("Udemy");
        sName.add("Guitar");
        sName.add("Photoshop");
        sName.add("Excel");

        mTimer.setList(sName);
        minute.setText(mTimer.getPomodoroSessionNumber());
        message.setText("Start your work");
        mTimer.setTimer(mp, minute, timer, message, startAndPause, llUserInput, llChronometer, rlBackground, reset, txtSessionName);

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