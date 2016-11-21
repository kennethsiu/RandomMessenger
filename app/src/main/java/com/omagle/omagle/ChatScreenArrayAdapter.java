package com.omagle.omagle;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bastard on 10/30/16.
 */

public class ChatScreenArrayAdapter extends ArrayAdapter<Message> {

    private Context context;
    private List<Message> messageList = new ArrayList<Message>();

    @Override
    public void add(Message message) {
        messageList.add(message);
        super.add(message);
    }


    public ChatScreenArrayAdapter(Context context, int textViewResourceID) {
        super(context, textViewResourceID);
        this.context = context;
    }

    public int size() {
        return this.messageList.size();
    }

    public Message getItem(int index) {
        return this.messageList.get(index);
    }

    public View getView(int pos, View convertView, ViewGroup parent) {
        Message message = getItem(pos);
        View row = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView messageText;
        if (message.didMessageSend()) {
            row = inflater.inflate(R.layout.message_bubble_right, parent, false);
            messageText = (TextView) row.findViewById(R.id.message_text);
        }
        else {
            row = inflater.inflate(R.layout.message_bubble_left, parent, false);
            messageText = (TextView) row.findViewById(R.id.message_text);
        }
        //took out for loop because changed getText to return String instead of List<String>
        String body = message.getText();
        if (body != null) {
            messageText.setText(body);
        }
        return row;

    }
}
