package com.yoyi.android.countdowntimer;


import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkTimer extends Timer
{
    private TextView txtMessage, txtSessionName;
    private LinearLayout llUserInput, llChronometer;
    private RelativeLayout rlBackground;
    private Button btnReset;
    private int isPause;
    private ArrayList<String> sessionName = new ArrayList<>();

    public void setList(ArrayList sessionName)
    {
        this.sessionName = sessionName;
    }

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
        txtSessionName.setText(sessionName.get(workSession));
        if (!isBreak)
        {
            txtSessionName.setVisibility(View.VISIBLE);
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
        txtSessionName.setVisibility(View.INVISIBLE);
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

        if (!isBreak)
        {
            txtMessage.setText("Start your work");
        }
        else if ((workSession%4)==0 && isBreak)
        {
            txtMessage.setText("Take a long break");
        }
        else
        {
            txtMessage.setText("Take a break");
        }
    }

    public void setTimer(MediaPlayer mp, TextView etxtMinutes,
                         TextView txtTimer, TextView message, Button btnStartAndPause,
                         LinearLayout llUserInput, LinearLayout llChronometer, RelativeLayout rlBackground, Button btnReset, TextView txtSessionName)
    {
     super.setTimer(mp, etxtMinutes, txtTimer,
             btnStartAndPause);
        this.llUserInput = llUserInput;
        this.llChronometer = llChronometer;
        this.rlBackground = rlBackground;
        this.btnReset = btnReset;
        this.txtMessage = message;
        this.txtSessionName = txtSessionName;
    }
}