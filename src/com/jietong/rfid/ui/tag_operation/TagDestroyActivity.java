package com.jietong.rfid.ui.tag_operation;

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

public class TagDestroyActivity extends Activity implements OnClickListener {
	EditText etKillPwd;
	EditText etAccessPwd;
	Button btnTagDestroy;
	
	ReaderService readerService = new ReaderServiceImpl();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_tag_opeation_tag_destroy);
		inital();
	}

	private void inital() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		etKillPwd = (EditText)findViewById(R.id.et_tag_destroy_pwd);
		etAccessPwd = (EditText)findViewById(R.id.et_tag_destroy_visit_pwd);
		btnTagDestroy = (Button)findViewById(R.id.btn_tag_destroy_set);
		
		btnTagDestroy.setOnClickListener(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	public void shake_activity_back(View v){
		this.finish();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_tag_destroy_set:
			tagDestroy();
			break;
		}
	}

	private void tagDestroy() {
		byte [] accessPwd = new byte[4];
		byte [] killPwd = new byte[4];
		
		String visitPwd = etKillPwd.getText().toString().replace(" ", "");
		String pwd = etKillPwd.getText().toString().replace(" ", "");
		
		if (pwd.length() != 8 || visitPwd.length() != 8 ) {
			Toasts.makeTextShort(this, "密码必须为8位");
			return;
		}
		if (!Regex.isHexCharacter(pwd) || !Regex.isHexCharacter(visitPwd)) {
			Toasts.makeTextShort(this, "密码含有0－9，A－F之外的非法字符");
			return;
		}
		String defaultPwd = "00000000";
		if(visitPwd.equals(defaultPwd) || pwd.equals(defaultPwd)){
			Toasts.makeTextShort(this, "默认密码不能执行操作,请修改后再操作!");
			return;
		}
		for (int i = 0; i < 4; ++i) {
			String str = visitPwd.substring(i * 2, (2 + i * 2));
			// 把字符串的子串转为16进制的8位无符号整数
			accessPwd[i] = Byte.parseByte(str, 16);
		}
		
		for (int i = 0; i < 4; ++i) {
			String str = pwd.substring(i * 2, (2 + i * 2));
			// 把字符串的子串转为16进制的8位无符号整数
			killPwd[i] = Byte.parseByte(str, 16);
		}
		boolean result = readerService.killTag(ReaderUtil.readers,accessPwd,killPwd);
		if(result){
			Toasts.makeTextShort(this, "销毁成功!");
		}else{
			Toasts.makeTextShort(this, "销毁失败!");
		}
	}
}
