package com.jietong.rfid.ui.other_operation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Toasts;

public class FactoryDataResetActivity extends Activity implements OnClickListener {

	private Button btnSet;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_factory_data_reset);
		inital();
	}

	private void inital() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		btnSet = (Button)findViewById(R.id.btn_factory_data_reset_set);
		btnSet.setOnClickListener(this);
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
		case R.id.btn_factory_data_reset_set:
			factoryDataReset();
			break;
		}
	}

	private void factoryDataReset() {
		ReaderService readerService = new ReaderServiceImpl();
		boolean result = readerService.factoryDataReset(ReaderUtil.readers);
		if(result){
			Toasts.makeTextShort(this, R.string.msg_factory_data_reset_set_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_factory_data_reset_set_failed);
		}
	}
}
