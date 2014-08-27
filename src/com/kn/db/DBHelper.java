package com.kn.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = "DBHelper";
	private static final String DATABASE_NAME = "ACCOUNT";
	private static final String DETAILS_CREATE_SQL = "CREATE TABLE IF NOT EXISTS details ('_id' integer not null autoincrement , 'details_date' data, 'account_id' integer, 'type_id' integer, 'details_money' double, primary key('_id'))";
	private static final String DETAILS_DROP_SQL = "DROP TABLE details IF NOT EXISTS";
	private static final String USER_CREATE_SQL = "CREATE TABLE IF NOT EXISTS user ('_id' integer not null autoincrement, 'username' text, 'password' text, primary key('_id'))";
	private static final String USER_DROP_SQL = "DROP TABLE user IF NOT EXISTS";
	private static final int VERSION = 1;
	private SQLiteDatabase db;

	public DBHelper(Context context, String name, CursorFactory factory) {
		super(context, DATABASE_NAME, factory, VERSION);
	}
	
	/**
	 * 关闭数据库连接
	 */
	public void close() {
		db.close();
	}

	/**
	 * 创建数据库后，对数据库的操作
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		Log.i(TAG, "Creating table for schema version " + VERSION);
		db.execSQL(DETAILS_CREATE_SQL);
		db.execSQL(USER_CREATE_SQL);
	}

	/**
	 * 更新数据库版本的操作
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,
			int newVersion) {
		this.db = db;
		db.execSQL(DETAILS_DROP_SQL);
		db.execSQL(USER_DROP_SQL);
	}
	
	@Override
	public void onOpen(SQLiteDatabase db) {
		super.onOpen(db);
	}

	public <T> void save(T t) {
		this.db = getWritableDatabase();
		new ContentValues();
	}
}
