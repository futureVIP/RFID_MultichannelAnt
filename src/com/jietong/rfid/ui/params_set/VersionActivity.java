package com.jietong.rfid.ui.params_set;

import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Toasts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class VersionActivity extends Activity implements OnClickListener {
	
	private Button btnRead;
	private TextView tvVersionInfo;
	private ReaderService readerService = new ReaderServiceImpl();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_params_set_get_version);
		inital();
	}
	
	private void inital() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		btnRead = (Button) findViewById(R.id.btn_params_set_version_btn);
		tvVersionInfo = (TextView) findViewById(R.id.tv_version_info);
		btnRead.setOnClickListener(this);
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
		case R.id.btn_params_set_version_btn:
			getVersion();
			break;
		default:
			break;
		}
	}

	private void getVersion() {
		String version = readerService.version(ReaderUtil.readers);
		if(null != version){
			tvVersionInfo.setText(version);
			Toasts.makeTextShort(this, R.string.msg_params_set_get_version_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_params_set_get_version_failed);
		}
	}
}
