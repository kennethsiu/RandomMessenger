package com.omagle.omagle;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 10/30/16.
 */

public class Message {
    private List<String> texts;
    private String sender;
    private String receiver;
    private boolean displayed;

    public Message()
    {
        texts = null;
        sender = "";
        receiver = "";
        displayed = false;
    }

    public Message(List<String> text){
        this.texts = text;
        sender = "";
        receiver = "";
        displayed = false;
    }

    public Message(String t){
        texts = new ArrayList<>();
        texts.add(t);
        sender = "";
        receiver = "";
        displayed = false;
    }
    public List<String> getText()
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

    public void setText(List<String> t)
    {
        texts = t;
    }

    public void addText(String newText)
    {
        if(!displayed)
            texts.add(newText);
        else
        {
            texts = new ArrayList<>();
            texts.add(newText);
            displayed = false;
        }
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

}
