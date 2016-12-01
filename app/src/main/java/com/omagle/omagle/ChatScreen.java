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

/*
These are the database related resources that we used
https://firebase.google.com/docs/database/android/start/
https://firebase.google.com/docs/database/android/read-and-write
https://firebase.google.com/docs/database/android/lists-of-data
 */
public class ChatScreen extends AppCompatActivity {
    private static final String TAG = "ChatScreen";
    //UI related information. buttons and such
    private static ChatScreenArrayAdapter arrAdapt;
    private EditText messageText;
    private Button sendButton;
    private Button exitButton;
    private ListView messageList;
    private ValueEventListener messageListener;

    //for customization purposes. theme and avatar
    protected static short theme = 0;
    protected static String avatar = "UCSD 1";
    private boolean firstMessage = true;

    private static final String BUMPED = "kjasdjf1290alks9124klalksdklf91239lkaskldf9012lkmzmqp102";
    private static final String MATCHED = "oiawer12041241203klasdklasmdklzxmvasldkflasdfamfzlkfq";
    private static final String pFound = "Chat Partner Found";


    //database
    private DatabaseReference myDatabase;
    //user of the app
    public MyUser newUser;
    //user's partner
    public MyUser partner;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //allows different themes for customization
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
        setContentView(R.layout.activity_chat_screen);

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

