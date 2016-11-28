package com.omagle.omagle.test;

import com.omagle.omagle.LoginActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class SignInTest extends ActivityInstrumentationTestCase2<LoginActivity> {
  	private Solo solo;
  	
  	public SignInTest() {
		super(LoginActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'com.omagle.omagle.LoginActivity'
		solo.waitForActivity(com.omagle.omagle.LoginActivity.class, 2000);
        //Sleep for 12088 milliseconds
		solo.sleep(12088);
        //Enter the text: 'james@ucsd.edu'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.email));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.email), "james@ucsd.edu");
        //Sleep for 1953 milliseconds
		solo.sleep(1953);
        //Enter the text: '123456'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.password));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.password), "123456");
        //Sleep for 2391 milliseconds
		solo.sleep(2391);
        //Click on Login
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.email_sign_in_button));
        //Sleep for 3448 milliseconds
		solo.sleep(3448);
        //Click on 123456
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.password));
        //Sleep for 3752 milliseconds
		solo.sleep(3752);
        //Enter the text: '12345678'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.password));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.password), "12345678");
        //Sleep for 1178 milliseconds
		solo.sleep(1178);
        //Click on Login
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.email_sign_in_button));
        //Wait for activity: 'com.omagle.omagle.StartChat'
		assertTrue("com.omagle.omagle.StartChat is not found!", solo.waitForActivity(com.omagle.omagle.StartChat.class));
        //Sleep for 1514 milliseconds
		solo.sleep(1514);
        //Click on Start Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.startChatButton));
        //Wait for activity: 'com.omagle.omagle.ChatScreen'
		assertTrue("com.omagle.omagle.ChatScreen is not found!", solo.waitForActivity(com.omagle.omagle.ChatScreen.class));
        //Sleep for 1965 milliseconds
		solo.sleep(1965);
        //Enter the text: 'asdfasdf'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message), "asdfasdf");
        //Click on Send
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.sendButton));
        //Click on Send
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.sendButton));
        //Sleep for 810 milliseconds
		solo.sleep(810);
        //Click on Exit Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.exitButton));
        //Sleep for 1101 milliseconds
		solo.sleep(1101);
        //Click on Settings
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.settingsButton));
        //Wait for activity: 'com.omagle.omagle.Settings'
		assertTrue("com.omagle.omagle.Settings is not found!", solo.waitForActivity(com.omagle.omagle.Settings.class));
        //Sleep for 1041 milliseconds
		solo.sleep(1041);
        //Click on Christmas
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.Christmas));
        //Sleep for 834 milliseconds
		solo.sleep(834);
        //Click on OK
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.okButton));
        //Wait for activity: 'com.omagle.omagle.StartChat'
		assertTrue("com.omagle.omagle.StartChat is not found!", solo.waitForActivity(com.omagle.omagle.StartChat.class));
        //Sleep for 1472 milliseconds
		solo.sleep(1472);
        //Click on Start Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.startChatButton, 1));
        //Wait for activity: 'com.omagle.omagle.ChatScreen'
		assertTrue("com.omagle.omagle.ChatScreen is not found!", solo.waitForActivity(com.omagle.omagle.ChatScreen.class));
        //Sleep for 1362 milliseconds
		solo.sleep(1362);
        //Enter the text: 'asdfasdf'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message), "asdfasdf");
        //Sleep for 554 milliseconds
		solo.sleep(554);
        //Click on Send
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.sendButton));
        //Sleep for 783 milliseconds
		solo.sleep(783);
        //Click on Exit Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.exitButton));
        //Sleep for 3213 milliseconds
		solo.sleep(3213);
        //Click on Sign Out
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.signOutButton, 1));
        //Wait for activity: 'com.omagle.omagle.LoginActivity'
		assertTrue("com.omagle.omagle.LoginActivity is not found!", solo.waitForActivity(com.omagle.omagle.LoginActivity.class));
	}
}
