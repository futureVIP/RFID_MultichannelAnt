package com.jietong.rfid.ui.tag_operation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Regex;
import com.jietong.rfid.util.Toasts;

public class TagLockUnlockActivity extends Activity implements OnClickListener, OnItemSelectedListener {
	Spinner spinnerArea;
	Spinner spinnerType;
	EditText etVisitPwd;
	Button btnExcute;
	int lockBank;
	int lockType;
	String visitPwd;
	
	ReaderService readerService = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_tag_operation_tag_lock_and_unlock);
		inital();
	}

	private void inital() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		spinnerArea = (Spinner) findViewById(R.id.spinner_lock_unlock_area);
		spinnerType = (Spinner) findViewById(R.id.spinner_lock_unlock_type);
		btnExcute = (Button) findViewById(R.id.btn_lock_unlock_set);
		etVisitPwd = (EditText) findViewById(R.id.et_lock_unlock_pwd);
		
		spinnerArea.setOnItemSelectedListener(this);
		spinnerType.setOnItemSelectedListener(this);
		btnExcute.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_lock_unlock_set:
			tagLockUnlock();
			break;
		}
	}

	private void tagLockUnlock() {
		visitPwd = etVisitPwd.getText().toString().replace(" ", "");
		if (visitPwd.length() != 8) {
			Toasts.makeTextShort(this, "密码必须为8位");
			return;
		}
		if (!Regex.isHexCharacter(visitPwd)) {
			Toasts.makeTextShort(this, "密码含有0－9，A－F之外的非法字符");
			return;
		}
		byte[] pwd = new byte[4];
		for (int i = 0; i < 4; ++i) {
			String str = visitPwd.substring(i * 2, (2 + i * 2));
			// 把字符串的子串转为16进制的8位无符号整数
			pwd[i] = Byte.parseByte(str, 16);
		}
		boolean result = readerService.lockTag(ReaderUtil.readers,(byte) lockType,(byte) lockBank, pwd);
		if(result){
			Toasts.makeTextShort(this, "操作成功!");
		}else{
			Toasts.makeTextShort(this, "操作失败!");
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void shake_activity_back(View v){
		this.finish();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		switch(parent.getId()){
		case R.id.spinner_lock_unlock_area:
			lockBank = position;
			break;
		case R.id.spinner_lock_unlock_type:
			lockType = position;
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}
}
