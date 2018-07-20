package com.yoyi.android.countdowntimer;

import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public interface TimerCalls {
    public int startOrPauseTimer();
    public void resetTimer();
    public void setTimer(MediaPlayer mp, TextView etxtMinutes,
                         TextView txtTimer, Button btnStartAndPause);
}
