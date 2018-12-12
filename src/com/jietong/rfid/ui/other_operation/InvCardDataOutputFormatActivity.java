package com.jietong.rfid.ui.other_operation;

import java.util.Map;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Toasts;

@SuppressWarnings("unused")
public class InvCardDataOutputFormatActivity  extends Activity implements  OnClickListener{
	
	private Button btnRead;
	private Button btnSet;
	
	private CheckBox cbAntenna;
	private CheckBox cbRssi;
	private CheckBox cbDeviceNo;
	private CheckBox cbAccessDoorDirection;
	
	private ReaderService readerService = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_inv_card_data_output_format);
		inital();
	}

	private void inital() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		btnRead = (Button)findViewById(R.id.btn_inv_card_data_output_format_read);
		btnSet = (Button)findViewById(R.id.btn_inv_card_data_output_format_set);
		
		cbAntenna = (CheckBox)findViewById(R.id.cb_inv_card_data_output_format_ant);
		cbRssi = (CheckBox)findViewById(R.id.cb_inv_card_data_output_format_rssi);
		cbDeviceNo = (CheckBox)findViewById(R.id.cb_inv_card_data_output_format_deviceNo);
		cbAccessDoorDirection = (CheckBox)findViewById(R.id.cb_inv_card_data_output_format_access_door_direction);
		
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
		case R.id.btn_inv_card_data_output_format_read:
			invCardDataOutputFormatRead();
			break;
		case R.id.btn_inv_card_data_output_format_set:
			invCardDataOutputFormatSet();
			break;
		}
	}

	private void invCardDataOutputFormatSet() {
		byte antenna = (byte) (cbAntenna.isChecked() == true ? 1 : 0);
		byte rssi = (byte) (cbRssi.isChecked() == true ? 1 : 0);
		byte deviceNo = (byte) (cbDeviceNo.isChecked() == true ? 1 : 0);
		byte accessDoorDirection = (byte) (cbAccessDoorDirection.isChecked() == true ? 1 : 0);
		
		boolean result = readerService.setInvOutPutData(ReaderUtil.readers, antenna, rssi, deviceNo, accessDoorDirection);
		if(result){
			Toasts.makeTextShort(this, R.string.msg_inv_card_data_output_format_set_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_inv_card_data_output_format_set_failed);
		}
	}

	private void invCardDataOutputFormatRead() {
		Map<String, Boolean> patternSet = readerService.getInvOutPutData(ReaderUtil.readers);
		if(null != patternSet){
			cbAntenna.setChecked(patternSet.get("antenna"));
			cbRssi.setChecked(patternSet.get("rssi"));
			cbDeviceNo.setChecked(patternSet.get("deviceNo"));
			cbAccessDoorDirection.setChecked(patternSet.get("accessDoorDirection"));
			Toasts.makeTextShort(this, R.string.msg_inv_card_data_output_format_read_succeed);
		}else{
			Toasts.makeTextShort(this, R.string.msg_inv_card_data_output_format_read_failed);
		}
	}
}
