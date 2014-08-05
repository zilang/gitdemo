package com.jany.phoneguard.app.receiver;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * 处理系统时钟管理器发送过来的事件
 * @author miki
 *
 */
public class ProcessAlarmReceiver extends BroadcastReceiver {
	
	private static final String TAG = "ProcessAlarmReceiver";
	private Context context;//上下文对象

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		Log.i(TAG, "Process Alarm Receiver... ...");
		if(checkChangeSim() && getValueByKey().equals("1")) { //如果更换了SIM卡，同时发送短信的标识设置为1的时
			sednSMS();//发送短信
		}
	}

	private void sednSMS() {
		SmsManager smsManager = SmsManager.getDefault();
		PendingIntent mPI = PendingIntent.getBroadcast(context, 0, new Intent(), 0);//建立一个空的PendingIntent
		
		String strDestAddr = "";
		String strMessage = "您的好友手机可能更换了SIM卡，或可能被盗！！！";	//提示信息
		
		if(strDestAddr != null && !"".equals(strDestAddr)){
			ArrayList<String> texts = smsManager.divideMessage(strMessage);
			for(String text: texts) {
				smsManager.sendTextMessage(strDestAddr, null, text, mPI, null);//发送短信到备份号码的手机
			}
		}
		
	}
	
	// 发送邮件
		private void sendEmail(){
			
		}
		// 获得位置信息
		private void getLocationMsg(){
			
		}
		// 播放声音文件
		private void playSound(){
			
		}
		// 备份数据-联系方式
		private void backContacts(){
			
		}
		// 清除联系方式数据
		private void eraseContacts(){
			
		}
		
		// 清除图片
		private void eraseImages(){
			
		}

	private String getValueByKey() {
		// TODO Auto-generated method stub
		return "1";
	}

	// 检测是否更换了SIM卡
	private boolean checkChangeSim() {
		// TODO Auto-generated method stub
		return true;
	}

}
