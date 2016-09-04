package com.atirek.alm.fcmdemo.MessagingService;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Alm on 7/5/2016.
 */
public class FirebaseInstanceIDListenerService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        registerToken(token);

    }

    private void registerToken(String token) {
        Log.d("Token>>>>", token);
    }
}
