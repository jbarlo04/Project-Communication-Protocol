package com.example.pcp.websocket;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.net.URI;
import java.nio.charset.Charset;

public class MessagingWebSocket extends Service {

    public static final String ACTION_MSG_RECEIVED = "msgReceived";
    public static final String ACTION_NETWORK_STATE_CHANGED = "networkStateChanged";

    private static final String TAG = MessagingWebSocket.class.getSimpleName();
    private static final String WS_URL = "ws://YOUR_IP:3000/ws/websocket";

    private final IBinder mBinder = new WebSocketsBinder();

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean networkIsOn = intent.getBooleanExtra(ACTION_NETWORK_STATE_CHANGED, false);
            if (networkIsOn) {
                startSocket();
            } else {
                stopSocket();
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        startSocket();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        stopSocket();
        return false;
    }

    public void onConnect() {
        Log.i(TAG, "Websocket onConnect()");
    }

    public void onMessage(String message) {
        Log.i(TAG, "Websocket onMessage()");
        Log.i(TAG, "Message (String) received: " + message);
        sendMessageReceivedEvent();
    }

    public void onMessage(byte[] data) {
        Log.i(TAG, "Websocket onMessage()");
        Log.i(TAG, "Message (byte[]) received: " + new String(data, Charset.defaultCharset()));
        sendMessageReceivedEvent();
    }

    public void onDisconnect(int code, String reason) {
        Log.i(TAG, "Websocket onDisconnect()");
        Log.i(TAG, "Code: " + code + " - Reason: " + reason);
    }

    public void onError(Exception error) {
        Log.i(TAG, "Websocket onError()");
    }

    private Boolean startSocket() {
        return true;
    }

    private Boolean stopSocket() {

        return false;
    }

    private void sendMessageReceivedEvent() {
        Intent intent = new Intent(ACTION_MSG_RECEIVED);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public final class WebSocketsBinder extends Binder {
        public MessagingWebSocket getService() {
            return MessagingWebSocket.this;
        }
    }
}