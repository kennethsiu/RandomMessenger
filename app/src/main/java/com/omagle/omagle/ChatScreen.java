package com.omagle.omagle;

import android.database.DataSetObserver;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.*;

public class ChatScreen extends AppCompatActivity {
    private static final String TAG = "ChatScreen";
    private static ChatScreenArrayAdapter arrAdapt;
    private EditText messageText;
    private Button sendButton;
    private static ArrayList<String> textReceived;
    private FirebaseTranslater fbTranslater;

    //database related things
    private DatabaseReference myDatabase;
    MyUser newUser;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //database reference
        myDatabase = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG,myDatabase.toString());
        //get token and add new user to database
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        newUser = new MyUser(refreshedToken);
        myDatabase.child("users").child(newUser.getToken()).setValue(newUser);

        //match new user with a partner
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean success = false;
                DataSnapshot users = dataSnapshot.child("users");
                for(DataSnapshot snap : users.getChildren())
                {
                    MyUser potentialPartner = snap.getValue(MyUser.class);
                    //found someone who isn't matched yet
                    if(!newUser.getMatched()&&!potentialPartner.getMatched()&&newUser.getToken()!=potentialPartner.getToken())
                    {
                        newUser.setPartner(potentialPartner.getToken());
                        newUser.setMatched(true);
                        potentialPartner.setPartner(newUser.getToken());
                        potentialPartner.setMatched(true);
                        myDatabase.child("users").child(newUser.getToken()).setValue(newUser);
                        myDatabase.child("users").child(potentialPartner.getToken()).setValue(potentialPartner);
                        success = true;
                    }
                }
                if(!success)
                {
                    //print "sorry, noone is available" message
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        myDatabase.addListenerForSingleValueEvent(userListener);

        setContentView(R.layout.activity_chat_screen);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        fbTranslater = new FirebaseTranslater();
        sendButton = (Button) findViewById(R.id.sendButton);
        final ListView messageList = (ListView) findViewById(R.id.message_list);

        arrAdapt = new ChatScreenArrayAdapter(getApplicationContext(), R.layout.message_bubble_right);
        messageList.setAdapter(arrAdapt);

        messageText = (EditText) findViewById(R.id.edit_message);

        //commented out by Anu because I changed Message
        //Message message = new Message(messageText.getText().toString().trim(), true);
        messageText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //commented out by Anu because I commented out sendMessage
                    // /return sendMessage();

                }
                return false;
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //commented out by Anu because I commented out send Mesfage
                //sendMessage();
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //store message on the database
    private void storeMessage(final String texts) {
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("message")) {
                    DataSnapshot users = dataSnapshot.child("message");
                    boolean added = false;
                    for (DataSnapshot snap : users.getChildren()) {
                        //look through each message and see if it was sent from this user
                        Message m = snap.getValue(Message.class);
                        String sender = m.getSender();
                        //user send message before. maybe double texting??
                        if (sender.equals(newUser.getToken())) {
                            m.addText(texts);
                            myDatabase.child("message").child(m.getReceiver()).setValue(m);
                            added = true;
                        }
                    }
                    //user hasn't sent message before. new entry
                    if(!added)
                    {
                        Message newEntry = new Message(texts);
                        newEntry.setReceiver(newUser.getPartner());
                        newEntry.setSender(newUser.getToken());
                        myDatabase.child("message").child(newUser.getPartner()).setValue(newEntry);
                    }
                }
                //first ever message. create message field and add message
                else{
                    Message newEntry = new Message(texts);
                    newEntry.setReceiver(newUser.getPartner());
                    newEntry.setSender(newUser.getToken());
                    myDatabase.child("message").child(newUser.getPartner()).setValue(newEntry);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseReference messageDatabase = FirebaseDatabase.getInstance().getReference("message");
        messageDatabase.addValueEventListener(messageListener);
    }

    public void retrieveMessage() {
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot users = dataSnapshot.child("message");
                for (DataSnapshot snap : users.getChildren()) {
                    //look through each message and see if it was sent for this user
                    Message m = snap.getValue(Message.class);
                    String receiver = m.getReceiver();
                    //message for user found
                    if (receiver.equals(newUser.getToken())) {
                        //see if displayed on screen yet
                        if (!m.getDisplayed()) {
                            List<String> messagesContent = m.getText();
                            m.setDisplayed(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseReference messageDatabase = FirebaseDatabase.getInstance().getReference("message");
        messageDatabase.addValueEventListener(messageListener);
    }

    //commented out by Anu because I wrote new ones
    /*
    protected static boolean getMessage(Map<String, String> receivedMessage) {
        for (String str : receivedMessage.keySet()) {
            textReceived.add(receivedMessage.get(str));
            arrAdapt.add(new Message(receivedMessage.get(str).toString().trim(), false));
        }
        return true;
    }

    private boolean sendMessage() {
        Message message = new Message(messageText.getText().toString().trim(), true);
        arrAdapt.add(message);
        fbTranslater.send(message);
        messageText.setText("");
        return true;
    }*/

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ChatScreen Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}