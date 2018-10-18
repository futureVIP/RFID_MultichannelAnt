package com.jt.rfid.ui;

import android.app.Activity;
import android.os.Bundle;

public class TagDestroyActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_tag_destroy);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
