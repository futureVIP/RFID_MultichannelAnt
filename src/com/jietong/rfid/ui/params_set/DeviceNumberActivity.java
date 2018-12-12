package com.jietong.rfid.ui.params_set;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Regex;
import com.jietong.rfid.util.Toasts;

public class DeviceNumberActivity extends Activity implements OnClickListener{
	private EditText etDeviceNo;
	private Button btnRead;
	private Button btnSet;
	
	ReaderService readerService = new ReaderServiceImpl();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_params_set_device_number);
		inital();
	}
	
	private void inital() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		etDeviceNo = (EditText)findViewById(R.id.et_device_no);
		btnRead = (Button)findViewById(R.id.btn_device_no_read);
		btnSet = (Button)findViewById(R.id.btn_device_no_set);
		
		btnRead.setOnClickListener(this);
		btnSet.setOnClickListener(this);
		
		btnRead.performClick();
	}

	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}
	


	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_device_no_read:
			deviceNoRead();
			break;
		case R.id.btn_device_no_set:
			deviceNoSet();
			break;
		}
	}

	private void deviceNoRead() {
		String result = readerService.getDeviceNo(ReaderUtil.readers);
		if(null != result){
			etDeviceNo.setText(result);
			Toasts.makeTextShort(this,R.string.msg_device_number_read_succeed);
		}else{
			Toasts.makeTextShort(this,R.string.msg_device_number_read_failed);
		}
	}

	private void deviceNoSet() {
		String device = etDeviceNo.getText().toString().replace(" ", "");
		if(!Regex.isDecNumber(device)){
			Toasts.makeTextShort(this,R.string.msg_device_number_value_scope);
			return;
		}
		if(device.length() > 5){
			Toasts.makeTextShort(this,R.string.msg_device_number_value_scope);
			return;
		}
		int dev = Integer.parseInt(device);
		if(dev < 0 || dev > 65535){
			Toasts.makeTextShort(this,R.string.msg_device_number_value_scope);
			return;
		}
		boolean result = readerService.setDeviceNo(ReaderUtil.readers, dev);
		if(result){
			Toasts.makeTextShort(this,R.string.msg_device_number_set_succeed);
		}else{
			Toasts.makeTextShort(this,R.string.msg_device_number_set_failed);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
