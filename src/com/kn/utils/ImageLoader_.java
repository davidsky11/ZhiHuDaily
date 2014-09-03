package com.kn.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

/**
 * 对图片进行管理的工具类
 * @author kevin
 *
 * Created At 2014-9-3 上午11:23:58
 */
public class ImageLoader_ {

	// 图片缓存技术的核心类，用于缓存所有下载完成的图片，在程序内存达到设定值时会将最少最近使用的图片移除
	private static LruCache<String, Bitmap> mMemoryCache;
	
	// ImageLoader_的实例
	private static ImageLoader_ mImageLoader;
	
	public ImageLoader_() {
		// 获取应用程序最大可用内存
		int maxMemory = (int) Runtime.getRuntime().maxMemory(); 
		int cacheSize = maxMemory / 8;
		
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}
	
	/**
	 * 获取ImageLoader_的实例
	 * @return
	 */
	public static ImageLoader_ getInstance() {
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader_();
		}
		return mImageLoader;
	}
	
	/**
	 * 将一张图片存储到LruCache中
	 * @param key		对应图片的URL地址
	 * @param bitmap	对应传入从网络上下载的Bitmap对象
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}
	
	/**
	 * 从LruCache中获取一张图片
	 * @param key	对应传入图片的URL地址
	 * @return		获取的Bitmap对象
	 */
	public Bitmap getBitmapFromMemoryCache(String key) {
		return mMemoryCache.get(key);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth) {
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (width > reqWidth) {
			// 计算出实际宽度和目标宽度的比例
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = widthRatio;
		}
		return inSampleSize;
	}
	
	public static Bitmap decodeSampleBitmapFromResource(String pathName, int reqWidth) {
		// 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		// 调用上面定义的方法计算inSampleSize值
		options.inSampleSize = calculateInSampleSize(options, reqWidth);
		// 使用获取到的inSampleSize值再次解析图片
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathName, options);
	}
}
