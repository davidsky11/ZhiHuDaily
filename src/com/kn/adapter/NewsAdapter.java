package com.kn.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kn.R;
import com.kn.entity.ZhiHuSummary;

/**
 * 知乎新闻数据（图片，文本）
 * @author kevin
 *
 * Created At 2014-8-25 下午4:34:16
 */
public class NewsAdapter extends BaseAdapter {
	
	private static final String TAG = "NewsAdapter";
	
	private int layoutId;
	private Context context;
	
	private List<ZhiHuSummary> zhs_list = new ArrayList<ZhiHuSummary>();

	public NewsAdapter(Context context, List<ZhiHuSummary> list) {
		this.context = context;
		this.zhs_list= list;
		Log.i(TAG, getClass().getName());
	}
	
	public NewsAdapter(Context context, int layoutId, List<ZhiHuSummary> list) {
		this.context = context;
		this.layoutId = layoutId;
		this.zhs_list = list;
		Log.i(TAG, getClass().getName() + "  ls.size " + list.size());
	}
	
	public NewsAdapter(Context context, List<? extends Map<String, ?>> data,
            int resource, String[] from, int[] to) {
		
	}
	
	@Override
	public int getCount() {
		return zhs_list.size();
	}

	@Override
	public Object getItem(int position) {
		return this.zhs_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		if (view == null)
			view = LayoutInflater.from(context)
					.inflate(layoutId, null);

		((TextView) view.findViewById(R.id.news_title)).setText(zhs_list.get(position).getTitle());
		((TextView) view.findViewById(R.id.news_detail)).setText(zhs_list.get(position).getDesc());
		
		((ImageView) view.findViewById(R.id.news_image)).setImageResource(R.drawable.ic_action_logo);
		
		return view;
	}

}
