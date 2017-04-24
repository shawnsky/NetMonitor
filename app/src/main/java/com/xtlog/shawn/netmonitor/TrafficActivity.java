package com.xtlog.shawn.netmonitor;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.xtlog.shawn.netmonitor.adapter.AppInfoAdapter;
import com.xtlog.shawn.netmonitor.utils.AppUtils;
import com.xtlog.shawn.netmonitor.view.PieChart;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import android.text.format.Formatter;

/**
 * Created by admin on 2017/4/24.
 */

public class TrafficActivity extends Activity {
    private List<ApplicationInfo> appInfos;
    private ListView lv_show_appinfos;
    private TextView allTraffic,allRx,allTx,mobileTraffic,mobileRx,mobileTx,wifiTraffic,wifiRx,wifiTx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic);
        appInfos = AppUtils.getAppInfos(this.getPackageManager());
        lv_show_appinfos = (ListView) findViewById(R.id.lv_show_appinfos);
        lv_show_appinfos.setAdapter(new AppInfoAdapter(this,appInfos));
        allTraffic = (TextView)findViewById(R.id.tv1);
        allRx = (TextView)findViewById(R.id.tv2);
        allTx = (TextView)findViewById(R.id.tv3);
        wifiTraffic = (TextView)findViewById(R.id.tv4);
        wifiRx = (TextView)findViewById(R.id.tv5);
        wifiTx = (TextView)findViewById(R.id.tv6);
        mobileTraffic = (TextView)findViewById(R.id.tv7);
        mobileRx = (TextView)findViewById(R.id.tv8);
        mobileTx = (TextView)findViewById(R.id.tv9);
        initHeader();

    }

    public void initHeader(){

        allTraffic.setText("全部流量："+Formatter.formatFileSize(this,TrafficStats.getTotalRxBytes()+TrafficStats.getTotalTxBytes()));
        allRx.setText("全部接收流量："+Formatter.formatFileSize(this,TrafficStats.getTotalRxBytes()));
        allTx.setText("全部发送流量："+Formatter.formatFileSize(this,TrafficStats.getTotalTxBytes()));

        mobileTraffic.setText("mobile流量："+Formatter.formatFileSize(this,TrafficStats.getMobileRxBytes()+TrafficStats.getMobileTxBytes()));
        mobileRx.setText("mobile接收流量："+Formatter.formatFileSize(this,TrafficStats.getMobileRxBytes()));
        mobileRx.setText("mobile发送流量："+Formatter.formatFileSize(this,TrafficStats.getMobileTxBytes()));

        mobileTraffic.setText("mobile流量："+Formatter.formatFileSize(this,TrafficStats.getMobileRxBytes()+TrafficStats.getMobileTxBytes()));
        mobileRx.setText("mobile接收流量："+Formatter.formatFileSize(this,TrafficStats.getMobileRxBytes()));
        mobileRx.setText("mobile发送流量："+Formatter.formatFileSize(this,TrafficStats.getMobileTxBytes()));

        wifiTraffic.setText("wifi流量："+Formatter.formatFileSize(this,(TrafficStats.getTotalRxBytes()+TrafficStats.getTotalTxBytes())-(TrafficStats.getMobileRxBytes()+TrafficStats.getMobileTxBytes())));
        wifiRx.setText("wifi接收流量："+Formatter.formatFileSize(this,(TrafficStats.getTotalRxBytes())-(TrafficStats.getMobileRxBytes())));
        wifiTx.setText("wifi发送流量："+Formatter.formatFileSize(this,(TrafficStats.getTotalTxBytes())-(TrafficStats.getMobileTxBytes())));
    }

    public void showRxChart(View view){
        Collections.sort(appInfos, new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo appInfo0, ApplicationInfo appInfo1) {
                return (int) (TrafficStats.getUidRxBytes(appInfo1.uid) - TrafficStats.getUidRxBytes(appInfo0.uid));
            }
        });
        Intent pieIntent = new PieChart(appInfos," 接收 ",this).getIntent(this);
                startActivity(pieIntent);
    }
    public void showTxChart(View view){
        Collections.sort(appInfos,new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo appInfo0, ApplicationInfo appInfo1) {
                return (int) (TrafficStats.getUidTxBytes(appInfo1.uid) - TrafficStats.getUidTxBytes(appInfo0.uid));
            }
        });
        Intent pieIntent = new PieChart(appInfos," 发送 ",this).getIntent(this);
        startActivity(pieIntent);
    }

    public void showTotChart(View view){
        Collections.sort(appInfos,new Comparator<ApplicationInfo>() {
            @Override
            public int compare(ApplicationInfo appInfo0, ApplicationInfo appInfo1) {
                return (int) ((TrafficStats.getUidTxBytes(appInfo1.uid)+TrafficStats.getUidRxBytes(appInfo1.uid)) - (TrafficStats.getUidTxBytes(appInfo0.uid)+TrafficStats.getUidRxBytes(appInfo0.uid)));
            }
        });
        Intent pieIntent = new PieChart(appInfos," 合计 ",this).getIntent(this);
        startActivity(pieIntent);
    }
}
