package com.kn.uitls;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
	
	private String url = "";
	
	public void Analysis_Lastest(String result) {
		JSONObject object = null;
		try {
			object = new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		String date = object.optString("date");
		
		List<ZhihuNews> 
		JSONArray stories = object.optJSONArray("stories");
		for (int i = 0; i < stories.length(); i++) {
			
		}
		
		String stories_type = stories.optString("type");
		String stories_id = stories.optString("id");
	}

	/**
	 * 网络请求，使用HttpClient
	 * @return
	 */
	public String RequestData() {
		HttpGet get = new HttpGet(url);
		HttpClient client = new DefaultHttpClient();
		StringBuilder builder = null;
		
		try {
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				InputStream inputStream = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				builder = new StringBuilder();
				String s = null;
				for (s = reader.readLine(); s != null; s = reader.readLine()) {
					builder.append(s);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
}
