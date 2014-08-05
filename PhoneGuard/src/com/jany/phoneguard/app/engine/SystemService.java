package com.jany.phoneguard.app.engine;

import com.jany.phoneguard.app.MenuActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class SystemService extends Service {
	
	private static final String TAG = "SystemService";
	
	private Intent intent;
	private AlarmManager alarmManager;
	private PendingIntent operation;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		this.intent = intent;
		
		Log.i(TAG, "SystemService-onStartCommand");
		
		long triggerAtTime= System.currentTimeMillis(); //获取当前时间
		long interval =  1000*60*1;// 每一分钟
		Intent receiverIntent = new Intent("com.jany.phoneguard.app.action.PROCESS_ALARM");//设置Receiver的Action
		
		operation = PendingIntent.getBroadcast(getApplicationContext(), 0, receiverIntent, 0);//获取一个将执行广播事件的PendingIntent
		
		if(alarmManager != null) {
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, interval, operation);
		}else {
			alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, interval, operation);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 在此方法中实现服务的循环重启，这就导致了此App的服务无法被系统kill，
	 * 从而实现了只要手机开机， App就会永远运行
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(alarmManager != null) {
			alarmManager.cancel(operation);//停止广播事件
		}
		
		if(intent != null) {
			String stop = intent.getStringExtra("stop");
			if(stop != null && stop.equals(MenuActivity.STOP_SERVICE)) {
				return;
			}
			
			if(stop == null || !stop.equals(MenuActivity.STOP_SERVICE)) {//当用户没有主动停止监听服务的时候，重新启动Service，此时是自己启动自己，进而导致循环
				startService(intent);
			}
		}
	}
}
