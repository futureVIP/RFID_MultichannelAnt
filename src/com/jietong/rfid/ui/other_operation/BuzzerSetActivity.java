package com.jietong.rfid.ui.other_operation;

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

public class BuzzerSetActivity extends Activity implements OnClickListener,OnItemSelectedListener {
	private Spinner spinnerBuzzer;
	private Button btnRead;
	private Button btnSet;
	private int state;
	ReaderService readerService = new ReaderServiceImpl();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_other_operation_buzzer_set);
		inital();
	}
	
	private void inital() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		spinnerBuzzer = (Spinner)findViewById(R.id.spinner_buzzer_on_off);
		btnRead =(Button)findViewById(R.id.btn_buzzer_set);
		btnSet = (Button)findViewById(R.id.btn_buzzer_read);
		btnRead.setOnClickListener(this);
		btnSet.setOnClickListener(this);
		spinnerBuzzer.setOnItemSelectedListener(this);
	}



	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		switch(parent.getId()){
		case R.id.spinner_buzzer_on_off:
			state = parent.getSelectedItemPosition();
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_buzzer_set:
			buzzerRead();
			break;
		case R.id.btn_buzzer_read:
			buzzerSet();
			break;
		}
	}
	
	private void buzzerSet() {
		if(null == ReaderUtil.readers){
			return;
		}
		boolean result = readerService.setBuzzer(ReaderUtil.readers, (byte)state);
		if(result){
			Toasts.makeTextShort(this, R.string.msg_other_set_buzzer_set_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_other_set_buzzer_set_failed);
		}
	}

	private void buzzerRead() {
		if(null == ReaderUtil.readers){
			return;
		}
		int result = readerService.getBuzzer(ReaderUtil.readers);
		if(result > -1){
			spinnerBuzzer.setSelection(result);
			Toasts.makeTextShort(this, R.string.msg_other_set_buzzer_read_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_other_set_buzzer_read_failed);
		}
	}

	public void shake_activity_back(View v) {
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
