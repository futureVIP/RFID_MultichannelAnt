package com.jietong.rfid.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class AppStart extends Activity {
	
	private String TAG = this.getClass().getName();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appstart);
		inital();
	}
	
	private void inital() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(AppStart.this, ConnectActivity.class);
				startActivity(intent);
				AppStart.this.finish();
			}
		}, 1000);
	}

	@Override
	protected void onStart() {
		super.onStart();
		String msg = "----------onStart----------";
		Log.d(TAG, msg);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		String msg = "----------onRestart----------";
		Log.d(TAG, msg);
	}

	@Override
	protected void onResume() {
		super.onResume();
//		Resources resource = getApplicationContext().getResources();
//		Configuration configuration = resource.getConfiguration();
//		configuration.fontScale = 1.0f;// 设置字体的缩放比例
//		resource.updateConfiguration(configuration, resource.getDisplayMetrics());
		String msg = "----------onResume----------";
		Log.d(TAG, msg);
	}

	@Override
	protected void onPause() {
		super.onPause();
		String msg = "----------onPause----------";
		Log.d(TAG, msg);
	}

	@Override
	protected void onStop() {
		super.onStop();
		String msg = "----------onStop----------";
		Log.d(TAG, msg);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		String msg = "----------onDestroy----------";
		Log.d(TAG, msg);
	}
}
