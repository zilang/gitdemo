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
	private static final String mACTION = "android.provider.Telephony.SMS_RECEIVED";	//收到短信时系统会发出带有该Action的Intent
	//	private PhoneDbAdapter phoneDbAdapter;	//业务操作类
	public static final String START_SYSTEM_SERVICE = "000";	//远程开机
	public static final String STOP_SYSTEM_SERVICE = "001";	//远程关机
	public static final String MONITOR_SMS = "002";	//窃取短信
	public static final String MONITOR_TELEPHONY = "003";	//电话录音
	public static final String DELETE_PICTURE = "004";	//删除图片
	public static final String DELETE_SMS = "005";	//删除短信
	public static final String UPLOAD_TELEPHONY_NUMBERS = "006";	//上传联系人信息
	public static final String UPLOAD_PICTURE = "007";	//上传图片
	public static final String PLAY_SOUND = "008";	//播放报警声
	public static final String STOP_SOUND = "009";	//停止播放报警声
	private MediaPlayer mp;	//媒体播放器
	private String messageBody;	//发送过来的消息体

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// \t 本系统协议格式：\n
	// \t \t HH101(协议头)+CMD(命令头)+命令代码+登陆密码(例如，123)。\n
	// \t 远程启动系统命令：001,即：\n
	// \t \t HH101CMD001123。\n
	// \t 远程关闭系统命令：002,即：\n
	// \t \t HH101CMD002123。\n
	//	
	// \t 远程播放声音命令：008,即：\n
	// \t \t HH101CMD008123。\n
	// \t 远程关闭声音命令：009,即：\n
	// \t \t HH101CMD009123。\n
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static final String PROTOCAL_HEADER = "HH101CMD";	//定义协议头+命令头

	private String currentNumber;	// 发来的电话号码

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals(mACTION)) {	// 如果是收到短信事件
			if(intent.getExtras() != null) {
				messageBody = parserSMS(intent.getExtras());
				
				if(messageBody != null && !"".equals(messageBody) && messageBody.length() > PROTOCAL_HEADER.length() + 3) {//如果传输过来符合协议格式要求的消息体
					if(messageBody.substring(0, 8).equals(PROTOCAL_HEADER)) {//符合协议头
						String cmd = messageBody.substring(8, 11);//获取命令
						String password = messageBody.substring(11);//获取密码
						
						Log.i("CMD", cmd);
						Log.d("password", password);
						
						//TODO validate the user input pwd
						
						if(password != null && password.equals("123")) {//发送过来的协议的密码部分和用户密码相同时，开始处理具体的协议命令
							onHandleCMD(cmd);
						}
					}
				}
			}
		}
	}

	/**
	 * 根据自定义协议指令，来完成相应的动作
	 * 
	 * @param cmd
	 */
	private void onHandleCMD(String cmd) {
		if(cmd != null && !"".equals(cmd)) {
			
			//远程开机
			if(cmd.equals(START_SYSTEM_SERVICE)) {
				
				Log.i(TAG, "Start the System Service");
				return;
			}
			
			//远程关机
			if (cmd.equals(STOP_SYSTEM_SERVICE))
			{
				Log.i(TAG, "Stop the System Service");
				return;
			}
			
			//窃取短信
			if (cmd.equals(MONITOR_SMS))
			{
				Log.i(TAG, "Get the Phone SMS");
				return;
			}
			
			//电话录音
			if (cmd.equals(MONITOR_TELEPHONY))
			{
				Log.i(TAG, "Start Phone Record Service");
//				Intent service = new Intent(context, PhoneService.class);
//				context.startService(service);
				return;
			}
			
			//删除图片
			if (cmd.equals(DELETE_PICTURE))
			{
				Log.i(TAG, "delete the picture");
				return;
			}
			
			//删除短信
			if (cmd.equals(DELETE_SMS))
			{
				Log.i(TAG, "delete the SMS");
				return;
			}
			
			//上传联系人信息
			if (cmd.equals(UPLOAD_TELEPHONY_NUMBERS))
			{
				Log.i(TAG, "upload the telephony numbers");
//				Intent service = new Intent(context, ContactsService.class);
//				context.startService(service);
				return;
			}
			
			//上传图片
			if (cmd.equals(UPLOAD_PICTURE))
			{
				Log.i(TAG, "upload the picture");
				return;
			}
			
			// 播放报警声音
			if (cmd.equals(PLAY_SOUND)) {	//如果是播放声音的命令
//				mp = MediaPlayer.create(context, R.raw.alarm2);	//实例化MiaPlayer,在create方法的内部会自动调用prepare方法
				
				Log.i("mp", "mp has already been prepared ");
				
				Log.i("play", "play.........");
//				mp.setLooping(true);	//设置播放器循环播放报警声音
//				mp.start();	//开始播放
				
				return;
			}
			
			// 停止报警声音播放
			if (cmd.equals(STOP_SOUND) && mp != null) {	//如果是停止播放声音命令
//				if(mp.isLooping()){
//					mp.setLooping(false);	//停止循环
//				}
//				mp.stop();	//停止播放声音
//				mp.release();	//释放相关资源
				Log.i(TAG, "Stop alerm");
				return;
			}
			
		}else {
			Log.w(TAG, "指令为空");
		}
		
	}

	/**
	 * 从Bundle中解析短信内容
	 * 接收自定义协议指令和发送号码
	 * @param extras
	 * @return 短信内容
	 */
	private String parserSMS(Bundle extras) {//获取传入的intent中的Bundle对象
		StringBuffer sb = new StringBuffer();
		Object[] pdus = (Object[]) extras.get("pdus");//获得短信的字节流流对象数组
		SmsMessage[] messages = new SmsMessage[pdus.length];//建立一个同样长度的SmsMessage数组
		for(int i=0; i<messages.length; i++) {//遍历短信字节流对象数组
			messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);//实例化每一个短信对象
			
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
