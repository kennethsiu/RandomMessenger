package com.omagle.omagle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {

    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myDatabase = FirebaseDatabase.getInstance().getReference();

        EditText nameText = (EditText) findViewById(R.id.nameEdit);
        EditText majorText = (EditText) findViewById(R.id.majorEdit);
        EditText ageText = (EditText) findViewById(R.id.ageEdit);

        //Added by MinhTuan
        final Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatabaseInfo();
                goBack(v);
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
        myDatabase.child("Profiles");
    }

}

