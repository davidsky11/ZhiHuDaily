package com.kn.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kn.R;
import com.kn.entity.ZhiHuSummary;
import com.kn.ui.widget.NewsListView;
import com.kn.utils.MyImageLoader;
import com.kn.utils.MyImageLoader.ImageCallback;

/**
 * 知乎新闻数据（图片，文本）
 * 
 * @author kevin
 * 
 *         Created At 2014-8-25 下午4:34:16
 */
public class NewsAdapter extends BaseAdapter {

	private static final String TAG = "NewsAdapter";

	private int layoutId;
	private Context context;
	private NewsListView newsListView;
	private MyImageLoader myImageLoader;

	private List<ZhiHuSummary> zhihu_list = new ArrayList<ZhiHuSummary>();

	public NewsAdapter(Context context) {
		this.context = context;
		myImageLoader = new MyImageLoader();
	}

	public NewsAdapter(Context context, NewsListView newsListView,
			List<ZhiHuSummary> zhihu_list) {
		this.context = context;
		this.zhihu_list = zhihu_list;
		this.newsListView = newsListView;
		myImageLoader = new MyImageLoader();
	}

	public NewsAdapter(Context context, NewsListView newsListView,
			List<ZhiHuSummary> zhihu_list, int layoutId) {
		this.context = context;
		this.layoutId = layoutId;
		this.zhihu_list = zhihu_list;
		this.newsListView = newsListView;
		myImageLoader = new MyImageLoader();
	}

	@Override
	public int getCount() {
		return zhihu_list.size();
	}

	@Override
	public Object getItem(int position) {
		return this.zhihu_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		View rowView = view;

		if (rowView == null) {
			rowView = LayoutInflater.from(context).inflate(layoutId, null);
			rowView.setTag("rowView");
		}

		((TextView) rowView.findViewById(R.id.news_title)).setText(zhihu_list
				.get(position).getTitle());
		((TextView) rowView.findViewById(R.id.news_detail)).setText(zhihu_list
				.get(position).getDesc());

		ImageView imageView = (ImageView) rowView.findViewById(R.id.news_image);
		String imageUrl = zhihu_list.get(position).getImageUrl();
		imageView.setTag(imageUrl);
		Log.i(TAG, imageUrl);

		Drawable cachedImage = myImageLoader.loadDrawable(imageUrl,
				new ImageCallback() {

					@Override
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						ImageView imageViewByTag = (ImageView) newsListView
								.findViewWithTag(imageUrl);
						if (imageViewByTag != null) {
							imageViewByTag.setImageDrawable(imageDrawable);
						}
					}

				});
		if (cachedImage == null) {
			imageView.setImageResource(R.drawable.no_image);
		} else {
			imageView.setImageDrawable(cachedImage);
		}

		return rowView;
	}

	/**
	 * 从URL获取位图
	 * @param imageUrl
	 * @return
	 */
	public Bitmap getBitmap(String imageUrl) {
		Bitmap mBitmap = null;
		try {
			URL url = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			mBitmap = BitmapFactory.decodeStream(is);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mBitmap;
	}
}
