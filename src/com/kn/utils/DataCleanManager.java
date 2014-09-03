package com.kn.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;

/**
 * 应用数据清理
 * @author kevin
 *
 * Created At 2014-9-3 下午2:12:26
 */
public class DataCleanManager {

	/**
	 * 清除本应用内部缓存(/data/data/###/cache)
	 * @param context
	 */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}
	
	/**
	 * 清除本应用所有数据库(/data/data/###/databases)
	 * @param context
	 */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
	}
	
	/**
	 * 清除/data/data/###/files下的内容
	 * @param context
	 */
	public static void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}
	
	/**
	 * 清除外部cache下的内容(mnt/sdcard/android/data/###/cache)
	 * @param context
	 */
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}
	
	/**
	 * 清除本应用SharedPreference(/data/data/###/shared_prefs)
	 * @param context
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
	}
	
	/**
	 * 清除自定义路径下的文件(仅支持目录下的文件删除)
	 * @param filePath
	 */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}
	
	public static void cleanApplicationData(Context context, String... filePaths) {
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filePaths) {
			cleanCustomCache(filePath);
		}
	}
	
	/**
	 * 删除操作（只删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理）
	 * @param directory
	 */
	public static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				file.delete();
			}
		}
	}
}
