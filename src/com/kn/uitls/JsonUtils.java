package com.kn.uitls;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;
import android.util.Log;

import com.kn.entity.ZhiHuSummary;

public class JsonUtils extends AndroidTestCase {

	private static final String TAG = "JsonUtils";

	private String url = "";

	public static Map<String, String> Analysis_Offline(String jsonStr)
			throws JSONException {

		Map<String, String> mMap = new HashMap<String, String>();

		JSONObject object = new JSONObject(jsonStr);
		
//		if (object.has("body")) {
//			Document doc = Jsoup.parse(object.getString("body"));
//			
//			Elements viewMoreElements = doc.getElementsByClass("view-more");		// 查看知乎讨论
//			
//			if (viewMoreElements.size() > 1) {
//				
//			}
//		}
//		Log.i(TAG, object.optString("body"));
		
		mMap.put("body", object.optString("body"));
		mMap.put("css", object.optString("css"));

		return mMap;
	}

	public static List<ZhiHuSummary> Analysis_Lastest(String jsonStr) throws JSONException {

		List<ZhiHuSummary> zhs_list = new ArrayList<ZhiHuSummary>();

		JSONObject object = new JSONObject(jsonStr);

		JSONArray stories = object.optJSONArray("stories");
		for (int i = 0; i < stories.length(); i++) {
			ZhiHuSummary zhs = new ZhiHuSummary();
			JSONObject story = (JSONObject) stories.getJSONObject(i);

			zhs.setTitle(story.has("title") ? story.getString("title") : null);
			zhs.setImageUrl(story.has("images") ? (String) story.getJSONArray(
					"images").get(0) : null);
			zhs.setId(story.has("id") ? story.getString("id") : null);

			if (i == 0) 
				System.out.println(zhs.getTitle() + " == " + zhs.getImageUrl() + " == " + zhs.getId());
			zhs_list.add(zhs);
		}
		Log.i(TAG, "获取的知乎新闻数目：" + zhs_list.size());

		return zhs_list;
	}

	/**
	 * 网络请求，使用HttpClient
	 * 
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
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
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
