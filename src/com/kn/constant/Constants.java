package com.kn.constant;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;

public class Constants {
	
	public Constants() {
		
	}

	public static final class Url {
		public static final String ZHIHU_DAILY_BEFORE = "http://news.at.zhihu.com/api/3/news/before/";
		public static final String ZHIHU_DAILY_OFFLINE_NEWS = "http://news-at.zhihu.com/api/3/news/";
        public static final String ZHIHU_DAILY_PURIFY_HEROKU_BEFORE = "http://zhihu-daily-purify.herokuapp.com/raw/";
        public static final String ZHIHU_DAILY_PURIFY_SAE_BEFORE = "http://zhihudailypurify.sinaapp.com/raw/";
        public static final String SEARCH = "http://zhihudailypurify.sinaapp.com/search/";
	}
	
	public static final class Date {
		@SuppressLint("SimpleDateFormat")
		public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	}

}
