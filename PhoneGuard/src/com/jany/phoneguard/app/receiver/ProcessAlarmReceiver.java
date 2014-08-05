package com.jany.phoneguard.app.receiver;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * ����ϵͳʱ�ӹ��������͹������¼�
 * @author miki
 *
 */
public class ProcessAlarmReceiver extends BroadcastReceiver {
	
	private static final String TAG = "ProcessAlarmReceiver";
	private Context context;//�����Ķ���

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		Log.i(TAG, "Process Alarm Receiver... ...");
		if(checkChangeSim() && getValueByKey().equals("1")) { //���������SIM����ͬʱ���Ͷ��ŵı�ʶ����Ϊ1��ʱ
			sednSMS();//���Ͷ���
		}
	}

	private void sednSMS() {
		SmsManager smsManager = SmsManager.getDefault();
		PendingIntent mPI = PendingIntent.getBroadcast(context, 0, new Intent(), 0);//����һ���յ�PendingIntent
		
		String strDestAddr = "";
		String strMessage = "���ĺ����ֻ����ܸ�����SIM��������ܱ���������";	//��ʾ��Ϣ
		
		if(strDestAddr != null && !"".equals(strDestAddr)){
			ArrayList<String> texts = smsManager.divideMessage(strMessage);
			for(String text: texts) {
				smsManager.sendTextMessage(strDestAddr, null, text, mPI, null);//���Ͷ��ŵ����ݺ�����ֻ�
			}
		}
		
	}
	
	// �����ʼ�
		private void sendEmail(){
			
		}
		// ���λ����Ϣ
		private void getLocationMsg(){
			
		}
		// ���������ļ�
		private void playSound(){
			
		}
		// ��������-��ϵ��ʽ
		private void backContacts(){
			
		}
		// �����ϵ��ʽ����
		private void eraseContacts(){
			
		}
		
		// ���ͼƬ
		private void eraseImages(){
			
		}

	private String getValueByKey() {
		// TODO Auto-generated method stub
		return "1";
	}

	// ����Ƿ������SIM��
	private boolean checkChangeSim() {
		// TODO Auto-generated method stub
		return true;
	}

}
