package com.omagle.omagle;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class ChatScreen extends AppCompatActivity {

    private static ChatScreenArrayAdapter arrAdapt;
    private EditText messageText;
    private Button sendButton;
    private static ArrayList<String> textReceived;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        sendButton = (Button) findViewById(R.id.sendButton);
        final ListView messageList = (ListView) findViewById(R.id.message_list);

        arrAdapt = new ChatScreenArrayAdapter(getApplicationContext(), R.layout.message_bubble);
        messageList.setAdapter(arrAdapt);

        messageText = (EditText) findViewById(R.id.edit_message);
        Message message = new Message(messageText.getText().toString().trim(), true);
        messageText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendMessage();
                }
                return false;
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        messageList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        messageList.setAdapter(arrAdapt);

        arrAdapt.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                messageList.setSelection(arrAdapt.getCount() - 1);
            }
        });
    }

    protected static boolean getMessage(Map<String, String> receivedMessage) {
        for(String str : receivedMessage.keySet())
        {
            textReceived.add(receivedMessage.get(str));
            arrAdapt.add(new Message(receivedMessage.get(str).toString().trim(), false));
        }
        return true;
    }
    private boolean sendMessage(){
        arrAdapt.add(new Message(messageText.getText().toString().trim(), true));
        //TODO Send to app server
        messageText.setText("");
        return true;
    }
}
