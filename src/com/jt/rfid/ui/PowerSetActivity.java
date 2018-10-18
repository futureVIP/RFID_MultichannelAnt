package com.jt.rfid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class PowerSetActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_power);
	}
	
	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
