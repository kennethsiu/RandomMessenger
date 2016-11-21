package com.omagle.omagle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartChat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_chat);

        final Button signOutButton = (Button) findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut(v);
            }
        });

        final Button startChatButton = (Button) findViewById(R.id.startChatButton);
        startChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startChat(v);
            }
        });
    }
    private void signOut(View view) {
        Intent signOutIntent = new Intent(this, LoginActivity.class);
        startActivity(signOutIntent);
        finish();
    }

    private void startChat(View view){
        Intent startChatIntent = new Intent(this, ChatScreen.class);
        startActivity(startChatIntent);
    }


}
