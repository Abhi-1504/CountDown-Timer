package com.yoyi.android.countdowntimer;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import static com.yoyi.android.countdowntimer.MainActivity.mTimer;
import static com.yoyi.android.countdowntimer.MainActivity.pref;
import static com.yoyi.android.countdowntimer.MainActivity.sessionName;
import static com.yoyi.android.countdowntimer.MainActivity.setstr;
import static com.yoyi.android.countdowntimer.MainActivity.stats;

public class Sessions extends AppCompatActivity {

    private ListView listView;
    private EditText editTextInput;
    private String buffer;
    private int toDelete;
    static ArrayAdapter<String> adapter;
    static SharedPreferences.Editor editor = pref.edit();
    static SharedPreferences.Editor statsEditor = stats.edit();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        // Initializing objects
        listView = findViewById(R.id.dynamic);
        editTextInput = findViewById(R.id.input);


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, MainActivity.sessionName);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>=mTimer.sessionCompleted)
                {
                    message(i, adapter);
                }
            }
        });
    }


    public void message(int i, final ArrayAdapter adapter)
    {
        toDelete = i;
        new AlertDialog.Builder(this)
                .setTitle("Deleting Session")
                .setMessage("Are you sure you want to delete this Session")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.sessionName.remove(toDelete);

                        editor.clear();
                        editor.commit();

                        for (int j = 0; j < sessionName.size(); j++)
                        {
                            editor.putString(Integer.toString(j), sessionName.get(j));
                        }
                        editor.putInt("numberOfSessions", sessionName.size());
                        editor.apply();
                        editor.commit();


                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    public void run(View view)
    {
        // Getting the text from UI
        buffer = editTextInput.getText().toString().trim();

        if (!(buffer.matches("")))
        {
            editTextInput.setText("");
            sessionName.add(buffer);
            for (int i = 0; i < sessionName.size(); i++)
            {
                editor.putString(Integer.toString(i), sessionName.get(i));
            }
            editor.putInt("numberOfSessions", sessionName.size());
            editor.apply();
            editor.commit();

            listView.setAdapter(adapter);
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setSelection(adapter.getCount() - 1);
                }
            });
            mTimer.setUpNext();

            buffer = buffer.toLowerCase();

            String m;
            try
            {
                m = stats.getStringSet("7287nx", null).toString();
            }
            catch (Exception e)
            {
                m = "";
            }
            if (!(m.matches("")))
            {
                for (String name: stats.getStringSet("7287nx",null))
                {
                    setstr.add(name);
                }
                setstr.add(buffer);
            }
            else
            {
                setstr.add(buffer);
            }
            statsEditor.putInt(buffer, 0);
            statsEditor.putStringSet("7287nx",setstr);
            statsEditor.commit();

        }
    }
}
