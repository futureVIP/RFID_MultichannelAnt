package com.jt.rfid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class DeviceNumberActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_device_number);
	}
	
	public void shake_activity_back(View v) { // ������ ���ذ�ť
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
