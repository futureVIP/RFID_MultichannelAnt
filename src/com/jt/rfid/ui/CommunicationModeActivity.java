package com.jt.rfid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CommunicationModeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_communication_mode);
	}
	
	public void shake_activity_back(View v) { // ������ ���ذ�ť
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
