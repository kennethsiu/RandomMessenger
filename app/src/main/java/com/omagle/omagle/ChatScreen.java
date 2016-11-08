package com.omagle.omagle;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Map;import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class ChatScreen extends AppCompatActivity {

    private static ChatScreenArrayAdapter arrAdapt;
    private EditText messageText;
    private Button sendButton;
    private static ArrayList<String> textReceived = new ArrayList<String>();
    private FirebaseTranslater fbTranslater;


 /*   private static class HandlerExtension extends Handler {

        private final WeakReference<ShowSomethingActivity> currentActivity;

        public HandlerExtension(ShowSomethingActivity activity){
            currentActivity = new WeakReference<ShowSomethingActivity>(activity);
        }

        @Override
        public void handleMessage(Message message){
            ShowSomethingActivity activity = currentActivity.get();
            if (activity!= null){
                activity.updateResults(message.getData().getString("result"));
            }
        }
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        fbTranslater = new FirebaseTranslater();
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

    /*    runOnUiThread(new Runnable() {
            @Override
            public void run() {
                arrAdapt.registerDataSetObserver(new DataSetObserver() {
                    @Override
                    public void onChanged() {
                        super.onChanged();
                        messageList.setSelection(arrAdapt.getCount() - 1);
                    }
                });
            }
        }); */

    }

    //protected static boolean getMessage(RemoteMessage.Notification receivedMessage){
    public static boolean getMessage(Map<String, String> receivedMessage) {
        for(String str : receivedMessage.keySet())
        {
            textReceived.add(receivedMessage.get(str));
            //textReceived.add(receivedMessage.getBody());
            //arrAdapt.add(new Message(receivedMessage.getBody().trim(), false));
            System.out.println(receivedMessage.get(str));
            arrAdapt.add(new Message(receivedMessage.get(str).toString().trim(), false));
        }
        return true;
    }
    private boolean sendMessage(){
        Message message = new Message(messageText.getText().toString().trim(), true);
        arrAdapt.add(message);
        fbTranslater.send(message);
        messageText.setText("");
        return true;
    }
}
