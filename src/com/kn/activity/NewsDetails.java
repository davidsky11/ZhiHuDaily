package com.kn.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

import com.kn.R;
import com.kn.constant.Constants;
import com.kn.task.DownloadAsyncTask;

public class NewsDetails extends Activity {
	
	private static final String TAG = "NewsDetails";
	
	private static final String jsonUrl = "http://10.0.2.2:8080/KnWebService/zhihu/";
//	private static final String jsonUrl = Constants.Url.ZHIHU_DAILY_OFFLINE_NEWS;
	
	Map<String, String> mMap = new HashMap<String, String>();
	
	private WebView contents;
	private WebSettings webSettings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_details);
		
		Intent intent = getIntent();
		int count = intent.getIntExtra("itemId", -1);
		String news_id = intent.getStringExtra("news_id");
		
		TextView tv_info = (TextView) findViewById(R.id.tv_info);
		tv_info.setText("完整内容:  第"+Integer.toString(count) + "条新闻" + " news_id : " + news_id);
		
		/*
		 * 填充WebView
		 */
		contents = (WebView) findViewById(R.id.wv_news);
		
		// 加载URL前，设置图片阻塞
//		webSettings.setBlockNetworkImage(true);
		
		// 优先使用缓存
		webSettings = contents.getSettings();
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);	
		
		// 设置支持Javascript的参数
		webSettings.setJavaScriptEnabled(true);
		
		// 图片过大时自动适应屏幕
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);		// 缩放排版
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);				// 适应屏幕
		
//		new DownloadAsyncTask(this, contents, Constants.DownType.NEW_DETAILS_DOWN).execute(Constants.Url.ZHIHU_DAILY_OFFLINE_NEWS + news_id);
		new DownloadAsyncTask(this, contents, Constants.DownType.NEW_DETAILS_DOWN).execute(jsonUrl + news_id);
	}

}
