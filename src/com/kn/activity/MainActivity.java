package com.kn.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.kn.R;
import com.kn.adapter.NewsAdapter;
import com.kn.entity.ZhiHuSummary;
import com.kn.ui.widget.NewsListView;
import com.kn.ui.widget.NewsListView.OnRefreshListener;
import com.kn.uitls.HttpUtils;
import com.kn.uitls.JsonUtils;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	private String jsonStr;
	List<ZhiHuSummary> zhihu_list = new ArrayList<ZhiHuSummary>();
	ByteArrayOutputStream out = null;
	InputStream in = null;

	String[] title = new String[] { "学习了C语言真的可以开发出很多东西吗？",
			"如果我来你的城市旅行，有哪些没事是不可错过的？", "独立书店一般如何选购售卖的图书？",
			"甲骨文诉谷歌案，法院裁决Java API受版权保护，这是破坏性先例吗？", "title05", "title06",
			"title07" };
	String[] detail = new String[] { "编程的困惑：不是学C语言的错",
			"来你的城市·桂林除了山山水水，还有很多好吃的（多图）", "独立书店一般如何选购售卖的图书？",
			"几行代码引发Google和Oracle之间的官司", "detail05", "detail06", "detail07" };
	int[] image = new int[] { R.drawable.pic1, R.drawable.pic2,
			R.drawable.pic3, R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
			R.drawable.pic7, };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_list);

		ActionBar actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(true);
		actionbar.setHomeButtonEnabled(true);

		try {
//			out = new ByteArrayOutputStream();

			AssetManager am = MainActivity.this.getAssets();
			in = am.open("files/latest.json");

			jsonStr = HttpUtils.ChangeInputStream(in);

//			byte[] data = new byte[1024];
//			int len = 0;
//			while ((len = in.read(data)) != -1) {
//				out.write(data, 0, len);
//			}
//			in.close();
//			jsonStr = new String(out.toByteArray());
			
			zhihu_list = JsonUtils.Analysis_Lastest(jsonStr);
			Log.i(TAG, "获取的NEWS长度： " + zhihu_list.size());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < zhihu_list.size(); i++) {
			Log.i(TAG, i + " \t TITLE : " + zhihu_list.get(i).getTitle()
					+ " \t DESC : " + zhihu_list.get(i).getDesc()
					+ " \t IMAGE : " + zhihu_list.get(i).getImageUrl());
		}

		NewsAdapter adapter = new NewsAdapter(this, R.layout.news_item,
				zhihu_list);

		// List<Map<String, Object>> listItems = new ArrayList<Map<String,
		// Object>>();
		// for (int i = 0; i < title.length; i++) {
		// Map<String, Object> listItem = new HashMap<String, Object>();
		// listItem.put("image", image[i]);
		// listItem.put("title", title[i]);
		// listItem.put("detail", detail[i]);
		// listItems.add(listItem);
		// }
		// SimpleAdapter adpter1 = new SimpleAdapter(this, listItems,
		// R.layout.news_item,
		// new String[] { "image", "title", "detail" }, new int[] {
		// R.id.news_image, R.id.news_title, R.id.news_detail });

		final NewsListView lv_news_list = (NewsListView) this
				.findViewById(R.id.news_list);
		lv_news_list.setAdapter(adapter);

		/**
		 * 添加下拉刷新模块
		 */
		lv_news_list.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				new AsyncTask<Void, Void, Void>() {
					// 下拉刷新过程中需要做的操作
					protected Void doInBackground(Void... params) {
//						try {
//							Thread.sleep(1000);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
						return null;
					}

					// 刷新完成后通知listview进行界面调整
					protected void onPostExecute(Void result) {

						lv_news_list.onRefreshComplete();
						Toast.makeText(getApplication(), "刷新完成...",
								Toast.LENGTH_SHORT).show();
					}
				};
			}
		});

		/**
		 * 为新闻列表添加ItemClick监听事件
		 */
		lv_news_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this, NewsDetails.class);
				Bundle data = new Bundle();
				String news_id = zhihu_list.get(position).getId();

				data.putInt("itemId", position);
				data.putString("news_id", news_id);

				intent.putExtras(data);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem mi) {
		if (mi.isChecked()) {
			mi.setCheckable(true);
		}
		switch (mi.getItemId()) {
		case R.id.setting01:
			Toast.makeText(this, "设置1", 0).show();
			break;
		case R.id.setting02:
			Toast.makeText(this, "设置2", 0).show();
			break;
		case R.id.calendar: {
			Calendar c = Calendar.getInstance();
			new DatePickerDialog(this, 0,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							Toast.makeText(
									MainActivity.this,
									"你选择了：" + year + "年" + (monthOfYear + 1)
											+ "月" + dayOfMonth + "日", 0).show();
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
							.get(Calendar.DAY_OF_YEAR)).show();
		}
			break;
		}

		return false;
	}

}
