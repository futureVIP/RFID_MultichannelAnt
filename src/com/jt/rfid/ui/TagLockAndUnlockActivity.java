package com.jt.rfid.ui;

import android.app.Activity;
import android.os.Bundle;

public class TagLockAndUnlockActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_tag_lock_and_unlock);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
