package com.omagle.omagle;


import java.util.ArrayList;
import java.util.List;
import android.util.Log;


/*Message class that stores the relevant information to send message
* A message object is what gets written to the firbase database for a user to view.
*/
public class Message {
    //text being sent
    private String texts;
    //sender and receiver
    private String sender;
    private String receiver;
    //for displaying on the screen purposes
    private boolean displayed;
    private boolean sentMessage;

    //blank default constructor
    public Message()
    {
        texts = "";
        sender = "";
        receiver = "";
        displayed = false;
    }

    //contructor that accepts message that user wants to send
    public Message(String text){
        this.texts = text;
        sender = "";
        receiver = "";
        displayed = false;
        sentMessage = false;
    }

    //getter methods

    public String getText()
    {
        return texts;
    }

    public String getSender()
    {
        return sender;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public boolean getDisplayed()
    {
        return displayed;
    }

    //setter methods

    public void setText(String t)
    {
        texts = t;
    }

    public void setSender(String s)
    {
        sender = s;
    }

    public void setReceiver(String r)
    {
        receiver = r;
    }

    public void setDisplayed(boolean d)
    {
        displayed = d;
    }

    public void setSentMessage(boolean s) { sentMessage = s; }

    //used for UI purposes
    public boolean didMessageSend() { return sentMessage; }
}
