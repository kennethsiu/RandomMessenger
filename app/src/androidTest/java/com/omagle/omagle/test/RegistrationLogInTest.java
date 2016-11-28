package com.omagle.omagle.test;

import com.omagle.omagle.LoginActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class RegistrationLogInTest extends ActivityInstrumentationTestCase2<LoginActivity> {
  	private Solo solo;
  	
  	public RegistrationLogInTest() {
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
        //Sleep for 7824 milliseconds
		solo.sleep(7824);
        //Click on Sign Up
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.signUpButton));
        //Wait for activity: 'com.omagle.omagle.SignUp'
		assertTrue("com.omagle.omagle.SignUp is not found!", solo.waitForActivity(com.omagle.omagle.SignUp.class));
        //Sleep for 3714 milliseconds
		solo.sleep(3714);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 868 milliseconds
		solo.sleep(868);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.enterEmail));
        //Sleep for 990 milliseconds
		solo.sleep(990);
        //Enter the text: 'asdf'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail), "asdf");
        //Sleep for 1538 milliseconds
		solo.sleep(1538);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 2975 milliseconds
		solo.sleep(2975);
        //Enter the text: 'asdf@asdf.com'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail), "asdf@asdf.com");
        //Sleep for 1095 milliseconds
		solo.sleep(1095);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 1234 milliseconds
		solo.sleep(1234);
        //Click on asdf@asdf.com
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.enterEmail));
        //Sleep for 2231 milliseconds
		solo.sleep(2231);
        //Enter the text: 'asdf@ucsd'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail), "asdf@ucsd");
        //Sleep for 1462 milliseconds
		solo.sleep(1462);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 2674 milliseconds
		solo.sleep(2674);
        //Enter the text: 'asdf@ucsd.com'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail), "asdf@ucsd.com");
        //Sleep for 1163 milliseconds
		solo.sleep(1163);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 2093 milliseconds
		solo.sleep(2093);
        //Enter the text: 'asdf@ucsd.'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail), "asdf@ucsd.");
        //Sleep for 1145 milliseconds
		solo.sleep(1145);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 1072 milliseconds
		solo.sleep(1072);
        //Enter the text: 'asdf@ucsd.edu'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.enterEmail), "asdf@ucsd.edu");
        //Sleep for 2027 milliseconds
		solo.sleep(2027);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 1033 milliseconds
		solo.sleep(1033);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.EnterPassword));
        //Sleep for 2116 milliseconds
		solo.sleep(2116);
        //Enter the text: '123'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.EnterPassword));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.EnterPassword), "123");
        //Sleep for 1297 milliseconds
		solo.sleep(1297);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 742 milliseconds
		solo.sleep(742);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.ConfirmPassword));
        //Sleep for 992 milliseconds
		solo.sleep(992);
        //Enter the text: '123'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.ConfirmPassword));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.ConfirmPassword), "123");
        //Sleep for 1069 milliseconds
		solo.sleep(1069);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 973 milliseconds
		solo.sleep(973);
        //Click on 123
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.EnterPassword));
        //Sleep for 1702 milliseconds
		solo.sleep(1702);
        //Enter the text: '12345678'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.EnterPassword));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.EnterPassword), "12345678");
        //Sleep for 1045 milliseconds
		solo.sleep(1045);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Sleep for 698 milliseconds
		solo.sleep(698);
        //Click on 123
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.ConfirmPassword));
        //Sleep for 1425 milliseconds
		solo.sleep(1425);
        //Enter the text: '12345678'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.ConfirmPassword));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.ConfirmPassword), "12345678");
        //Sleep for 1022 milliseconds
		solo.sleep(1022);
        //Click on Sign Up!
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.beginSignUp));
        //Wait for activity: 'com.omagle.omagle.StartChat'
		assertTrue("com.omagle.omagle.StartChat is not found!", solo.waitForActivity(com.omagle.omagle.StartChat.class));
        //Sleep for 2618 milliseconds
		solo.sleep(2618);
        //Click on Settings
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.settingsButton));
        //Wait for activity: 'com.omagle.omagle.Settings'
		assertTrue("com.omagle.omagle.Settings is not found!", solo.waitForActivity(com.omagle.omagle.Settings.class));
        //Sleep for 2125 milliseconds
		solo.sleep(2125);
        //Click on Thanksgiving
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.Thanksgiving));
        //Sleep for 719 milliseconds
		solo.sleep(719);
        //Click on OK
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.okButton));
        //Wait for activity: 'com.omagle.omagle.StartChat'
		assertTrue("com.omagle.omagle.StartChat is not found!", solo.waitForActivity(com.omagle.omagle.StartChat.class));
        //Sleep for 891 milliseconds
		solo.sleep(891);
        //Click on Start Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.startChatButton, 1));
        //Wait for activity: 'com.omagle.omagle.ChatScreen'
		assertTrue("com.omagle.omagle.ChatScreen is not found!", solo.waitForActivity(com.omagle.omagle.ChatScreen.class));
        //Sleep for 1794 milliseconds
		solo.sleep(1794);
        //Click on Send
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.sendButton));
        //Click on Send
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.sendButton));
        //Sleep for 1028 milliseconds
		solo.sleep(1028);
        //Enter the text: 'asdfasdfasdf'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message), "asdfasdfasdf");
        //Click on Send
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.sendButton));
        //Sleep for 2158 milliseconds
		solo.sleep(2158);
        //Click on Exit Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.exitButton));
        //Sleep for 1587 milliseconds
		solo.sleep(1587);
        //Click on Settings
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.settingsButton, 1));
        //Wait for activity: 'com.omagle.omagle.Settings'
		assertTrue("com.omagle.omagle.Settings is not found!", solo.waitForActivity(com.omagle.omagle.Settings.class));
        //Sleep for 822 milliseconds
		solo.sleep(822);
        //Click on Default
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.Default, 1));
        //Sleep for 780 milliseconds
		solo.sleep(780);
        //Click on OK
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.okButton, 1));
        //Wait for activity: 'com.omagle.omagle.StartChat'
		assertTrue("com.omagle.omagle.StartChat is not found!", solo.waitForActivity(com.omagle.omagle.StartChat.class));
        //Sleep for 748 milliseconds
		solo.sleep(748);
        //Click on Start Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.startChatButton, 2));
        //Wait for activity: 'com.omagle.omagle.ChatScreen'
		assertTrue("com.omagle.omagle.ChatScreen is not found!", solo.waitForActivity(com.omagle.omagle.ChatScreen.class));
        //Sleep for 1770 milliseconds
		solo.sleep(1770);
        //Enter the text: 'asdfadfsdafasd'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message), "asdfadfsdafasd");
        //Click on Send
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.sendButton));
        //Click on Send
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.sendButton));
        //Sleep for 987 milliseconds
		solo.sleep(987);
        //Click on Exit Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.exitButton));
        //Sleep for 1551 milliseconds
		solo.sleep(1551);
        //Click on Settings
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.settingsButton, 2));
        //Wait for activity: 'com.omagle.omagle.Settings'
		assertTrue("com.omagle.omagle.Settings is not found!", solo.waitForActivity(com.omagle.omagle.Settings.class));
        //Sleep for 2223 milliseconds
		solo.sleep(2223);
        //Enter the text: 'asdfasd'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.nameEdit, 2));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.nameEdit, 2), "asdfasd");
        //Sleep for 531 milliseconds
		solo.sleep(531);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.majorEdit, 2));
        //Enter the text: 'asdfasdf'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.majorEdit, 2));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.majorEdit, 2), "asdfasdf");
        //Sleep for 746 milliseconds
		solo.sleep(746);
        //Assert that: 'Major' is shown
		assertTrue("'Major' is not shown!", solo.waitForView(solo.getView(com.omagle.omagle.R.id.Major, 2)));
        //Sleep for 878 milliseconds
		solo.sleep(878);
        //Click on asdfasdf
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.majorEdit, 2));
        /*Deleted some stuff that didnt compile. Refer to group chat*/
		//Sleep for 2145 milliseconds
		solo.sleep(2145);
        //Enter the text: 'asdfasdf'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.majorEdit, 2));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.majorEdit, 2), "asdfasdf");
        //Sleep for 1393 milliseconds
		solo.sleep(1393);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.ageEdit, 2));
        //Sleep for 1110 milliseconds
		solo.sleep(1110);
        //Enter the text: '132'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.ageEdit, 2));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.ageEdit, 2), "132");
        //Assert that: 'Major' is shown
		assertTrue("'Major' is not shown!", solo.waitForView(solo.getView(com.omagle.omagle.R.id.Major, 2)));
        //Sleep for 2141 milliseconds
		solo.sleep(2141);
        //Click on OK
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.okButton, 2));
        //Wait for activity: 'com.omagle.omagle.StartChat'
		assertTrue("com.omagle.omagle.StartChat is not found!", solo.waitForActivity(com.omagle.omagle.StartChat.class));
        //Sleep for 3167 milliseconds
		solo.sleep(3167);
        //Click on Start Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.startChatButton, 3));
        //Wait for activity: 'com.omagle.omagle.ChatScreen'
		assertTrue("com.omagle.omagle.ChatScreen is not found!", solo.waitForActivity(com.omagle.omagle.ChatScreen.class));
        //Sleep for 869 milliseconds
		solo.sleep(869);
        //Enter the text: 'asdfasdf'
		solo.clearEditText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message));
		solo.enterText((android.widget.EditText) solo.getView(com.omagle.omagle.R.id.edit_message), "asdfasdf");
        //Sleep for 549 milliseconds
		solo.sleep(549);
        //Click on Send
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.sendButton));
        //Sleep for 1741 milliseconds
		solo.sleep(1741);
        //Click on Exit Chat
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.exitButton));
        //Sleep for 2299 milliseconds
		solo.sleep(2299);
        //Click on Sign Out
		solo.clickOnView(solo.getView(com.omagle.omagle.R.id.signOutButton, 3));
        //Wait for activity: 'com.omagle.omagle.LoginActivity'
		assertTrue("com.omagle.omagle.LoginActivity is not found!", solo.waitForActivity(com.omagle.omagle.LoginActivity.class));
	}
}
