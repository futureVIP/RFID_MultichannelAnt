package com.jietong.rfid.ui.params_set;

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

public class WorkPatternActivity extends Activity implements OnClickListener,OnItemSelectedListener {
	private Spinner spinnerWorkPattern;
	private Button btnRead;
	private Button btnSet;
	private int workPattern;
	ReaderService readerService = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_params_set_work_pattern);
		inital();
	}

	private void inital() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		spinnerWorkPattern = (Spinner) findViewById(R.id.spinner_work_pattern);
		btnRead = (Button) findViewById(R.id.btn_work_pattern_read);
		btnSet = (Button) findViewById(R.id.btn_work_pattern_set);

		spinnerWorkPattern.setOnItemSelectedListener(this);
		btnRead.setOnClickListener(this);
		btnSet.setOnClickListener(this);
		
		btnRead.performClick();
	}

	/*****************************************************************************/
	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		switch (parent.getId()) {
		case R.id.spinner_work_pattern:
			workPattern = position;
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_work_pattern_read:
			workPatternRead();
			break;
		case R.id.btn_work_pattern_set:
			workPatternSet();
			break;
		}
	}

	private void workPatternSet() {
		boolean result = readerService.setWorkMode(ReaderUtil.readers, workPattern);
		if(result){
			Toasts.makeTextShort(this, R.string.msg_work_mode_set_succeed);
		}else{
			Toasts.makeTextShort(this,R.string.msg_work_mode_set_failed);
		}
	}

	private void workPatternRead() {
		int result = readerService.getWorkMode(ReaderUtil.readers);
		if(result > -1 && result < 3){
			spinnerWorkPattern.setSelection(result);
			Toasts.makeTextShort(this,R.string.msg_work_mode_read_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_work_mode_read_failed);
		}
	}
}