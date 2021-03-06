package com.ldw.music.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ldw.music.model.FolderInfo;

/**
 * 数据表FolderInfo操作类
 * @author 慎之
 *
 */
public class FolderInfoDao {

	private static final String TABLE_FOLDER = "folder_info";
	private Context mContext;
	
	public FolderInfoDao(Context context) {
		this.mContext = context;
	}
	
	/**
	 * 将FolderInfo集合中的数据存放进数据库
	 * @param list
	 */
	public void saveFolderInfo(List<FolderInfo> list) {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		for (FolderInfo info : list) {
			ContentValues cv = new ContentValues();
			cv.put("folder_name", info.folder_name);
			cv.put("folder_path", info.folder_path);
			db.insert(TABLE_FOLDER, null, cv);
		}
	}
	
	/**
	 * 将数据表FolderInfo中的数据封装进FolderInfo集合中
	 * @return
	 */
	public List<FolderInfo> getFolderInfo() {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		List<FolderInfo> list = new ArrayList<FolderInfo>();
		String sql = "select * from " + TABLE_FOLDER;
		Cursor cursor = db.rawQuery(sql, null);
		while(cursor.moveToNext()) {
			FolderInfo info = new FolderInfo();
			info.folder_name = cursor.getString(cursor.getColumnIndex("folder_name"));
			info.folder_path = cursor.getString(cursor.getColumnIndex("folder_path"));
			list.add(info);
		}
		cursor.close();
		return list;
	}
	
	/**
	 * 检查数据表FolderInfo中是否有数据
	 * @return
	 */
	public boolean hasData() {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		String sql = "select count(*) from " + TABLE_FOLDER;
		Cursor cursor = db.rawQuery(sql, null);
		boolean has = false;
		if(cursor.moveToFirst()) {
			int count = cursor.getInt(0);
			if(count > 0) {
				has = true;
			}
		}
		cursor.close();
		return has;
	}
	
	/**
	 * 获取FolderInfo表中的数据条数
	 * @return
	 */
	public int getDataCount() {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		String sql = "select count(*) from " + TABLE_FOLDER;
		Cursor cursor = db.rawQuery(sql, null);
		int count = 0;
		if(cursor.moveToFirst()) {
			count = cursor.getInt(0);
		}
		return count;
	}
}
