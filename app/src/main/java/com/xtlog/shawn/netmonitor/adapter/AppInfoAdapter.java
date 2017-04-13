package com.example.trafficmanager.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trafficmanager.R;
import com.example.trafficmanager.utils.ImageUtils;

import java.util.List;

public class AppInfoAdapter extends BaseAdapter {

    private Context context;
    private List<ApplicationInfo> appInfos;

    public AppInfoAdapter() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AppInfoAdapter(Context context, List<ApplicationInfo> appInfos) {
        super();
        this.context = context;
        this.appInfos = appInfos;
    }

    @Override
    //要显示List的条目数
    public int getCount() {
        // TODO Auto-generated method stub
        return appInfos.size();
    }

    @Override
    //返回某个位置的条目
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    //条目的id
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    //返回要显示的View对象
    public View getView(int position, View convertView, ViewGroup parent) {


        // TODO Auto-generated method stub

        ApplicationInfo appInfo = appInfos.get(position);

        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.appinfos_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_app_icon = (ImageView) view.findViewById(R.id.iv_app_icon);
            viewHolder.tv_app_name = (TextView) view.findViewById(R.id.tv_app_name);
            viewHolder.tv_app_mobile = (TextView) view.findViewById(R.id.tv_app_mobile);
            viewHolder.tv_app_wifi = (TextView) view.findViewById(R.id.tv_app_wifi);

            view.setTag(viewHolder);
        } else {

            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        PackageManager packageManager = context.getPackageManager();

        Drawable appIcon = appInfo.loadIcon(packageManager);
        viewHolder.iv_app_icon.setImageBitmap(ImageUtils.getResizedBitmap(appIcon));

        viewHolder.tv_app_name.setText(appInfo.loadLabel(packageManager).toString());

        viewHolder.tv_app_mobile.setText(Formatter.formatFileSize(context, TrafficStats.getUidRxBytes(appInfo.uid)));

        viewHolder.tv_app_wifi.setText(Formatter.formatFileSize(context, TrafficStats.getUidTxBytes(appInfo.uid)));

        return view;
    }

    class ViewHolder {

        ImageView iv_app_icon;
        TextView tv_app_name;
        TextView tv_app_mobile;
        TextView tv_app_wifi;
    }

}
