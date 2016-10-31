package com.omagle.omagle;

import java.util.Date;

/**
 * Created by Michael on 10/30/16.
 */

public class Message {
    private String text;
    private Date date;
    private String uID;

    public Message(String text, Date date, String uID){
        this.text = text;
        this.date = date;
        this.uID = uID;
    }

    public Message (String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

}
