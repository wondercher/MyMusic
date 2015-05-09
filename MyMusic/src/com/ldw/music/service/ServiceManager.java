package com.ldw.music.service;

import java.util.ArrayList;
import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;

import com.ldw.music.activity.IConstants;
import com.ldw.music.aidl.IMediaService;
import com.ldw.music.interfaces.IOnServiceConnectComplete;
import com.ldw.music.model.MusicInfo;

/**
 * 控制Service
 * @author 慎之
 *
 */
public class ServiceManager implements IConstants {

	public IMediaService mService;
	private Context mContext;
	private ServiceConnection mConn;
	private IOnServiceConnectComplete mIOnServiceConnectComplete;

	public ServiceManager(Context context) {
		this.mContext = context;
		initConn();
	}
	
	/**
	 * 初始化连接
	 */
	private void initConn() {
		mConn = new ServiceConnection() {
			
			@Override
			public void onServiceDisconnected(ComponentName name) {
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				mService = IMediaService.Stub.asInterface(service);
				if (mService != null) {
					mIOnServiceConnectComplete.onServiceConnectComplete(mService);
				}
			}
		};
	}
	
	/**
	 * 连接绑定服务
	 */
	public void connectService() {
		Intent intent = new Intent("com.ldw.music.service.MediaService");
		mContext.bindService(intent, mConn, Context.BIND_AUTO_CREATE);
	}
	
	/**
	 * 取消绑定服务
	 */
	public void disConnectService() {
		mContext.unbindService(mConn);
		mContext.stopService(new Intent("com.ldw.music.service.MediaService"));
	}
	
	/**
	 * 刷新音乐列表
	 * @param musicList
	 */
	public void refreshMusicList(List<MusicInfo> musicList) {
		if(musicList != null && mService != null) {
			try {
				mService.refreshMusicList(musicList);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取音乐列表
	 * @return
	 */
	public List<MusicInfo> getMusicList() {
		List<MusicInfo> musicList = new ArrayList<MusicInfo>();
		try {
			if(mService != null) {
				mService.getMusicList(musicList);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return musicList;
	}
	
	/**
	 * 播放音乐
	 * @param pos
	 * @return
	 */
	public boolean play(int pos) {
		if(mService != null) {
			try {
				return mService.play(pos);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 根据歌曲ID进行播放歌曲
	 * @param id
	 * @return
	 */
	public boolean playById(int id) {
		if(mService != null) {
			try {
				return mService.playById(id);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 重新播放歌曲
	 * @return
	 */
	public boolean rePlay() {
		if(mService != null) {
			try {
				return mService.rePlay();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 暂停播放歌曲
	 * @return
	 */
	public boolean pause() {
		if(mService != null) {
			try {
				return mService.pause();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 播放上一首歌曲
	 * @return
	 */
	public boolean prev() {
		if(mService != null) {
			try {
				return mService.prev();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 播放下一首歌曲
	 * @return
	 */
	public boolean next() {
		if(mService != null) {
			try {
				return mService.next();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 歌曲跳转到给定的位置开始播放
	 * @param progress
	 * @return
	 */
	public boolean seekTo(int progress) {
		if(mService != null) {
			try {
				return mService.seekTo(progress);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 获取歌曲播放的位置
	 * @return
	 */
	public int position() {
		if(mService != null) {
			try {
				return mService.position();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/**
	 * 获取歌曲播放的总时间
	 * @return
	 */
	public int duration() {
		if(mService != null) {
			try {
				return mService.duration();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/**
	 * 获取歌曲播放的状态
	 * @return
	 */
	public int getPlayState() {
		if(mService != null) {
			try {
				int mode = mService.getPlayState();
				return mService.getPlayState();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/**
	 * 设置歌曲播放的模式（循环，随机..）
	 * @param mode
	 */
	public void setPlayMode(int mode) {
		if(mService != null) {
			try {
				mService.setPlayMode(mode);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取歌曲播放的模式
	 * @return
	 */
	public int getPlayMode() {
		if(mService != null) {
			try {
				return mService.getPlayMode();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	/**
	 * 获取当前播放歌曲的ID
	 * @return
	 */
	public int getCurMusicId() {
		if(mService != null) {
			try {
				return mService.getCurMusicId();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	/**
	 * 获取当前播放歌曲的信息，并封装在MusicInfo对象
	 * @return
	 */
	public MusicInfo getCurMusic() {
		if(mService != null) {
			try {
				return mService.getCurMusic();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 发送播放歌曲的广播
	 */
	public void sendBroadcast() {
		if(mService != null) {
			try {
				mService.sendPlayStateBrocast();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 退出
	 */
	public void exit() {
		if(mService != null) {
			try {
				mService.exit();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		mContext.unbindService(mConn);
		mContext.stopService(new Intent(SERVICE_NAME));
	}
	
	/**
	 * 更新专辑图片，标题，名称等信息
	 * @param bitmap
	 * @param title
	 * @param name
	 */
	public void updateNotification(Bitmap bitmap, String title, String name) {
		try {
			mService.updateNotification(bitmap, title, name);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取消显示专辑图片，标题，名称等信息
	 */
	public void cancelNotification() {
		try {
			mService.cancelNotification();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	
	public void setOnServiceConnectComplete(IOnServiceConnectComplete IServiceConnect) {
		mIOnServiceConnectComplete = IServiceConnect;
	}

}
