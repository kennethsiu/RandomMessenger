package com.omagle.omagle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class StartChat extends AppCompatActivity {

    private FirebaseAuth mAuth;

    /**
     * @param savedInstanceState
     *
     * After signing in, go to the main menu screen, with the options to start a chat,
     * go to settings, and to sign out of the account and go back to login page.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_chat);
        mAuth = FirebaseAuth.getInstance();

        /*Sign out button displays on button left*/
        final Button signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(v);
            }
        });

        /*Chat button displays on middle screen*/
        final Button startChatButton = (Button) findViewById(R.id.startChatButton);
        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startChat(v);
            }
        });

        /*Settings button appears on bottom right*/
        final Button settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings(v);
            }
        });
    }

    /*Sign out the currently signed out user if the sign out button is pushed.*/
    private void signOut(View view) {
        Intent signOutIntent = new Intent(this, LoginActivity.class);
        mAuth.signOut();
        startActivity(signOutIntent);
        finish();
    }

    /*Go to messaging screen*/
    private void startChat(View view){
        Intent startChatIntent = new Intent(this, ChatScreen.class);
        startActivity(startChatIntent);
    }
    /*Go to the settings screen*/
    private void settings(View view){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
    }

}