        //get snapshot of the database
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot users = dataSnapshot.child("users");
                //match the user with someone
                boolean success = matchUsers(users);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Something went wrong. Print out a message
                Log.w(TAG, "match users", databaseError.toException());
            }
        };
        myDatabase.addListenerForSingleValueEvent(userListener);

        //send message and exit chat buttons
        sendButton = (Button) findViewById(R.id.sendButton);
        exitButton = (Button) findViewById(R.id.exitButton);
        messageList = (ListView) findViewById(R.id.message_list);

        arrAdapt = new ChatScreenArrayAdapter(getApplicationContext(), R.layout.message_bubble_right_default);
        messageList.setAdapter(arrAdapt);
        messageList.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        messageText = (EditText) findViewById(R.id.edit_message);

        //see if user tried to send message through enter key
        messageText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //store the message onto the database
                    storeMessage(messageText.getText().toString().trim());
                    //display the sent message on the screen
                    sendMessage();
                    return true;
                }
                return false;
            }
        });

        //see if user tried to send message by clicking the button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store the message on database
                storeMessage(messageText.getText().toString().trim());
                //display the sent message on the screen
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

        //get snapshot of the database
        messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //receive the message from database
                retrieveMessage(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // something went wrong. log the message
                Log.w(TAG, "retreive message", databaseError.toException());
            }
        };
        DatabaseReference messageDatabase = FirebaseDatabase.getInstance().getReference();
        messageDatabase.addValueEventListener(messageListener);

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //match users from database
    public boolean matchUsers(DataSnapshot users)
    {
        //look through all uses
        for(DataSnapshot snap : users.getChildren())
        {
            MyUser potentialPartner = snap.getValue(MyUser.class);
            //found someone who isn't matched yet
            if(!newUser.getMatched()&&!potentialPartner.getMatched()&&!newUser.getToken().equals(potentialPartner.getToken()))
            {
                //give partner to user
                newUser.setPartner(potentialPartner.getToken());
                newUser.setMatched(true);
                //give user to partner
                potentialPartner.setPartner(newUser.getToken());
                potentialPartner.setMatched(true);
                //reflect the change on the database
                myDatabase.child("users").child(newUser.getToken()).setValue(newUser);
                myDatabase.child("users").child(potentialPartner.getToken()).setValue(potentialPartner);
                storeMessage(MATCHED);
                partner = potentialPartner;
                //update partner's avatar on user's size
                ImageView otherAvatar = (ImageView) findViewById(R.id.receiverAvatar);
                changeAvatar(otherAvatar,partner.getAvatar());
                makeToast(pFound);
                return true;
            }
        }
        return false;
    }

    //store message on the database
    public void storeMessage(final String texts) {
        //get snap shot of the database
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot users = dataSnapshot.child("users");
                for(DataSnapshot snap : users.getChildren()) {
                    MyUser potentialUpdate = snap.getValue(MyUser.class);
                    if(potentialUpdate.getToken().equals(newUser.getToken())&&potentialUpdate.getMatched()) {
                        //create message to be sent
                        Message newEntry = new Message(texts);
                        newEntry.setReceiver(potentialUpdate.getPartner());
                        newEntry.setSender(potentialUpdate.getToken());
                        //store the message on database
                        myDatabase.child("message").child(potentialUpdate.getPartner()).setValue(newEntry);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // something went wrong. log the message
                Log.w(TAG, "send message", databaseError.toException());
            }
        };
        DatabaseReference messageDatabase = FirebaseDatabase.getInstance().getReference();
        messageDatabase.addListenerForSingleValueEvent(messageListener);
    }

    //receive message from the database
    public String retrieveMessage(DataSnapshot dataSnapshot) {
        //look through every message on database
        DataSnapshot users = dataSnapshot.child("message");
        for (DataSnapshot snap : users.getChildren()) {
            Message m = snap.getValue(Message.class);
            String receiver = m.getReceiver();
            //see if the message was sent to the user
            if(receiver.equals(newUser.getToken()) && !m.getDisplayed())
            {
                String messages = m.getText();
                //partner exited app. user needs to exit chat too
                if (messages != null && messages.equals(BUMPED))
                    otherUserEnded();
                //message received. retrieve the message
                else if(messages != null) {
                    if (messages.equals(MATCHED)&&firstMessage){
                        firstMessage = false;
                        ImageView otherAvatar = (ImageView) findViewById(R.id.receiverAvatar);
                        DataSnapshot otherUser = dataSnapshot.child("users").child(m.getSender());
                        partner = otherUser.getValue(MyUser.class);
                        partner.setMatched(true);
                        newUser.setPartner(partner.getToken());
                        newUser.setMatched(true);
                        changeAvatar(otherAvatar,partner.getAvatar());
                        myDatabase.child("message").child(newUser.getToken()).removeValue();
                        makeToast(pFound);
                        return m.getText();
                    }
                    //display the message onto the screen
                    m.setSentMessage(false);
                    arrAdapt.add(m);
                    m.setDisplayed(true);
                    myDatabase.child("message").child(receiver).setValue(m);
                    return m.getText();
                }
            }
        }
        return "Nothing";
    }

    /* Populate UI with sent message */
    private boolean sendMessage() {
        //create the message and display on the screen
        Message message = new Message(messageText.getText().toString().trim());
        message.setSentMessage(true);
        arrAdapt.add(message);
        messageText.setText("");
        return true;
    }

    /**
     * implement the App Indexing API..
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("ChatScreen Page")
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

        // implement the App Indexing API.
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

    //need to exit the chat
    @Override
    public void onBackPressed(){
        //make other user exit the chat
        bumpOtherUser();
        //delete the chat
        deleteChat();
        super.onBackPressed();
        finish();
    }

    //user wants to exit. delete the chatscreen
    public void deleteChat() {
        //reset everything on database
        myDatabase.child("message").child(newUser.getToken()).removeValue();
        newUser.setMatched(false);
        newUser.setPartner("Default partner");
        myDatabase.child("users").child(newUser.getToken()).removeValue();
        myDatabase.removeEventListener(messageListener);
        firstMessage = true;
        this.finish();
    }

    /* Force other user to exit chat when you exit */
    public void bumpOtherUser(){
        Log.d("BUMP",newUser.getPartner());
        storeMessage(BUMPED);
    }

    /* End your chat if the other user ended the chat */
    public void otherUserEnded() {
        myDatabase.child("message").child(newUser.getToken()).removeValue();
        String bumpedMessage = "Other user has ended the chat.";
        makeToast(bumpedMessage);
        deleteChat();
        super.onBackPressed();
    }

    public void makeToast(String m) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getApplicationContext(), m, duration);
        toast.show();
    }

    /* Update the avatar with the given choice */
    public void changeAvatar(ImageView imageView, String choice){
        //different choises for the avatar
        switch (choice){
            case "UCSD 1":
                imageView.setImageResource(R.drawable.default_avatar);
                break;
            case "UCSD 2":
                imageView.setImageResource(R.drawable.ucsd_avatar2);
                break;
            case "Warren":
                imageView.setImageResource(R.drawable.warren_avatar);
                break;
            case "Marshall":
                imageView.setImageResource(R.drawable.marshall_avatar);
                break;
            case "Muir":
                imageView.setImageResource(R.drawable.muir_avatar);
                break;
            case "Revelle":
                imageView.setImageResource(R.drawable.revelle_avatar);
                break;
            case "ERC":
                imageView.setImageResource(R.drawable.erc_avatar);
                break;
            case "Triton":
                imageView.setImageResource(R.drawable.triton_avatar);
                break;
            case "Sixth":
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