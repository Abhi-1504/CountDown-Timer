package com.yoyi.android.countdowntimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

public class Timer implements TimerCalls{
    private static final String FORMAT = "%02d:%02d:%02d";

    protected long timeInMilliSeconds;
    protected int seconds, minutes, hours;
    protected CountDownTimer countDownTimer;
    protected boolean isTimerRunning, isFirstRun = true;
    protected MediaPlayer mp;
    protected EditText etxtHours, etxtMinutes, etxtSeconds;
    protected TextView txtTimer;
    protected Button btnStartAndPause;


    @Override
    public void setTimer(MediaPlayer mp, EditText etxtHours, EditText etxtMinutes,
                         EditText etxtSeconds, TextView txtTimer, Button btnStartAndPause) {
        this.mp = mp;
        this.etxtHours = etxtHours;
        this.etxtMinutes = etxtMinutes;
        this.etxtSeconds = etxtSeconds;
        this.txtTimer = txtTimer;
        this.btnStartAndPause = btnStartAndPause;
    }

    @Override
    public int startOrPauseTimer()
    {
        onFirstRun();
        if (timeInMilliSeconds==0)
        {
            isFirstRun = true;
            return 3;
        }
        if (isTimerRunning)
        {
            onPauseTimer();
            return 1;
        }
        countDownTimer = new CountDownTimer(timeInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Setting Elapsed time to total time
                timeInMilliSeconds = millisUntilFinished;

                txtTimer.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)-TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                        )
                        ));
            }

            @Override
            public void onFinish() {
                txtTimer.setText("DONE!");
                btnStartAndPause.setEnabled(false);
                isTimerRunning = false;
                mp.start();
            }
        }.start() ;
        isTimerRunning = true;
        return 0;
    }

    private void onPauseTimer() {
        countDownTimer.cancel();
        isTimerRunning = false;
    }

    @Override
    public void resetTimer() {
        countDownTimer.cancel();
        timeInMilliSeconds = 0;
        isTimerRunning = false;
        isFirstRun = true;
        txtTimer.setText("00:00:00");
        etxtSeconds.setText("");
        etxtMinutes.setText("");
        etxtHours.setText("");
    }

    private boolean onFirstRun()
    {
        if (isFirstRun)
        {
            String bufferString;

            // Getting value for seconds
            bufferString = etxtSeconds.getText().toString();
            if (bufferString.compareTo("")==0)
            {
                seconds = 0;
            }
            else
            {
                seconds = Integer.parseInt(bufferString);
            }

            // Getting value for minutes
            bufferString = etxtMinutes.getText().toString();
            if (bufferString.compareTo("")==0)
            {
                minutes = 0;
            }
            else
            {
                minutes = Integer.parseInt(bufferString);
            }

            // Getting value for hours
            bufferString = etxtHours.getText().toString();
            if (bufferString.compareTo("")==0)
            {
                hours = 0;
            }
            else
            {
                hours = Integer.parseInt(bufferString);
            }

            // Setting  value to Countdown variable
            timeInMilliSeconds = ((hours * 3600) + (minutes * 60) + seconds) * 1000;

            // Setting initial run to false
            isFirstRun = false;
            return true;
        }
        else
        {
            return false;
        }
    }
}
