package com.yoyi.android.countdowntimer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Creating objects and Variables to link Views and store values
    TextView timer, message, txtSessionName ,minute;
    private Button startAndPause, reset;
    private LinearLayout llUserInput, llChronometer;
    private RelativeLayout rlBackground;
    private MediaPlayer mp;
    static WorkTimer mTimer;
    public static ArrayList<String> sessionName = new ArrayList<>();

    private TextView next1, next2, next3;

    static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences(getPackageName()+"Test3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();


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

        next1 = findViewById(R.id.next_1);
        next2 = findViewById(R.id.next_2);
        next3 = findViewById(R.id.next_3);

        //Disable reset button on Create
        reset.setEnabled(false);

        startAndPause.setBackgroundColor(Color.parseColor("#1c1c1c"));
        startAndPause.setTextColor(Color.parseColor("#b8e3be"));

        reset.setBackgroundColor(Color.parseColor("#272626"));
        reset.setTextColor(Color.parseColor("#737473"));
        

        mTimer = new WorkTimer();

        Log.e( "onCreate: ", String.valueOf(pref.getInt("workSession",0)));
        Log.e( "onCreate: ", String.valueOf(pref.getInt("sessionCompleted",0)));
        Log.e( "onCreate: ", String.valueOf(pref.getInt("numberOfSessions",0)));

        for(int j = 0; j <pref.getInt("numberOfSessions",0); j++)
        {
            Log.e( "run:1 ", pref.getString(Integer.toString(j),null));
        }

        for(int j = 0; j <pref.getInt("numberOfSessions",0); j++)
        {
            sessionName.add(pref.getString(Integer.toString(j),null));
        }


        for (int i = 0; i < sessionName.size(); i++)
        {
            editor.putString(Integer.toString(i), sessionName.get(i));
        }
        editor.putInt("numberOfSessions", sessionName.size());
        editor.apply();
        editor.commit();


        for(int j = 0; j <pref.getInt("numberOfSessions",0); j++)
        {
            Log.e( "run:2 ", pref.getString(Integer.toString(j),null));
        }


        mTimer.setList(sessionName);
        minute.setText(mTimer.getPomodoroSessionNumber());
        message.setText("Start your work");
        mTimer.setTimer(mp, minute, timer, message, startAndPause, llUserInput, llChronometer,
                rlBackground, reset, txtSessionName, next1, next2, next3);

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

        try
        {
            txtSessionName.setText(sessionName.get(mTimer.workSession));
        }
        catch (Exception e)
        {
            txtSessionName.setText("Unnamed");
        }
        mTimer.setUpcoming();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Sessions:
                Intent intentSessions = new Intent(this, Sessions.class);
                startActivity(intentSessions);
                break;
            case R.id.Categories:
                Toast.makeText(this, "This functionality is yet to come", Toast.LENGTH_LONG).show();
                break;
            case R.id.ResetTimer:
                mTimer.Reset();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}