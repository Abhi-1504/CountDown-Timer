package com.yoyi.android.countdowntimer;


import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WorkTimer extends Timer
{
    private TextView txtMessage;
    private LinearLayout llUserInput, llChronometer;
    private RelativeLayout rlBackground;
    private Button btnReset;
    private int isPause;

    @Override
    public int startOrPauseTimer()
    {
        isPause = super.startOrPauseTimer();
        if (isPause==1)
        {
            btnStartAndPause.setText("Start");
          //  rlBackground.setBackgroundColor(Color.parseColor("#5fc497"));
        }
        else if (isPause==0)
        {
            btnStartAndPause.setText("Pause");
          //  rlBackground.setBackgroundColor(Color.parseColor("#87d9b5"));
        }
        else {
            return 1;
        }
        llUserInput.setVisibility(View.GONE);
        llChronometer.setVisibility(View.VISIBLE);
        btnReset.setEnabled(true);
        txtMessage.setText("Countdown");
        btnReset.setBackgroundColor(Color.parseColor("#1c1c1c"));
        btnReset.setTextColor(Color.parseColor("#b8e3be"));
        return isPause;
    }

    @Override
    public void resetTimer()
    {
        super.resetTimer();
        llUserInput.setVisibility(View.VISIBLE);
        llChronometer.setVisibility(View.GONE);
        btnReset.setEnabled(false);
        btnStartAndPause.setEnabled(true);
        btnStartAndPause.setText("Start");
        txtMessage.setText("Set timer");
        btnReset.setBackgroundColor(Color.parseColor("#272626"));
        btnReset.setTextColor(Color.parseColor("#737473"));

        btnStartAndPause.setBackgroundColor(Color.parseColor("#1c1c1c"));
        btnStartAndPause.setTextColor(Color.parseColor("#b8e3be"));
    }

    public void setTimer(MediaPlayer mp, EditText etxtHours, EditText etxtMinutes,
                         EditText etxtSeconds, TextView txtTimer, TextView message, Button btnStartAndPause,
                         LinearLayout llUserInput, LinearLayout llChronometer, RelativeLayout rlBackground, Button btnReset)
    {
     super.setTimer(mp, etxtHours, etxtMinutes, etxtSeconds, txtTimer,
             btnStartAndPause);
        this.llUserInput = llUserInput;
        this.llChronometer = llChronometer;
        this.rlBackground = rlBackground;
        this.btnReset = btnReset;
        this.txtMessage = message;
    }
}