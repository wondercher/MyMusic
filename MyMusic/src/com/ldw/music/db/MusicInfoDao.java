package com.ldw.music.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ldw.music.activity.IConstants;
import com.ldw.music.model.MusicInfo;

/**
 * MusicInfo表的数据库操作类
 * @author 慎之
 *
 */
public class MusicInfoDao implements IConstants {
	
	private static final String TABLE_MUSIC = "music_info";
	private Context mContext;
	
	public MusicInfoDao(Context context) {
		this.mContext = context;
	}
	
	/**
	 * 将MusicInfo集合对象存入数据库中
	 * @param list
	 */
	public void saveMusicInfo(List<MusicInfo> list) {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		for (MusicInfo music : list) {
			//contenvalues只能存储基本类型的数据
			ContentValues cv = new ContentValues();
			cv.put("songid", music.songId);
			cv.put("albumid", music.albumId);
			cv.put("duration", music.duration);
			cv.put("musicname", music.musicName);
			cv.put("artist", music.artist);
			cv.put("data",music.data);
			cv.put("folder", music.folder);
			cv.put("musicnamekey", music.musicNameKey);
			cv.put("artistkey", music.artistKey);
			cv.put("favorite", music.favorite);
			db.insert(TABLE_MUSIC, null, cv);
		}
	}
	
	/**
	 * 获取数据库中的数据，并封装进MusicInfo集合
	 * @return
	 */
	public List<MusicInfo> getMusicInfo() {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		String sql = "select * from " + TABLE_MUSIC;
		
		return parseCursor(db.rawQuery(sql, null));
	}
	
	/**
	 * 读取游标对象，将游标对象中的值存放进MusicInfo集合
	 * @param cursor
	 * @return
	 */
	private List<MusicInfo> parseCursor(Cursor cursor) {
		List<MusicInfo> list = new ArrayList<MusicInfo>();
		while(cursor.moveToNext()) {
			MusicInfo music = new MusicInfo();
			music._id = cursor.getInt(cursor.getColumnIndex("_id"));
			music.songId = cursor.getInt(cursor.getColumnIndex("songid"));
			music.albumId = cursor.getInt(cursor.getColumnIndex("albumid"));
			music.duration = cursor.getInt(cursor.getColumnIndex("duration"));
			music.musicName = cursor.getString(cursor.getColumnIndex("musicname"));
			music.artist = cursor.getString(cursor.getColumnIndex("artist"));
			music.data = cursor.getString(cursor.getColumnIndex("data"));
			music.folder = cursor.getString(cursor.getColumnIndex("folder"));
			music.musicNameKey = cursor.getString(cursor.getColumnIndex("musicnamekey"));
			music.artistKey = cursor.getString(cursor.getColumnIndex("artistkey"));
			music.favorite = cursor.getInt(cursor.getColumnIndex("favorite"));
			list.add(music);
		}
		cursor.close();
		return list;
	}
	
	/**
	 * 根据分类获取数据库中的音乐对象集合
	 * @param selection 从外部传入的参数值
	 * @param type 查找类型（歌手，专辑，文件夹）
	 * @return
	 */
	public List<MusicInfo> getMusicInfoByType(String selection, int type) {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		String sql = "";
		if(type == START_FROM_ARTIST) {
			sql = "select * from " + TABLE_MUSIC + " where artist = ?";
		} else if(type == START_FROM_ALBUM) {
			sql = "select * from " + TABLE_MUSIC + " where albumid = ?";
		} else if(type == START_FROM_FOLDER) {
			sql = "select * from " + TABLE_MUSIC + " where folder = ?";
		}
		return parseCursor(db.rawQuery(sql, new String[]{ selection }));
	}
	
	/**
	 * 根据歌曲的ID设置其喜欢（收藏）属性（0，1）
	 * @param id 歌曲的ID
	 * @param favorite
	 */
	public void setFavoriteStateById(int id, int favorite) {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		String sql = "update " + TABLE_MUSIC + " set favorite = " + favorite + " where _id = " + id;
		db.execSQL(sql);
	}
	
	/**
	 * 检查数据表MusicInfo中是否有数据
	 * @return
	 */
	public boolean hasData() {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		String sql = "select count(*) from " + TABLE_MUSIC;
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
	 * 获取数据表MusicInfo中数据条数
	 * @return
	 */
	public int getDataCount() {
		SQLiteDatabase db = DatabaseHelper.getInstance(mContext);
		String sql = "select count(*) from " + TABLE_MUSIC;
		Cursor cursor = db.rawQuery(sql, null);
		int count = 0;
		if(cursor.moveToFirst()) {
			count = cursor.getInt(0);
		}
		return count;
	}

}
