package com.example.trafficmanager.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.TrafficStats;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.List;

/**
 * 饼图
 * 
 * @Package com.manyou.androidchart
 * @FileName PieChart.java
 * @Author APKBUS-manyou
 * @Date 2013-1-30
 */
public class PieChart{
	
	private List<ApplicationInfo> appInfos;
	private String title;
	private Context context;
	

	public PieChart(List<ApplicationInfo> appInfos, String title,Context context) {
		super();
		this.appInfos = appInfos;
		this.title = title;
		this.context = context;
	}

	public Intent getIntent(Context context) {
		return ChartFactory.getPieChartIntent(context, getDataSet(), getPieRenderer(), title);
	}

	/**
	 * 构造饼图数据
	 */
	private CategorySeries getDataSet() {
		// 构造数据
		CategorySeries pieSeries = new CategorySeries(title);
		PackageManager packageManager = context.getPackageManager();
		for(ApplicationInfo appInfo : appInfos){
			if(title.equals("接收")){
				pieSeries.add(appInfo.loadLabel(packageManager).toString(), TrafficStats.getUidRxBytes(appInfo.uid));
			}else if(title.equals("发送")){
				pieSeries.add(appInfo.loadLabel(packageManager).toString(), TrafficStats.getUidTxBytes(appInfo.uid));
			}
		}
		return pieSeries;
	}

	/**
	 * 获取一个饼图渲染器
	 */
	private DefaultRenderer getPieRenderer() {
		// 构造一个渲染器
		DefaultRenderer renderer = new DefaultRenderer();
		// 设置渲染器显示缩放按钮
		renderer.setZoomButtonsVisible(false);
		// 设置渲染器允许放大缩小
		renderer.setZoomEnabled(true);
		
		/*//设置标题
		renderer.setChartTitle(title);
		// 设置渲染器标题文字大小
		renderer.setChartTitleTextSize(20);*/
		// 给渲染器增加3种颜色
		
		int[] color = new int[]{Color.RED,Color.GREEN,Color.BLUE,Color.YELLOW,Color.CYAN,Color.LTGRAY,Color.GRAY,Color.MAGENTA,Color.DKGRAY};
		int i = 0;
		for(ApplicationInfo appInfo : appInfos){
			SimpleSeriesRenderer Renderer = new SimpleSeriesRenderer();
			if(i<9){
				Renderer.setColor(color[i]);
			}else{
				Renderer.setColor(color[(i+1)%9]);
			}
			renderer.addSeriesRenderer(Renderer);
			i++;
		}
		
		//renderer.setTextTypeface(typefaceName, style)
		// 设置饼图文字字体大小和饼图标签字体大小
		renderer.setLabelsTextSize(25);
		renderer.setLabelsColor(Color.BLACK);
		renderer.setLegendTextSize(30);
		// 消除锯齿
		renderer.setAntialiasing(true);
		// 设置背景颜色
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.parseColor("#FFFAF0"));
		//设置开始的角度
		renderer.setStartAngle(45);
		// 设置线条颜色
		renderer.setAxesColor(Color.WHITE);
		//设置自适应
		//renderer.setFitLegend(true);
		//设置是否允许拖动
		renderer.setPanEnabled(true);
		
		return renderer;
	}
}
