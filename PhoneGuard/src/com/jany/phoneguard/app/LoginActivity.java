package com.jany.phoneguard.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * @author miki
 *
 */
public class LoginActivity extends Activity {
	private TextView tvLoginPrompt;
	private EditText etLoginPwd;
	private AlertDialog alertDialog;
	
	private int counter = 0;	//��¼�������������������ʼֵΪ0�����û����������������������ε�ʱ��ǿ���˳�����
	private static final int THREAD_TIME = 3;

	private static final String TAG = "LoginActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ȫ����ʾ
		//turning off the title at the top of the screen. 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//the status bar will be hidden when an app window with this flag set is on the top layer.
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		onHandleLogin();	// ��ʼ����½�Ի���
	}

	private void onHandleLogin() {
		LayoutInflater li = LayoutInflater.from(this);	//����Context��ȡLayoutInflater
		View view = li.inflate(R.layout.login_layout, null);
		
		tvLoginPrompt = (TextView) view.findViewById(R.id.tv_login_prompt);
		etLoginPwd = (EditText) view.findViewById(R.id.et_login_pwd);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(view);
		builder.setTitle(R.string.title);
		alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);	// ���ܵ������Dialog֮�������������Cancel the dialog
		
		alertDialog.setOnKeyListener(new OnKeyListener() {	// �����������¼�����ǰView�Ĳ���
			
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_BACK:
					if(alertDialog != null) {
						alertDialog.dismiss();	//�رնԻ���
						finish();	//�رյ�ǰ��Activity���˳�Ӧ�ó���
					}
					Log.d(TAG, "on back");
					break;

				default:
					break;
				}
				return true;
			}
		});
		
		alertDialog.show();
	}
	
	
	public void onLogin(View view) {
		switch (view.getId()) {
		case R.id.btn_login:
			String pwd = etLoginPwd.getText().toString();
			if(pwd != null && !"".equals(pwd)) {
				if(validatePWD(pwd)) {
					alertDialog.dismiss();	// �رնԻ���
					startActivity(new Intent(getApplicationContext(), MenuActivity.class));
					finish();
					Log.i(TAG, "The pwd is right");
				}else {
					counter ++;
					if(counter >= THREAD_TIME) {
						alertDialog.dismiss();
						finish();
					}else {
						tvLoginPrompt.setText(getApplicationContext().getResources().getString(R.string.passwordIncorrect)
								+ " " + (THREAD_TIME - counter) + 
								getApplicationContext().getResources().getString(R.string.timesRemained));
					}
				}
			}else {
				tvLoginPrompt.setText(getApplicationContext().getResources().getString(R.string.pwd_error));
			}
			tvLoginPrompt.setTextColor(Color.RED);
			break;

		case R.id.btn_cancel:
			alertDialog.dismiss();
			finish();
			break;
		default:
			break;
		}
	}

	private boolean validatePWD(String pwd) {
		if("aabbcc".equals(pwd)) {
			return true;
		}
		return false;
	}
	
}
