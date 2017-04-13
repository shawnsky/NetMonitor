package com.example.trafficmanager.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AppUtils {

	/*
	 * 获取网络类型
	 */
	
	public static String getNetworkType(ConnectivityManager connManager){
		
		NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
		String networkType = null;
		if(networkInfo == null){
			networkType = "not connect";
		}else if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
			networkType = "wifi";
		}else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
			networkType = "mobile";
		}
		return networkType;
	}

	public static List<ApplicationInfo> getAppInfos(PackageManager packageManager){

		List<ApplicationInfo> appInfos = new ArrayList<ApplicationInfo>();

		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES|PackageManager.GET_PERMISSIONS);
		for(PackageInfo packageInfo : packageInfos){
			String[] premissions = packageInfo.requestedPermissions;
			if(premissions != null && premissions.length > 0){
				for(String premission : premissions){
					if("android.permission.INTERNET".equals(premission)){

						/*
						 * 得到应用的信息
						 */
						ApplicationInfo applicationInfo = packageInfo.applicationInfo;
						if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0){


							appInfos.add(applicationInfo);
						}
						break;
					}
				}
			}
		}

		Collections.sort(appInfos, new Comparator<ApplicationInfo>() {
			@Override
			public int compare(ApplicationInfo appInfo0, ApplicationInfo appInfo1) {
				long rx0 = TrafficStats.getUidRxBytes(appInfo0.uid);
				long tx0 = TrafficStats.getUidTxBytes(appInfo0.uid);
				long rx1 = TrafficStats.getUidRxBytes(appInfo1.uid);
				long tx1 = TrafficStats.getUidTxBytes(appInfo1.uid);
				return (int)((rx1+tx1)-(rx0+tx0));
			}
		});
		return appInfos;
	}
}