package com.kn.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.kn.R;
import com.kn.constant.Constants;
import com.kn.entity.ZhiHuSummary;
import com.kn.task.DownloadAsyncTask;
import com.kn.ui.widget.NewsListView;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

//	private String jsonStr;
	private NewsListView lv_news_list;
	public List<ZhiHuSummary> zhihu_list = new ArrayList<ZhiHuSummary>();
//	private ByteArrayOutputStream out = null;
//	private InputStream in = null;
	private static final String jsonUrl = "http://10.0.2.2:8080/KnWebService/zhihu/latest";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_list);

		ActionBar actionbar = getActionBar();
		actionbar.setDisplayShowHomeEnabled(true);
		actionbar.setHomeButtonEnabled(true);

//		try {
//			out = new ByteArrayOutputStream();

//			AssetManager am = MainActivity.this.getAssets();
//			in = am.open("files/latest.json");
//
//			jsonStr = HttpUtils.ChangeInputStream(in);

//			byte[] data = new byte[1024];
//			int len = 0;
//			while ((len = in.read(data)) != -1) {
//				out.write(data, 0, len);
//			}
//			in.close();
//			jsonStr = new String(out.toByteArray());
			
//			zhihu_list = JsonUtils.Analysis_Lastest(jsonStr);
//			Log.i(TAG, "获取的NEWS长度： " + zhihu_list.size());

//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}

//		NewsAdapter adapter = new NewsAdapter(this, R.layout.news_item,
//				zhihu_list);

		lv_news_list = (NewsListView) this
				.findViewById(R.id.news_list);
//		lv_news_list.setAdapter(adapter);
		
//		new DownloadAsyncTask(this, lv_news_list, zhihu_list, Constants.DownType.NEWS_List_DOWN).execute(Constants.Url.ZHIHU_DAILY_LATEST);
		new DownloadAsyncTask(this, lv_news_list, zhihu_list, Constants.DownType.NEWS_List_DOWN).execute(jsonUrl);
		
		/**
		 * 添加下拉刷新模块
		 */
//		lv_news_list.setonRefreshListener(new OnRefreshListener() {
//
//			@Override
//			public void onRefresh() {
//
//				new AsyncTask<Void, Void, Void>() {
//					// 下拉刷新过程中需要做的操作
//					protected Void doInBackground(Void... params) {
//						try {
//							Thread.sleep(1000);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						return null;
//					}
//
//					// 刷新完成后通知listview进行界面调整
//					protected void onPostExecute(Void result) {
//
//						lv_news_list.onRefreshComplete();
//						Toast.makeText(getApplication(), "刷新完成...",
//								Toast.LENGTH_SHORT).show();
//					}
//				};
//			}
//		});

		/**
		 * 为新闻列表添加ItemClick监听事件
		 */
//		lv_news_list.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Intent intent = new Intent(MainActivity.this, NewsDetails.class);
//				Bundle data = new Bundle();
//				
//				if (zhihu_list.size() > 0) {
//					String news_id = zhihu_list.get(position).getId();
//
//					data.putInt("itemId", position);
//					data.putString("news_id", news_id);
//					Log.i(TAG, "传递的数据： itemId : " + position + " news_id : " + news_id);
//
//					intent.putExtras(data);
//					startActivity(intent);
//				} else {
//					Log.i(TAG, "zhihu_list为空！");
//				}
//				
//			}
//		});
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
			Toast.makeText(this, "设置1", Toast.LENGTH_LONG).show();
			break;
		case R.id.setting02:
			Toast.makeText(this, "设置2", Toast.LENGTH_LONG).show();
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
											+ "月" + dayOfMonth + "日", Toast.LENGTH_LONG).show();
						}
					}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
							.get(Calendar.DAY_OF_YEAR)).show();
		}
			break;
		}

		return false;
	}

}
