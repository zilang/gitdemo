package com.jany.phoneguard.app.receiver;

import com.jany.phoneguard.app.engine.SystemService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * ���������¼�������
 * @author miki
 *
 */
public class BootCompleteReceiver extends BroadcastReceiver {
	private static final String TAG = "BootCompleteReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, SystemService.class));
		Log.i(TAG, "Boot Complete --> start SystemService");
	}

}
