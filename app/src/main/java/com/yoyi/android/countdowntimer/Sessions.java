package com.yoyi.android.countdowntimer;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import static com.yoyi.android.countdowntimer.MainActivity.mTimer;

public class Sessions extends AppCompatActivity {

    private ListView listView;
    private EditText editTextInput;
    private String buffer;
    private int toDelete;
    static ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        // Initializing objects
        listView = findViewById(R.id.dynamic);
        editTextInput = findViewById(R.id.input);


        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, MainActivity.sName);

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
                        MainActivity.sName.remove(toDelete);
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
            MainActivity.sName.add(buffer);
            listView.setAdapter(adapter);
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setSelection(adapter.getCount() - 1);
                }
            });
            mTimer.setUpNext();
        }
    }


}
