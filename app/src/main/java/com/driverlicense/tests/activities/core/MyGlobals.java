package com.driverlicense.tests.activities.core;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.driverlicense.tests.R;

import static com.parse.Parse.getApplicationContext;

public class MyGlobals {

    private Context mContext;
    private Dialog connectivityDialog;

    public MyGlobals(Context mContext) {
        this.mContext = mContext;
    }

    public void checkConnectivityChanges()
    {
        getApplicationContext().registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
            boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

            NetworkInfo currentNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            NetworkInfo otherNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

            if(!currentNetworkInfo.isConnected()){
                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
            }else{
                connectivityDialog = new Dialog(mContext);
                connectivityDialog.setContentView(R.layout.activity_connectivity_changes);
                connectivityDialog.setCancelable(false);
                connectivityDialog.show();
            }
        }
    };



}
