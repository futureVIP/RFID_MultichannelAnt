package com.jietong.rfid.ui.other_operation;

import java.util.Map;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Toasts;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class SpecialReadTagPatternActivity extends Activity implements OnClickListener {

	private Button btnRead;
	private Button btnSet;
	private Spinner spinnerSession;
	private Spinner spinnerQvalue;
	private Spinner spinnerTagfocus;
	private Spinner spinnerABvalue;
	
	private ReaderService readerService = new ReaderServiceImpl();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_special_read_tag_pattern);
		inital();
	}

	private void inital() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		btnRead = (Button)findViewById(R.id.btn_special_read_pattern_read);
		btnSet = (Button)findViewById(R.id.btn_special_read_pattern_set);
		
		spinnerSession = (Spinner)findViewById(R.id.spinner_special_session);
		spinnerQvalue = (Spinner)findViewById(R.id.spinner_special_Q_value);
		spinnerTagfocus = (Spinner)findViewById(R.id.spinner_special_tag_focus);
		spinnerABvalue = (Spinner)findViewById(R.id.spinner_special_ab_value);
		
		btnRead.setOnClickListener(this);
		btnSet.setOnClickListener(this);
		
		btnRead.performClick();
	}

	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_special_read_pattern_read:
			patternRead();
			break;
		case R.id.btn_special_read_pattern_set:
			patternSet();
			break;
		}
	}

	private void patternSet() {
		byte session = (byte) Integer.parseInt(spinnerSession.getSelectedItem().toString());
		byte qValue = (byte) Integer.parseInt(spinnerQvalue.getSelectedItem().toString());
		byte tagFocus = (byte) Integer.parseInt(spinnerTagfocus.getSelectedItem().toString());
		byte abValue = (byte) Integer.parseInt(spinnerABvalue.getSelectedItem().toString());
		boolean result = readerService.setInvPatternConfig(ReaderUtil.readers, session, qValue, tagFocus, abValue);
		if(result){
			Toasts.makeTextShort(this,R.string.msg_read_tag_pattern_params_set_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_read_tag_pattern_params_set_failed);
		}
	}

	private void patternRead() {
		Map<String, Integer> patternSet = readerService.getInvPatternConfig(ReaderUtil.readers);
		if(null != patternSet){
			spinnerSession.setSelection(patternSet.get("session"));
			int position = patternSet.get("qValue");
			spinnerQvalue.setSelection(position-1);
			spinnerTagfocus.setSelection(patternSet.get("tagFocus"));
			spinnerABvalue.setSelection(patternSet.get("abValue"));
			Toasts.makeTextShort(this,R.string.msg_read_tag_pattern_params_read_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_read_tag_pattern_params_read_failed);
		}
	}
}
