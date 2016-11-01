# RandomMessenger

RandomMessenger is an Android mobile application to connect to another user randomly and start a conversation

<strong>Project Created By:</strong>
<ul>
<li> Kenneth Siu </li>
<li> Michael Carroll </li>
<li> Jason Diaz </li>
<li> Minh-Tuan Duong </li>
<li> Anu Ganzorig </li>
<li> James Kwon </li>
<li> Mohammed Omar </li>
</ul>

<h2> Features </h2>
<h4> Messaging </h4>
Ability to send/receive messages, start/end chat, and find/reroll a chat partner
<h4> Login </h4>
Ability to accept username/password, username/password verification, profile setup (contact info), and chat filters
<h4> Report System </h4>
Ability to temporarily ban users who have been reported for harassment

<h2> Design </h2>
This application is designed on Android Studio. [add language(s) used]

<h2> Implementation </h2>
The project consisted of several steps.
<ul>
  <li> First, we set up the login activity for the application. Upon inputting a valid username and password, the user will be sent to the Start Chat activity. </li>
  <li> Second, we set up the start chat activity for the application. This page only holds the "Start Chat" button which allows the user to actually start a conversation. </li>
  <li> Third, we set up the messaging activity for the application. For the actual messaging, we are using Firebase's API. </li>
</ul>

<h2> Possible Bugs & Future Updates </h2>
The Report System and Database functionality has not been added yet.
We may also add a feature to allow the user to keep open old messages.
