package com.omagle.omagle;



import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.util.Log;
import android.widget.Toast;

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
    private ListView messageList;
    private ValueEventListener messageListener;

    protected static short theme = 0;
    protected static String avatar = "UCSD 1";
    private boolean firstMessage = true;

    private static final String BUMPED = "kjasdjf1290alks9124klalksdklf91239lkaskldf9012lkmzmqp102";

    //database related things
    private DatabaseReference myDatabase;
    public MyUser newUser;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        switch (theme){
            case 1:
                setTheme(R.style.ChristmasTheme); break;
            case 2:
                setTheme(R.style.ThanksgivingTheme); break;
            case 3:
                setTheme(R.style.EyebleedTheme); break;
            case 4:
                setTheme(R.style.UnderwaterTheme); break;
            default:
                setTheme(R.style.DefaultTheme); break;

        }

        //database reference
        myDatabase = FirebaseDatabase.getInstance().getReference();
        //get token and add new user to database
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        newUser = new MyUser(refreshedToken);

        // Get the avatar
        ImageView imageView = (ImageView) findViewById(R.id.senderAvatar);
        Log.d(TAG, imageView.toString());
        newUser.setAvatar(avatar);
        changeAvatar(imageView, avatar);

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

        sendButton = (Button) findViewById(R.id.sendButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        messageList = (ListView) findViewById(R.id.message_list);

        arrAdapt = new ChatScreenArrayAdapter(getApplicationContext(), R.layout.message_bubble_right_default);
        messageList.setAdapter(arrAdapt);
        messageList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        messageText = (EditText) findViewById(R.id.edit_message);

        //send message
        messageText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Log.d(TAG, "trying to send message: onKey");
                    storeMessage(messageText.getText().toString().trim());
                    Log.d(TAG, "after store message");
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

        arrAdapt.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                Log.d("arrAdaptListener","inside");
                super.onChanged();
                messageList.setSelection(arrAdapt.getCount() - 1);
            }
        });

        //receiver mesage
        messageListener = new ValueEventListener() {
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
    public boolean matchUsers(DataSnapshot users)
    {
        Log.d(TAG, "trying to match users");
        for(DataSnapshot snap : users.getChildren())
        {
            MyUser potentialPartner = snap.getValue(MyUser.class);
            //found someone who isn't matched yet
            if(!newUser.getMatched()&&!potentialPartner.getMatched()&&!newUser.getToken().equals(potentialPartner.getToken()))
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
    public void storeMessage(final String texts) {
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

    public String retrieveMessage(DataSnapshot dataSnapshot) {
        DataSnapshot users = dataSnapshot.child("message");
        Log.d(TAG, "trying to receive message");
        for (DataSnapshot snap : users.getChildren()) {
            //look through each message and see if it was sent for this user
            Message m = snap.getValue(Message.class);
            String receiver = m.getReceiver();
            if(receiver.equals(newUser.getToken()) && !m.getDisplayed())
            {
                String messages = m.getText();
                if (messages != null && messages.equals(BUMPED))
                    otherUserEnded();
                else if(messages != null) {
                    if (firstMessage){
                        firstMessage = false;
                        ImageView otherAvatar = (ImageView) findViewById(R.id.receiverAvatar);
                        DataSnapshot otherUser = dataSnapshot.child("users").child(m.getSender());
                        MyUser other = otherUser.getValue(MyUser.class);
                        changeAvatar(otherAvatar,other.getAvatar());
                    }
                    m.setSentMessage(false);
                    arrAdapt.add(m);
                    m.setDisplayed(true);
                    myDatabase.child("message").child(receiver).setValue(m);
                    Log.d(TAG,"retrieved message");
                    return m.getText();
                }
            }
        }
        return "Nothing";
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
        bumpOtherUser();
        deleteChat();
        super.onStop();
        finish();

         //ATTENTION: This was auto-generated to implement the App Indexing API.
         //See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }




    @Override
    public void onBackPressed(){
        bumpOtherUser();
        deleteChat();
        super.onBackPressed();
        finish();
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
        myDatabase.removeEventListener(messageListener);
        firstMessage = true;
        this.finish();
    }

    /*
         * A fix for an error with bumping another user. Pulled from:
         * http://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa
         */
    public void bumpOtherUser(){
        Log.d("BUMP",newUser.getPartner());
        storeMessage(BUMPED);
    }

    public void otherUserEnded() {
        myDatabase.child("message").child(newUser.getToken()).removeValue();
        int duration = Toast.LENGTH_SHORT;
        String bumpedMessage = "Other user has ended the chat.";
        Toast toast = Toast.makeText(getApplicationContext(), bumpedMessage, duration);
        toast.show();
        deleteChat();
        super.onBackPressed();
    }

    public void changeAvatar(ImageView imageView, String choice){
        switch (avatar){
            case "UCSD 1":
                newUser.setAvatar(choice);
                imageView.setImageResource(R.drawable.default_avatar);
                break;
            case "UCSD 2":
                newUser.setAvatar(choice);
                imageView.setImageResource(R.drawable.ucsd_avatar2);
                break;
            case "Warren":
                newUser.setAvatar(choice);
                imageView.setImageResource(R.drawable.warren_avatar);
                break;
            case "Marshall":
                newUser.setAvatar(choice);
                imageView.setImageResource(R.drawable.marshall_avatar);
                break;
            case "Muir":
                newUser.setAvatar(choice);
                imageView.setImageResource(R.drawable.muir_avatar);
                break;
            case "Revelle":
                newUser.setAvatar(choice);
                imageView.setImageResource(R.drawable.revelle_avatar);
                break;
            case "ERC":
                newUser.setAvatar(choice);
                imageView.setImageResource(R.drawable.erc_avatar);
                break;
            case "Triton":
                newUser.setAvatar(choice);
                imageView.setImageResource(R.drawable.triton_avatar);
                break;
            case "Sixth":
                newUser.setAvatar(choice);
                imageView.setImageResource(R.drawable.sixth_avatar);
                break;
        }
    }
    /*
     * A fix for an error with bumping another user. Pulled from:
     * http://stackoverflow.com/questions/7469082/getting-exception-illegalstateexception-can-not-perform-this-action-after-onsa
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }
}