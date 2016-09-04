package com.atirek.alm.fcmdemo.MessagingService;

import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Alm on 7/5/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        showNotification(remoteMessage.getData().get("message"));

    }

    private void showNotification(String message) {

        Log.d("Message>>>>", message);

    }
}
