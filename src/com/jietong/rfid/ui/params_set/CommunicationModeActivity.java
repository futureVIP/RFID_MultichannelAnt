package com.jietong.rfid.ui.params_set;

import java.nio.ByteBuffer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;

import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Toasts;

public class CommunicationModeActivity extends Activity implements OnClickListener,OnItemSelectedListener{
	private Spinner spinnerModeOutput;
	private int modeOutput;
	private Button btnReadOutput;
	private Button btnSetOutput;
	
	ReaderService readerService = new ReaderServiceImpl();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_params_set_communication_mode_r2k);
		inital();
	}
	
	private void inital() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		spinnerModeOutput = (Spinner)findViewById(R.id.spinner_communication_output_mode);
		btnReadOutput = (Button) findViewById(R.id.btn_communication_mode_output_read);
		btnSetOutput = (Button) findViewById(R.id.btn_communication_mode_output_set);
		
		btnReadOutput.setOnClickListener(this);
		btnSetOutput.setOnClickListener(this);
		spinnerModeOutput.setOnItemSelectedListener(this);
	}
	
	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		switch(parent.getId()){
		case R.id.spinner_communication_output_mode:
			modeOutput = position;
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_communication_mode_output_read:
			outputRead();
			break;
		case R.id.btn_communication_mode_output_set:
			outputSet();
			break;
		}
	}

	private void outputSet() {
		if(null == ReaderUtil.readers){
			return;
		}
		boolean result = readerService.setOutputMode(ReaderUtil.readers, (byte)modeOutput);
		if(result){
			Toasts.makeTextShort(this,R.string.msg_output_port_setting_set_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_output_port_setting_set_failed);
		}
	}

	private void outputRead() {
		if(null == ReaderUtil.readers){
			return;
		}
		int result = readerService.getOutputMode(ReaderUtil.readers);
		if(result > -1){
			Toasts.makeTextShort(this, R.string.msg_output_port_setting_read_succeed);
			spinnerModeOutput.setSelection(result);
		}else{
			Toasts.makeTextShort(this, R.string.msg_output_port_setting_read_failed);
		}
	}
}
