package com.kn.ui.fragment;

import com.kn.R;
import com.kn.constant.Constants;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 知乎新闻条目
 * @author kevin
 *
 * Created At 2014-8-25 下午4:34:55
 */
public class NewsFragment extends Fragment  {
	
	private static final String TAG = "NewsFragment";
	private ImageView imageView;
	private TextView tv_title, tv_desc;
	
	private int layoutId = R.layout.news_item;
	
	public NewsFragment() {
		Log.i(TAG, getClass().getName());
	}
	
	public NewsFragment(int layoutId) {
		super();
		this.layoutId = layoutId;
		Log.i(TAG, getClass().getName() + " 带layoutId的构造方法");
	}
	

	public void test() {
		System.out.println(Constants.Url.SEARCH);
	}
}
