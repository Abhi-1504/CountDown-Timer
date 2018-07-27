package com.yoyi.android.countdowntimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.yoyi.android.countdowntimer.MainActivity.stats;

public class Stats extends AppCompatActivity {


    public static ArrayAdapter<String> statsAdapter;
    ArrayList<String> statsArray;
    ListView statsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        statsListView = findViewById(R.id.stats);


        statsArray = new ArrayList<>();
        statsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, statsArray);

        setStatsList();

        statsListView.setAdapter(statsAdapter);
    }

    public void setStatsList()
    {
        String buffer;
        int timeBuffer;
        String testString;
        try
        {
            testString = stats.getStringSet("7287nx", null).toString();
        }
        catch (Exception e)
        {
            testString = "";
        }

        if (!(testString.matches("")))
        {
            for(String name: stats.getStringSet("7287nx",null))
            {
                timeBuffer = stats.getInt(name, 0);
                if (timeBuffer!=0)
                {
                    timeBuffer = timeBuffer * 25*60*1000;
                }

                buffer = name+":                  " + String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(timeBuffer),
                        TimeUnit.MILLISECONDS.toMinutes(timeBuffer)-TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(timeBuffer)),
                        TimeUnit.MILLISECONDS.toSeconds(timeBuffer) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(timeBuffer)
                        ));

                statsArray.add(buffer);
            }
        }
    }
}
