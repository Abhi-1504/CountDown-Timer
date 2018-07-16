package com.yoyi.android.countdowntimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    // TODO make new branch for this application

    private static final String FORMAT = "%02d:%02d:%02d";

    // Creating objects and Variables to link Views and store values
    TextView timer;
    TextView message;
    long timeInMilliSeconds;
    private EditText hour, minute, second;
    private int seconds, minutes, hours;
    private CountDownTimer counter;
    private boolean timerRunningCheck, firstIntialization = true;
    private Button startAndPause, reset;
    private LinearLayout llUserInput;
    private LinearLayout llChronometer;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llUserInput = findViewById(R.id.llUserInput);
        llChronometer = findViewById(R.id.llChronometer);

        llUserInput.setVisibility(View.VISIBLE);
        llChronometer.setVisibility(View.GONE);

        timer = findViewById(R.id.chronometer);
        message = findViewById(R.id.txtMessage);

        startAndPause = findViewById(R.id.start_button);

        reset = findViewById(R.id.reset_button);
        reset.setEnabled(false);

        mp = MediaPlayer.create(this, R.raw.alert);

        // Setting on click listener to start/pause button object
        startAndPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (timerRunningCheck)
                    pauseTimer();
                else
                    startTimer();
            }
        });

        // Setting on click listener to reset button object
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

    }

    /***
     * The Function instantiate the countdown timer counter object with value to start the count down from
     */
    private void startTimer() {
        reset.setEnabled(true);

        firstTimerRunInitialization();
        Log.i("timeInMilliSeconds", "" + timeInMilliSeconds);
        counter = new CountDownTimer(timeInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInMilliSeconds = millisUntilFinished;
                timer.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                Log.v("Testing here", FORMAT);
            }

            @Override
            public void onFinish() {
                timer.setText("done!");
                startAndPause.setText(R.string.start);
                startAndPause.setEnabled(false);
                timerRunningCheck = false;
                mp.start();
            }
        }.start();

        timerRunningCheck = true;
        startAndPause.setText(R.string.pause);
    }

    /***
     * The Function pause the timer at a given point of time
     */
    private void pauseTimer() {
        counter.cancel();
        timerRunningCheck = false;
        startAndPause.setText(R.string.start);
    }

    private void resetTimer() {
        startAndPause.setEnabled(true);
        reset.setEnabled(false);
        llUserInput.setVisibility(View.VISIBLE);
        llChronometer.setVisibility(View.GONE);
        message.setText("Set timer");
        counter.cancel();
        timeInMilliSeconds = 0;
        timerRunningCheck = false;
        firstIntialization = true;
        timer.setText(R.string.timer_text);
        startAndPause.setText(R.string.start);
        second.setText("");
        minute.setText("");
        hour.setText("");
        Toast.makeText(this, "Enter the new start time", Toast.LENGTH_SHORT).show();
    }

    /***
     * The function takes the input from the user and initialises the start time of the counter
     */
    private void firstTimerRunInitialization() {
        if (firstIntialization) {

            llUserInput.setVisibility(View.GONE);
            llChronometer.setVisibility(View.VISIBLE);

            message.setText("Countdown");

            hour = findViewById(R.id.hour_input);

            minute = findViewById(R.id.minute_input);

            second = findViewById(R.id.second_input);

            String testString;

            testString = second.getText().toString();

            System.out.println(testString);

            if (testString.compareTo("") == 0) {
                seconds = 0;
            } else
                seconds = Integer.parseInt(testString);

            System.out.println(seconds);

            testString = minute.getText().toString();

            if (testString.compareTo("") == 0) {
                minutes = 0;
            } else
                minutes = Integer.parseInt(testString);

            System.out.println(minutes);

            testString = hour.getText().toString();

            if (testString.compareTo("") == 0) {
                hours = 0;
            } else
                hours = Integer.parseInt(testString);

            timeInMilliSeconds = ((hours * 3600) + (minutes * 60) + seconds) * 1000;
            firstIntialization = false;
        }
    }
}



