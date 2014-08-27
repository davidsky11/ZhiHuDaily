package com.kn.activity;

import com.kn.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Detail extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		
		Intent intent = getIntent();
		int count =intent.getIntExtra("itemId", -1);
		TextView tv01 = (TextView) findViewById(R.id.tv03);
		tv01.setText("完整内容:  第"+Integer.toString(count) +"条新闻");
	}

}
