package com.jietong.rfid.ui.other_operation;

import com.jietong.rfid.ui.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class UsbDeliveryOutletActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_other_set_user_delivery_outlet);
		inital();
	}
	
	private void inital() {
		// ����activityʱ���Զ����������
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
	}

	public void shake_activity_back(View v) { // ������ ���ذ�ť
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
