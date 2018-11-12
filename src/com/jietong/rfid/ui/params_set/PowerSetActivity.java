package com.jietong.rfid.ui.params_set;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.jietong.rfid.ui.R;

public class PowerSetActivity extends Activity implements OnClickListener{
	TextView tvPowerSet;
	Button btnRead;
	Button btnSet;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_params_set_power);
		inital();
	}
	
	private void inital() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		tvPowerSet = (TextView)findViewById(R.id.tv_power_set);
		btnRead = (Button)findViewById(R.id.btn_power_set_read);
		btnSet = (Button)findViewById(R.id.btn_power_set_set);
		
		btnRead.setOnClickListener(this);
		btnSet.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_power_set_read:
			powerRead();
			break;
		case R.id.btn_power_set_set:
			powerSet();
			break;
		}
	}
	
	private void powerSet() {
		
	}

	private void powerRead() {
		//ReaderUtil.readerService.get
	}

	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
