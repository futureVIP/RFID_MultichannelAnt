package com.jt.rfid.ui;

import android.app.Activity;
import android.os.Bundle;

public class FastWriteTagActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_fast_wirte_tag);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
