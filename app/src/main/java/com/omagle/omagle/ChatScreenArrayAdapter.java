package com.omagle.omagle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bastard on 10/30/16.
 *
 * Started from tutorial from "https://trinitytuts.com/simple-chat-application-using-listview-in-android/"
 * to create chat UI, and evolved over time
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
            row = inflater.inflate(R.layout.message_bubble_right_default, parent, false);
            switch (ChatScreen.theme){
                case 1: row = inflater.inflate(R.layout.message_bubble_right_christmas, parent, false); break;
                case 2: row = inflater.inflate(R.layout.message_bubble_right_thanksgiving, parent, false); break;
                case 3: row = inflater.inflate(R.layout.message_bubble_right_eyebleed, parent, false); break;
                case 4: row = inflater.inflate(R.layout.message_bubble_right_underwater, parent, false); break;
                default: row = inflater.inflate(R.layout.message_bubble_right_default, parent, false); break;
            }
        }
        else {
            switch (ChatScreen.theme){
                case 1: row = inflater.inflate(R.layout.message_bubble_left_christmas, parent, false); break;
                case 2: row = inflater.inflate(R.layout.message_bubble_left_thanksgiving, parent, false); break;
                case 3: row = inflater.inflate(R.layout.message_bubble_left_eyebleed, parent, false); break;
                case 4: row = inflater.inflate(R.layout.message_bubble_left_underwater, parent, false); break;
                default: row = inflater.inflate(R.layout.message_bubble_left_default, parent, false); break;
            }
        }
        switch (ChatScreen.theme){
            case 1: messageText = (TextView) row.findViewById(R.id.message_text_christmas); break;
            case 2: messageText = (TextView) row.findViewById(R.id.message_text_thanksgiving); break;
            case 3: messageText = (TextView) row.findViewById(R.id.message_text_eyebleed); break;
            case 4: messageText = (TextView) row.findViewById(R.id.message_text_underwater); break;
            default: messageText = (TextView) row.findViewById(R.id.message_text_default); break;

        }
        //took out for loop because changed getText to return String instead of List<String>
        String body = message.getText();
        if (body != null) {
            messageText.setText(body);
        }
        return row;

    }
}
