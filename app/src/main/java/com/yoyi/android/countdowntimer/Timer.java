package com.yoyi.android.countdowntimer;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import java.util.concurrent.TimeUnit;

import static com.yoyi.android.countdowntimer.MainActivity.pref;
import static com.yoyi.android.countdowntimer.MainActivity.sessionName;
import static com.yoyi.android.countdowntimer.MainActivity.stats;

public class Timer implements TimerCalls{
    private static final String FORMAT = "%02d:%02d:%02d";

    protected long timeInMilliSeconds;
    protected int seconds, minutes, hours;
    protected CountDownTimer countDownTimer;
    protected boolean isTimerRunning, isFirstRun = true;
    protected MediaPlayer mp;
    protected TextView txtTimer, etxtMinutes;
    protected Button btnStartAndPause;
    protected int workSession;
    protected int sessionCompleted;
    protected boolean isBreak;
    protected String pomodoroSessionNumber;
    private SharedPreferences.Editor editor = pref.edit();
    private int tempSessionNo;
    public Timer()
    {
        try
        {
            workSession = pref.getInt("workSession",0);
            sessionCompleted = workSession;
        }
        catch (Exception e)
        { }
    }


    public void Reset()
    {
        editor.clear();
        editor.commit();
        workSession = 0;
        sessionCompleted = 0;
        isBreak = false;
        setWork();
        etxtMinutes.setText(getPomodoroSessionNumber());
    }

    protected String getPomodoroSessionNumber()
    {
        pomodoroSessionNumber = "Pomodoro " + (workSession+1);
        return pomodoroSessionNumber;
    }

    // Set time for one pomodoro session
    protected void setWork()
    {
        timeInMilliSeconds = 25*60*1000;
    }

    // Set time for one short break
    protected void setBreak()
    {
        if ((workSession%4)!=0)
        {
            timeInMilliSeconds = 5*60*1000;
        }
        else
        {
            timeInMilliSeconds = 30*60*1000;
        }
    }


    @Override
    public void setTimer(MediaPlayer mp, TextView etxtMinutes,
                         TextView txtTimer, Button btnStartAndPause) {
        this.mp = mp;
        this.etxtMinutes = etxtMinutes;
        this.txtTimer = txtTimer;
        this.btnStartAndPause = btnStartAndPause;
    }

    @Override
    public int startOrPauseTimer()
    {
        if (!isBreak)
        {
            setWork();
        }
        else
        {
            setBreak();
        }

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
                btnStartAndPause.setEnabled(false);
                isTimerRunning = false;
                mp.start();
                btnStartAndPause.setBackgroundColor(Color.parseColor("#272626"));
                btnStartAndPause.setTextColor(Color.parseColor("#737473"));
                if (!isBreak)
                {
                    try
                    {
                        tempSessionNo = stats.getInt(sessionName.get(workSession).toLowerCase(), 0);
                        Sessions.statsEditor.putInt(sessionName.get(workSession).toLowerCase() , tempSessionNo+1);
                        Sessions.statsEditor.commit();
                        tempSessionNo = stats.getInt(sessionName.get(workSession).toLowerCase(), 0);
                        Log.e("onFinish: ", Integer.toString(tempSessionNo));
                    }
                    catch (Exception e)
                    { }


                    try
                    {
                        MainActivity.sessionName.set(workSession, MainActivity.sessionName.get(workSession)+" [Completed]");
                        Sessions.adapter.notifyDataSetChanged();
                    }
                    catch (Exception e)
                    {
                        MainActivity.sessionName.add("Unnamed [Completed]");
                    }


                    isBreak = true;
                    etxtMinutes.setText(getPomodoroSessionNumber()+" Completed");
                    workSession++;
                }
                else
                {
                    sessionCompleted++;
                    isBreak = false;
                    etxtMinutes.setText(getPomodoroSessionNumber());
                }

                for (int i = 0; i < sessionName.size(); i++)
                {
                    editor.putString(Integer.toString(i), sessionName.get(i));
                }
                editor.putInt("numberOfSessions", sessionName.size());
                editor.putInt("workSession", workSession);
                editor.putInt("sessionCompleted", sessionCompleted);
                editor.commit();
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
        if (isTimerRunning)
        {
            countDownTimer.onFinish();
        }
        isTimerRunning = false;
        isFirstRun = true;
   //     txtTimer.setText("00:00:00");
    }
    //
}
