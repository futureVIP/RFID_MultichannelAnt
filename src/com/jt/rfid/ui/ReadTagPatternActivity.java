package com.jt.rfid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ReadTagPatternActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_read_tag_pattern);
	}
	
	public void shake_activity_back(View v) { // ������ ���ذ�ť
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
