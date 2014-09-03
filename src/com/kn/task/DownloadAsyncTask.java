package com.kn.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.kn.R;
import com.kn.activity.NewsDetails;
import com.kn.adapter.NewsAdapter;
import com.kn.constant.Constants;
import com.kn.entity.ZhiHuSummary;
import com.kn.ui.widget.NewsListView;
import com.kn.ui.widget.NewsListView.OnRefreshListener;
import com.kn.utils.JsonUtils;

public class DownloadAsyncTask extends AsyncTask<String, Void, String> {
	
	private static final String TAG = "DownloadAsyncTask";
	
	private static final String mimeType = "text/html";
	private static final String encoding = "UTF-8";
	
	private int downType;		// 下载类型（新闻列表、新闻内容）
	private Context context;
	private WebView webView;
	private NewsListView newsListView;
	private List<ZhiHuSummary> zhihu_list;

	public DownloadAsyncTask(Context context, WebView webView, int downType) {
		this.downType = downType;
		this.context = context;
		this.webView = webView;
	}
	
	public DownloadAsyncTask(Context context, NewsListView newsListView, List<ZhiHuSummary> zhihu_list, int downType) {
		this.downType = downType;
		this.zhihu_list = zhihu_list;
		this.context = context;
		this.newsListView = newsListView;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		/**
		 * 根据下载类型进行下载
		 */
		switch (downType) {
		case Constants.DownType.NEWS_List_DOWN:
			
			try {
				zhihu_list = JsonUtils.Analysis_Lastest(result);
				
				int count = 1;
				for (ZhiHuSummary zh : zhihu_list) {
					System.out.println(count++  + " : " + zh.getId());
				}
				
				NewsAdapter adapter = new NewsAdapter(this.context, newsListView,
						zhihu_list, R.layout.news_item);
				this.newsListView.setAdapter(adapter);
				
				/**
				 * 为news_list_view添加下拉刷新事件
				 */
				newsListView.setonRefreshListener(new OnRefreshListener() {

					@Override
					public void onRefresh() {

						new AsyncTask<Void, Void, Void>() {
							// 下拉刷新过程中需要做的操作
							protected Void doInBackground(Void... params) {
//								try {
//									Thread.sleep(1000);
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
								return null;
							}

							// 刷新完成后通知listview进行界面调整
							protected void onPostExecute(Void result) {

								newsListView.onRefreshComplete();
								Toast.makeText(context, "刷新完成...",
										Toast.LENGTH_SHORT).show();
							}
						};
					}
				});
				
				/**
				 * 为news_list_view添加点击事件
				 */
				newsListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent(context, NewsDetails.class);
						Bundle data = new Bundle();
						
						if (zhihu_list.size() > 0) {
							String news_id = zhihu_list.get(position-1).getId();		// List下标从0开始

							data.putInt("itemId", position);
							data.putString("news_id", news_id);
							Log.i(TAG, "传递的数据： itemId : " + position + " news_id : " + news_id);

							intent.putExtras(data);
							context.startActivity(intent);
						} else {
							Log.i(TAG, "zhihu_list为空！");
						}
						
					}
				});
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
			
			return;
			
		case Constants.DownType.NEW_DETAILS_DOWN:
			String bodyStr = "";
			Map<String, String> mMap = new HashMap<String, String>();
			
			try {
				mMap = JsonUtils.Analysis_Offline(result);
				bodyStr = mMap.get("body");		// 获取网页部分
				Log.i(TAG, bodyStr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			webView.loadDataWithBaseURL(null, bodyStr, mimeType, encoding, null);
			return;
		}
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected String doInBackground(String... params) {

		String urlStr = params[0];			// 获取下载链接
		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(3 * 1000);
			connection.setRequestMethod("GET");// 请求方法
			connection.setDoInput(true);// 输入流
			int code = connection.getResponseCode();
			
			if (code == 200) {
				// 服务器已准备好，可以取出流，流直接转换成字符串
				return ChangeInputStream(connection.getInputStream());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	/**
	 * 从网络输入流中获取JSON数据
	 * @param inputStream	输入流
	 * @return
	 */
	public static String ChangeInputStream(InputStream inputStream) {
		
		String jsonString = "";
		
		// 字节数组流，表示在内存地址当中可以显示出来，可以把数据写入内存当中
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}

			// outputStream流——>写入ByteArrayOutputStream——>转换成字节数组
			jsonString = new String(outputStream.toByteArray());

		} catch (IOException e) {
			e.printStackTrace();
		}

		Log.i(TAG + " ChangeInputStream", jsonString);
		return jsonString;
	}

}
