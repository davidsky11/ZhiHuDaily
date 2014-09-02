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
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kn.R;
import com.kn.entity.ZhiHuSummary;
import com.kn.uitls.ImageLoader;

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
	private ImageLoader imageLoader;

	private List<ZhiHuSummary> zhihu_list = new ArrayList<ZhiHuSummary>();

	public NewsAdapter() {
		
	}
	
	public NewsAdapter(Context context, List<ZhiHuSummary> zhihu_list) {
		this.context = context;
		this.zhihu_list = zhihu_list;
		this.imageLoader = new ImageLoader();
//		this.imageLoader = new ImageLoader(context);
		Log.i(TAG, getClass().getName());
	}

	public NewsAdapter(Context context, int layoutId,
			List<ZhiHuSummary> zhihu_list) {
		this.context = context;
		this.layoutId = layoutId;
		this.zhihu_list = zhihu_list;
		this.imageLoader = new ImageLoader(context);
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

		if (view == null)
			view = LayoutInflater.from(context).inflate(layoutId, null);

		((TextView) view.findViewById(R.id.news_title)).setText(zhihu_list.get(
				position).getTitle());
		((TextView) view.findViewById(R.id.news_detail)).setText(zhihu_list
				.get(position).getDesc());

		ImageView imageView = (ImageView) view.findViewById(R.id.news_image);
		
		String imgUrl = zhihu_list.get(position).getImageUrl();
		Log.i(TAG, imgUrl);
		
		imageLoader.DisplayImage(imgUrl, imageView);
//		new PicDownloadAsyncTask((ImageView) view.findViewById(R.id.news_image))
//				.execute(zhihu_list.get(position).getImageUrl());

		return view;
	}

	private class PicDownloadAsyncTask extends AsyncTask<String, Void, Bitmap> {

		private ImageView imageView;

		public PicDownloadAsyncTask(ImageView imageView) {
			this.imageView = imageView;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			String urlStr = params[0]; // 获取下载链接
			Bitmap bitMap = null;

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
					return BitmapFactory.decodeStream(connection.getInputStream());
				}
				
//				URL imgUrl = new URL(urlStr);
//				bitMap = BitmapFactory.decodeStream(imgUrl.openStream());

			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitMap;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onCancelled(Bitmap result) {
			super.onCancelled(result);

			((ImageView) imageView.findViewById(R.id.news_image))
					.setImageBitmap(result);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

	}
	
	public Bitmap getBitmap(String imageUrl){  
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
