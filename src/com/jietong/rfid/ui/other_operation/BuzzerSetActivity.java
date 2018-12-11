package com.jietong.rfid.ui.other_operation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Toasts;

public class BuzzerSetActivity extends Activity implements OnClickListener{
	private RadioButton rbBuzzerOn;
	private RadioButton rbBuzzerOff;
	private RadioGroup rbGroupBuzzer;
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
		rbGroupBuzzer = (RadioGroup)findViewById(R.id.rb_buzzer_group);
		rbBuzzerOn = (RadioButton)findViewById(R.id.rb_buzzer_on);
		rbBuzzerOff = (RadioButton)findViewById(R.id.rb_buzzer_off);
		btnRead =(Button)findViewById(R.id.btn_buzzer_set);
		btnSet = (Button)findViewById(R.id.btn_buzzer_read);
		btnRead.setOnClickListener(this);
		btnSet.setOnClickListener(this);
		rbBuzzerOn.setOnClickListener(this);
		rbBuzzerOff.setOnClickListener(this);
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
		if(rbBuzzerOn.isChecked()){
			state = 1;
		}else if(rbBuzzerOff.isChecked()){
			state = 0;
		}
		boolean result = readerService.setBuzzer(ReaderUtil.readers, (byte)state);
		if(result){
			Toasts.makeTextShort(this, R.string.msg_other_set_buzzer_set_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_other_set_buzzer_set_failed);
		}
	}

	private void buzzerRead() {
		int result = readerService.getBuzzer(ReaderUtil.readers);
		if(result > -1){
			if(result == 0){
				rbBuzzerOff.setChecked(true);
			}else if(result == 1){
				rbBuzzerOn.setChecked(true);
			}
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
