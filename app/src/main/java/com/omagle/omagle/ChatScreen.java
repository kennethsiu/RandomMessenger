package com.omagle.omagle;

import android.database.DataSetObserver;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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

import java.util.List;

public class ChatScreen extends AppCompatActivity {
    private static final String TAG = "CharScreen";
    private ChatScreenArrayAdapter arrAdapt;
    private EditText messageText;
    private Button sendButton;
    //database related things
    private DatabaseReference myDatabase;
    MyUser newUser;

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
                boolean success = false;
                DataSnapshot users = dataSnapshot.child("users");
                for(DataSnapshot snap : users.getChildren())
                {
                    MyUser potentialPartner = snap.getValue(MyUser.class);
                    //found someone who isn't matched yet
                    if(!potentialPartner.getMatched())
                    {
                        newUser.setPartner(potentialPartner);
                        newUser.setMatched(true);
                        potentialPartner.setPartner(newUser);
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

        //Commented out by Anu because I changed Message class
        //Message message = new Message(messageText.getText().toString().trim());

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


    //store message on the database
    private void storeMessage(final String texts)
    {
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot users = dataSnapshot.child("message");
                boolean added = false;
                for(DataSnapshot snap : users.getChildren())
                {
                    //look through each message and see if it was sent from this user
                    Message m = snap.getValue(Message.class);
                    MyUser sender = m.getSender();
                    //user send message before. maybe double texting??
                    if(sender.getToken().equals(newUser.getToken()))
                    {
                        m.addText(texts);
                        myDatabase.child("message").child(sender.getPartner().getToken()).setValue(m);
                        added = true;
                    }
                }
                //user hasn't send message before. new entry
                if(!added)
                {
                    Message newEntry = new Message(texts);
                    newEntry.setReceiver(newUser.getPartner());
                    newEntry.setSender(newUser);
                    myDatabase.child("message").child(newUser.getPartner().getToken()).setValue(newEntry);
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

    public void retrieveMessage()
    {
        ValueEventListener messageListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot users = dataSnapshot.child("message");
                for(DataSnapshot snap : users.getChildren())
                {
                    //look through each message and see if it was sent for this user
                    Message m = snap.getValue(Message.class);
                    MyUser receiver = m.getReceiver();
                    //message for user found
                    if(receiver.getToken().equals(newUser.getToken()))
                    {
                        //see if displayed on screen yet
                        if(!m.getDisplayed()) {
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

    //commented out by Anu because I changed Message class.
    private boolean sendMessage(){
        //arrAdapt.add(new Message(messageText.getText().toString().trim()));
        //messageText.setText("");
        return true;
    }

}
