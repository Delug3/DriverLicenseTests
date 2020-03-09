package com.driverlicense.tests.activities.core;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;

import com.driverlicense.tests.R;

public class MyGlobals {

    private Context context;
    private Dialog connectivityDialog;

    public MyGlobals(Context mContext) {
        this.context = mContext;
    }

    public void checkConnectivityChanges()
    {
        context.registerReceiver(this.mConnReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }


    private BroadcastReceiver mConnReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            String reason = intent.getStringExtra(ConnectivityManager.EXTRA_REASON);
            boolean isFailover = intent.getBooleanExtra(ConnectivityManager.EXTRA_IS_FAILOVER, false);

            //NetworkInfo currentNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            //NetworkInfo otherNetworkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo currentNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if(!currentNetworkInfo.isConnected()){
                connectivityDialog = new Dialog(MyGlobals.this.context);
                connectivityDialog.setContentView(R.layout.activity_connectivity_changes);
                connectivityDialog.setCancelable(true);
                connectivityDialog.show();
            }




        }
    };



}
