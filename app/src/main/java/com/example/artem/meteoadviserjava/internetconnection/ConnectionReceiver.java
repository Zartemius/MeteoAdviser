package com.example.artem.meteoadviserjava.internetconnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.artem.meteoadviserjava.MaApplication;

public class ConnectionReceiver extends BroadcastReceiver{

    public static ConnectionReceiverListener mConnectionReceiverListener;

    public ConnectionReceiver(){
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        boolean isConnected = false;

        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }catch (NullPointerException e){
            Log.e("ERROR IN NETWORK_INFO", e.toString());
        }

        if(mConnectionReceiverListener != null){
            mConnectionReceiverListener.onNetworkConnectionChanged(isConnected);
        }

    }

    public static boolean isConnected(){

        boolean isConnected = false;

        ConnectivityManager mConnectivityManager = (ConnectivityManager) MaApplication.getInstance()
                .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }catch (NullPointerException e){
            Log.e("ERROR IN NETWORK_INFO", e.toString());
        }

        return isConnected;
    }

    public interface ConnectionReceiverListener{
        void onNetworkConnectionChanged(boolean isConnected);
    }
}
