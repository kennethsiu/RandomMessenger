package com.omagle.omagle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Added by MinhTuan
        final Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.Default:
//                if (checked)
//                    ChatScreen.theme = 0;
//                break;
//            case R.id.Christmas:
//                if (checked)
//                    ChatScreen.theme = 1;
//                break;
//            case R.id.Thanksgiving:
//                if (checked)
//                    ChatScreen.theme = 2;
//                break;
//            case R.id.Eye_Bleed:
//                if (checked)
//                    ChatScreen.theme = 3;
//                break;
//            case R.id.Underwater:
//                if (checked)
//                    ChatScreen.theme = 4;
//                break;
//        }
//    }
}

