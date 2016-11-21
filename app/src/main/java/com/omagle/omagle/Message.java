package com.omagle.omagle;


import java.util.ArrayList;
import java.util.List;
import android.util.Log;

/**
 * Created by Michael on 10/30/16.
 */

public class Message {
    private String texts;
    private String sender;
    private String receiver;
    private boolean displayed;
    private boolean sentMessage;

    public Message()
    {
        texts = "";
        sender = "";
        receiver = "";
        displayed = false;
    }

    public Message(String text){
        this.texts = text;
        sender = "";
        receiver = "";
        displayed = false;
        sentMessage = false;
    }

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

    public boolean didMessageSend() { return sentMessage; }
}
