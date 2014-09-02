package com.kn.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.TextView;

import com.kn.R;
import com.kn.uitls.HttpUtils;
import com.kn.uitls.JsonUtils;

public class NewsDetails extends Activity {
	
	private static final String TAG = "NewsDetails";
	
	private static final String mimeType = "text/html";
	private static final String encoding = "GBK";
	private static final String jsonUrl = "http://10.0.2.2:8080/KnWebService/zhihu/";
	
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
		
		// 优先使用缓存
		webSettings = contents.getSettings();
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);	
		
		// 设置支持Javascript的参数
		webSettings.setJavaScriptEnabled(true);
		
		// 图片过大时自动适应屏幕
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);		// 缩放排版
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);				// 适应屏幕
		
//		String data = "<HTML>在模拟器 2.1 上测试,这是APK里的图片</HTML>"; 
//		contents.loadDataWithBaseURL(null, data, mimeType, encoding, null);
		
		new MyAsyncTask(this, contents).execute(jsonUrl + news_id);
		
	}
	
	private class MyAsyncTask extends AsyncTask<String, Void, String> {
		
		private Context context = null;
		private WebView webView = null;
		
		public MyAsyncTask() {
			
		}
		
		public MyAsyncTask(Context context) {
			this.context = context;
		}
		
		public MyAsyncTask(Context context, WebView webView) {
			this.context = context;
			this.webView = webView;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			String bodyStr = "";
			Map<String, String> mMap = new HashMap<String, String>();
			
			try {
				mMap = JsonUtils.Analysis_Offline(result);
				bodyStr = mMap.get("body");		// 获取网页部分
				Log.i(TAG, bodyStr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			webView.loadData(bodyStr, mimeType, encoding);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onCancelled(String result) {
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected String doInBackground(String... params) {

			String urlStr = params[0];
			String jsonStr = HttpUtils.getJsonContent(urlStr);
			Log.i(TAG, "doInBackground : json --> " + jsonStr.substring(1, 20));
			
			return jsonStr;
		}
		
	}

}
