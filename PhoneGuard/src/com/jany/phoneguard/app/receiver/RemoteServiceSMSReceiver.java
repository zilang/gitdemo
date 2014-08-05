package com.jany.phoneguard.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class RemoteServiceSMSReceiver extends BroadcastReceiver {

	private static final String TAG = "RemoteServiceSMSReceiver";
	private static final String mACTION = "android.provider.Telephony.SMS_RECEIVED";	//�յ�����ʱϵͳ�ᷢ�����и�Action��Intent
	//	private PhoneDbAdapter phoneDbAdapter;	//ҵ�������
	public static final String START_SYSTEM_SERVICE = "000";	//Զ�̿���
	public static final String STOP_SYSTEM_SERVICE = "001";	//Զ�̹ػ�
	public static final String MONITOR_SMS = "002";	//��ȡ����
	public static final String MONITOR_TELEPHONY = "003";	//�绰¼��
	public static final String DELETE_PICTURE = "004";	//ɾ��ͼƬ
	public static final String DELETE_SMS = "005";	//ɾ������
	public static final String UPLOAD_TELEPHONY_NUMBERS = "006";	//�ϴ���ϵ����Ϣ
	public static final String UPLOAD_PICTURE = "007";	//�ϴ�ͼƬ
	public static final String PLAY_SOUND = "008";	//���ű�����
	public static final String STOP_SOUND = "009";	//ֹͣ���ű�����
	private MediaPlayer mp;	//ý�岥����
	private String messageBody;	//���͹�������Ϣ��

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// \t ��ϵͳЭ���ʽ��\n
	// \t \t HH101(Э��ͷ)+CMD(����ͷ)+�������+��½����(���磬123)��\n
	// \t Զ������ϵͳ���001,����\n
	// \t \t HH101CMD001123��\n
	// \t Զ�̹ر�ϵͳ���002,����\n
	// \t \t HH101CMD002123��\n
	//	
	// \t Զ�̲����������008,����\n
	// \t \t HH101CMD008123��\n
	// \t Զ�̹ر��������009,����\n
	// \t \t HH101CMD009123��\n
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String PROTOCAL_HEADER = "HH101CMD";	//����Э��ͷ+����ͷ

	private String currentNumber;	// �����ĵ绰����

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(mACTION)) {	// ������յ������¼�
			if(intent.getExtras() != null) {
				messageBody = parserSMS(intent.getExtras());
				
				if(messageBody != null && !"".equals(messageBody) && messageBody.length() > PROTOCAL_HEADER.length() + 3) {//��������������Э���ʽҪ�����Ϣ��
					if(messageBody.substring(0, 8).equals(PROTOCAL_HEADER)) {//����Э��ͷ
						String cmd = messageBody.substring(8, 11);//��ȡ����
						String password = messageBody.substring(11);//��ȡ����
						
						Log.i("CMD", cmd);
						Log.d("password", password);
						
						//TODO validate the user input pwd
						
						if(password != null && password.equals("123")) {//���͹�����Э������벿�ֺ��û�������ͬʱ����ʼ��������Э������
							onHandleCMD(cmd);
						}
					}
				}
			}
		}
	}

	/**
	 * �����Զ���Э��ָ��������Ӧ�Ķ���
	 * 
	 * @param cmd
	 */
	private void onHandleCMD(String cmd) {
		if(cmd != null && !"".equals(cmd)) {
			
			//Զ�̿���
			if(cmd.equals(START_SYSTEM_SERVICE)) {
				
				Log.i(TAG, "Start the System Service");
				return;
			}
			
			//Զ�̹ػ�
			if (cmd.equals(STOP_SYSTEM_SERVICE))
			{
				Log.i(TAG, "Stop the System Service");
				return;
			}
			
			//��ȡ����
			if (cmd.equals(MONITOR_SMS))
			{
				Log.i(TAG, "Get the Phone SMS");
				return;
			}
			
			//�绰¼��
			if (cmd.equals(MONITOR_TELEPHONY))
			{
				Log.i(TAG, "Start Phone Record Service");
//				Intent service = new Intent(context, PhoneService.class);
//				context.startService(service);
				return;
			}
			
			//ɾ��ͼƬ
			if (cmd.equals(DELETE_PICTURE))
			{
				Log.i(TAG, "delete the picture");
				return;
			}
			
			//ɾ������
			if (cmd.equals(DELETE_SMS))
			{
				Log.i(TAG, "delete the SMS");
				return;
			}
			
			//�ϴ���ϵ����Ϣ
			if (cmd.equals(UPLOAD_TELEPHONY_NUMBERS))
			{
				Log.i(TAG, "upload the telephony numbers");
//				Intent service = new Intent(context, ContactsService.class);
//				context.startService(service);
				return;
			}
			
			//�ϴ�ͼƬ
			if (cmd.equals(UPLOAD_PICTURE))
			{
				Log.i(TAG, "upload the picture");
				return;
			}
			
			// ���ű�������
			if (cmd.equals(PLAY_SOUND)) {	//����ǲ�������������
//				mp = MediaPlayer.create(context, R.raw.alarm2);	//ʵ����MiaPlayer,��create�������ڲ����Զ�����prepare����
				
				Log.i("mp", "mp has already been prepared ");
				
				Log.i("play", "play.........");
//				mp.setLooping(true);	//���ò�����ѭ�����ű�������
//				mp.start();	//��ʼ����
				
				return;
			}
			
			// ֹͣ������������
			if (cmd.equals(STOP_SOUND) && mp != null) {	//�����ֹͣ������������
//				if(mp.isLooping()){
//					mp.setLooping(false);	//ֹͣѭ��
//				}
//				mp.stop();	//ֹͣ��������
//				mp.release();	//�ͷ������Դ
				Log.i(TAG, "Stop alerm");
				return;
			}
			
		}else {
			Log.w(TAG, "ָ��Ϊ��");
		}
		
	}

	/**
	 * ��Bundle�н�����������
	 * �����Զ���Э��ָ��ͷ��ͺ���
	 * @param extras
	 * @return ��������
	 */
	private String parserSMS(Bundle extras) {//��ȡ�����intent�е�Bundle����
		StringBuffer sb = new StringBuffer();
		Object[] pdus = (Object[]) extras.get("pdus");//��ö��ŵ��ֽ�������������
		SmsMessage[] messages = new SmsMessage[pdus.length];//����һ��ͬ�����ȵ�SmsMessage����
		for(int i=0; i<messages.length; i++) {//���������ֽ�����������
			messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);//ʵ����ÿһ�����Ŷ���
			
			// parser the SmsMessage
			sb.append(messages[i].getDisplayMessageBody());
			currentNumber = messages[i].getDisplayOriginatingAddress();
		}
		
		if(sb != null && !"".equals(sb)) {
			Log.i("Body", "I Receiver SMS Wars: " + sb.toString());
		}
		return sb.toString();
	}

}
