package com.example.artem.meteoadviserjava;

import android.app.Application;
import com.example.artem.meteoadviserjava.internetconnection.ConnectionReceiver;

public class MaApplication extends Application {

    private static MaApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MaApplication getInstance(){
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener){
        ConnectionReceiver.mConnectionReceiverListener = listener;
    }
}
