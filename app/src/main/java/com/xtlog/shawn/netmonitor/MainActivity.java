package com.xtlog.shawn.netmonitor;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xtlog.shawn.netmonitor.utils.AppUtils;

public class MainActivity extends AppCompatActivity {
    private TextView mNetwork;
    private TextView mTraffic;
    private TextView mPower;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNetwork = (TextView) findViewById(R.id.tv_network);
        mTraffic = (TextView)findViewById(R.id.tv_traffic);
        mPower = (TextView)findViewById(R.id.tv_power);
        mButton = (Button) findViewById(R.id.button1);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TrafficActivity.class);
                startActivity(intent);
            }
        });
        initNetwork();
        initPower();
        initTraffic();

    }

    private void initNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        String networkType = null;
        if(networkInfo == null){
            networkType = "not connect";
        }else if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
            networkType = "wifi";
        }else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
            networkType = "mobile";
        }
        mNetwork.setText(networkType);
    }

    private void initPower(){
        BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
                    int level = intent.getIntExtra("level", 0);
                    int scale = intent.getIntExtra("scale", 100);
                    mPower.setText((level * 100 / scale) + "%");
                }
            }
        };
        registerReceiver(batteryChangedReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private void initTraffic(){
        long tot = TrafficStats.getTotalRxBytes()+TrafficStats.getTotalTxBytes();
        String totSize = android.text.format.Formatter.formatFileSize(getApplicationContext(),tot);
        mTraffic.setText(totSize);
    }
}
