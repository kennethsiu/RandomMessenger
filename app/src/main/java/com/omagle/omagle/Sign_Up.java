package com.omagle.omagle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_Up extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final String TAG = "Sign_up_activity";

    private Button signUpButton;
    private EditText email;
    private EditText passw;
    private EditText confPass;
    final String auth_failed = "Failed to create account";
    View view1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //Track when the user is signing in or out
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }

            }
        };


        email = (EditText) findViewById(R.id.enterEmail);
        passw = (EditText) findViewById(R.id.EnterPassword);
        confPass = (EditText) findViewById(R.id.ConfirmPassword);

        signUpButton = (Button) findViewById(R.id.beginSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailStr = email.getText().toString().trim();
                String passStr = passw.getText().toString().trim();
                String confPassStr = confPass.getText().toString().trim();
                createAccount(emailStr, passStr, confPassStr, view);
            }
        });
    }

    //Check if the password is longer than 7 chars
    private boolean isPasswordValid(String password) {
        if(!(password.length() > 7) || password.isEmpty())
            return false;
        return true;
    }

    //Go to the start chat page
    private void goToStartChat(View view) {
        Intent intent = new Intent(this, StartChat.class);
        startActivity(intent);
    }

    //Do necessary checks to see if email and password are correct, then create account
    public boolean createAccount(String emailStr, String passStr, String confPassStr, final View view) {

        View focusView = null;
        Boolean cancel = false;
        if (!(passStr.equals(confPassStr))) {
            Log.d(TAG, passw.getText().toString() + " " + confPass.getText().toString());
            Log.d(TAG, "Password not the same");
            passw.setError("The passwords are not the same");
            focusView = passw;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(passStr)) {
            Log.d(TAG, "invalid password");
            passw.setError("Invalid password");
            focusView = passw;
            cancel = true;
        }
        //Check for a valid email address
        if (!emailStr.contains("@ucsd.edu") || emailStr.isEmpty()) {
            Log.d(TAG, "invalid email");
            email.setError("Invalid email");
            focusView = email;
            cancel = true;
        }
        //If the information is not valid, set the appropriate erorr screen and do not submit
        if (cancel) {
            focusView.requestFocus();
            return false;
        } else {
            final String auth_failed = "Failed to create account";
            mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                            Toast.makeText(Sign_Up.this, auth_failed,
                                    Toast.LENGTH_SHORT).show();
                            }
                            else {goToStartChat(view);}
                        }
                    });
            return true;
        }
    }


}
