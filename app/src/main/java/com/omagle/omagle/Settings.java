package com.omagle.omagle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class Settings extends AppCompatActivity {

    private DatabaseReference myDatabase;
    private String avt;
    private String them;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDatabase = FirebaseDatabase.getInstance().getReference();

        //String userId = firebase.auth().currentUser.uid;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        avt = myDatabase.child("Profiles").child(userID).child("Avatar").getKey();
        them = myDatabase.child("Profiles").child(userID).child("Theme").getKey();

        //The following three lines are for a feature that was scrapped.
        //EditText nameText = (EditText) findViewById(R.id.nameEdit);
        //EditText majorText = (EditText) findViewById(R.id.majorEdit);
        //EditText ageText = (EditText) findViewById(R.id.ageEdit);

        //Figure out which radio button needs to be checked when the settings page is opened.
        RadioButton toCheck = (RadioButton) findViewById(R.id.Default);
        switch(them) {
            case "Default":
                toCheck = (RadioButton) findViewById(R.id.Default);

            case "Christmas":
                toCheck = (RadioButton) findViewById(R.id.Christmas);

            case "Thanksgiving":
                toCheck = (RadioButton) findViewById(R.id.Thanksgiving);

            case "Underwater":
                toCheck = (RadioButton) findViewById(R.id.Underwater);

            case "Eye_Bleed":
                toCheck = (RadioButton) findViewById(R.id.Eye_Bleed);
        }


        toCheck.setChecked(true);

        final ImageView avImage = (ImageView) findViewById(R.id.avatar_image);

        //Added by MinhTuan
        final Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabaseInfo();
                goBack(v);
                finish();
            }
        });

        // Populate spinner, used tutorial from Android at
        // "https://developer.android.com/guide/topics/ui/controls/spinner.html"
        Spinner spinner = (Spinner) findViewById(R.id.avatar_spinner);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.avatar_options, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String avatar = parent.getItemAtPosition(pos).toString();
                switch (avatar) {
                    case "UCSD 1":
                        ChatScreen.avatar = avatar;
                        avImage.setImageResource(R.drawable.default_avatar);

                        break;
                    case "UCSD 2":
                        ChatScreen.avatar = avatar;
                        avImage.setImageResource(R.drawable.ucsd_avatar2);
                        break;
                    case "Warren":
                        ChatScreen.avatar = avatar;
                        avImage.setImageResource(R.drawable.warren_avatar);
                        break;
                    case "Marshall":
                        ChatScreen.avatar = avatar;
                        avImage.setImageResource(R.drawable.marshall_avatar);
                        break;
                    case "Muir":
                        ChatScreen.avatar = avatar;
                        avImage.setImageResource(R.drawable.muir_avatar);
                        break;
                    case "Revelle":
                        ChatScreen.avatar = avatar;
                        avImage.setImageResource(R.drawable.revelle_avatar);
                        break;
                    case "ERC":
                        ChatScreen.avatar = avatar;
                        avImage.setImageResource(R.drawable.erc_avatar);
                        break;
                    case "Triton":
                        ChatScreen.avatar = avatar;
                        avImage.setImageResource(R.drawable.triton_avatar);
                        break;
                    case "Sixth":
                        ChatScreen.avatar = avatar;
                        avImage.setImageResource(R.drawable.sixth_avatar);
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final RadioGroup rGroup = (RadioGroup) findViewById(R.id.ThemeButtons);
        rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                // This puts the value (true/false) into the variable
                boolean isChecked = checkedRadioButton.isChecked();
                // If the radiobutton that has changed in check state is now checked...
                if (isChecked)
                {
                    switch(checkedId) {
                        case R.id.Default:
                            ChatScreen.theme = 0;
                            break;
                        case R.id.Christmas:
                            ChatScreen.theme = 1;
                            break;
                        case R.id.Thanksgiving:
                            ChatScreen.theme = 2;
                            break;
                        case R.id.Eye_Bleed:
                            ChatScreen.theme = 3;
                            break;
                        case R.id.Underwater:
                            ChatScreen.theme = 4;
                            break;
                    }
                }
            }
        });

    }

    private void goBack(View view) {
        Intent OKIntent = new Intent(this, StartChat.class);
        startActivity(OKIntent);
    }

    private void updateDatabaseInfo () {

        myDatabase.child("Profiles").child(userID).child("Theme").removeValue();
        myDatabase.child("Profiles").child(userID).child("Theme").setValue(them);
        myDatabase.child("Profiles").child(userID).child("Avatar").removeValue();
        myDatabase.child("Profiles").child(userID).child("Avatar").setValue(avt);
    }

}

