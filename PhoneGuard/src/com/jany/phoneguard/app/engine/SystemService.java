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
		
		long triggerAtTime= System.currentTimeMillis(); //��ȡ��ǰʱ��
		long interval =  1000*60*1;// ÿһ����
		Intent receiverIntent = new Intent("com.jany.phoneguard.app.action.PROCESS_ALARM");//����Receiver��Action
		
		operation = PendingIntent.getBroadcast(getApplicationContext(), 0, receiverIntent, 0);//��ȡһ����ִ�й㲥�¼���PendingIntent
		
		if(alarmManager != null) {
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, interval, operation);
		}else {
			alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtTime, interval, operation);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * �ڴ˷�����ʵ�ַ����ѭ����������͵����˴�App�ķ����޷���ϵͳkill��
	 * �Ӷ�ʵ����ֻҪ�ֻ������� App�ͻ���Զ����
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(alarmManager != null) {
			alarmManager.cancel(operation);//ֹͣ�㲥�¼�
		}
		
		if(intent != null) {
			String stop = intent.getStringExtra("stop");
			if(stop != null && stop.equals(MenuActivity.STOP_SERVICE)) {
				return;
			}
			
			if(stop == null || !stop.equals(MenuActivity.STOP_SERVICE)) {//���û�û������ֹͣ���������ʱ����������Service����ʱ���Լ������Լ�����������ѭ��
				startService(intent);
			}
		}
	}
}
