package com.omagle.omagle;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Bastard on 11/1/16.
 */

public class FirebaseTranslater {
    //not sure that this is going but commented out by Anu. Was giving me errors
    /*
    protected boolean send(Message message){
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        String msgID = FirebaseInstanceId.getInstance() + Integer.toString(Message.messageID);
        fm.send(new RemoteMessage.Builder(FirebaseInstanceId.getInstance().getToken() + "@gcm.googleapis.com")
                .setMessageId(msgID)
                .addData(msgID, message.getText())
                .build());
        return true;
    }*/
}
