package com.kn.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;

public class DownloadAsyncTask extends AsyncTask<String, Void, String> {
	
	

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
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

		String urlStr = params[0];
		
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(3000);
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

		return jsonString;
	}

}