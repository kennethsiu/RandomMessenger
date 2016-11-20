package com.omagle.omagle;



import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class ChatScreen extends AppCompatActivity {
    private static final String TAG = "ChatScreen";
    private static ChatScreenArrayAdapter arrAdapt;
    private EditText messageText;
    private Button sendButton;
    private Button exitButton;

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
        //get token and add new user to database
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        newUser = new MyUser(refreshedToken);
        myDatabase.child("users").child(newUser.getToken()).setValue(newUser);

        //match new user with a partner
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot users = dataSnapshot.child("users");
                boolean success = matchUsers(users);
                if(!success) {
                    // print out "sorry no match message" on chat screen
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

        sendButton = (Button) findViewById(R.id.sendButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        final ListView messageList = (ListView) findViewById(R.id.message_list);

        arrAdapt = new ChatScreenArrayAdapter(getApplicationContext(), R.layout.message_bubble_right);
        messageList.setAdapter(arrAdapt);

        messageText = (EditText) findViewById(R.id.edit_message);

        //send message
        messageText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.d(TAG, "trying to send message: onKey");
                    storeMessage(messageText.getText().toString().trim());
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        //send message
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "trying to send message: onClick");
                storeMessage(messageText.getText().toString().trim());
                sendMessage();
            }
        });
        //when pressing the exit chat button
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "trying to exit chat: onClick");
                onBackPressed();

            }
        });

        //receiver mesage
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                retrieveMessage(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseReference messageDatabase = FirebaseDatabase.getInstance().getReference();
        messageDatabase.addValueEventListener(messageListener);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //match users from database
    private boolean matchUsers(DataSnapshot users)
    {
        Log.d(TAG, "trying to match users");
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
                Log.d(TAG, "matched users");
                return true;
            }
        }
        return false;
    }

    //store message on the database
    private void storeMessage(final String texts) {
        Log.d(TAG, "trying storing message");
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot users = dataSnapshot.child("users");
                for(DataSnapshot snap : users.getChildren()) {
                    MyUser potentialUpdate = snap.getValue(MyUser.class);
                    if(potentialUpdate.getToken().equals(newUser.getToken())&&potentialUpdate.getMatched()) {
                        Message newEntry = new Message(texts);
                        newEntry.setReceiver(potentialUpdate.getPartner());
                        newEntry.setSender(potentialUpdate.getToken());
                        myDatabase.child("message").child(potentialUpdate.getPartner()).setValue(newEntry);
                        Log.d(TAG, "Stored message");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        DatabaseReference messageDatabase = FirebaseDatabase.getInstance().getReference();
        messageDatabase.addListenerForSingleValueEvent(messageListener);
    }

    public void retrieveMessage(DataSnapshot dataSnapshot) {
        DataSnapshot users = dataSnapshot.child("message");
        Log.d(TAG, "trying to receive message");
        for (DataSnapshot snap : users.getChildren()) {
            //look through each message and see if it was sent for this user
            Message m = snap.getValue(Message.class);
            String receiver = m.getReceiver();
            String sender = m.getSender();
            if(receiver.equals(newUser.getToken())&&sender.equals(newUser.getPartner())&&!m.getDisplayed())
            {
                String messages = m.getText();
                if(messages != null) {
                    m.setSentMessage(false);
                    arrAdapt.add(m);
                    m.setDisplayed(true);
                    myDatabase.child("message").child(receiver).setValue(m);
                    Log.d(TAG,"retrieved message");
                }
            }
        }
    }


    private boolean sendMessage() {
        Message message = new Message(messageText.getText().toString().trim());
        message.setSentMessage(true);
        arrAdapt.add(message);
        messageText.setText("");
        return true;
    }

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
        deleteChat();
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }



    @Override
    public void onBackPressed(){
        deleteChat();
        super.onBackPressed();
    }

    private void goToStartChat() {
        Intent intent = new Intent(this, StartChat.class);
        startActivity(intent);
    }

    public void deleteChat() {
        myDatabase.child("message").child(newUser.getToken()).removeValue();
        newUser.setMatched(false);
        newUser.setPartner("Default partner");
        myDatabase.child("users").child(newUser.getToken()).removeValue();
    }



}