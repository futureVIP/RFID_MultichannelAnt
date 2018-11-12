package com.jietong.rfid.ui.params_set;

import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.jietong.rfid.uhf.entity.AntStruct;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Multichannel32_16AntCb;

public class Antenna32ChannelActivity extends Activity implements OnCheckedChangeListener, OnClickListener {
	CheckBox cbAntennaGroup[];
	CheckBox cbAntenna[];
	List<String> checkedStr;
	// 操作取消一个时，全选取消，这个变量是是否是用户点击
	boolean checkFoUser = true;

	Spinner spinnerWorkTime;
	Spinner spinnerPower;
	
	Button btnReaderAntenner;
	Button btnSetAntenner;
	
	ReaderService readerService = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_params_set_antenna32_channel);
		intial();
	}

	private void intial() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		cbAntennaGroup = new CheckBox[8];
		cbAntenna = new CheckBox[32];
		// 四组天线
		cbAntennaGroup[0] = (CheckBox) findViewById(R.id.cb_32antenna1_group);
		cbAntennaGroup[1] = (CheckBox) findViewById(R.id.cb_32antenna2_group);
		cbAntennaGroup[2] = (CheckBox) findViewById(R.id.cb_32antenna3_group);
		cbAntennaGroup[3] = (CheckBox) findViewById(R.id.cb_32antenna4_group);
		cbAntennaGroup[4] = (CheckBox) findViewById(R.id.cb_32antenna5_group);
		cbAntennaGroup[5] = (CheckBox) findViewById(R.id.cb_32antenna6_group);
		cbAntennaGroup[6] = (CheckBox) findViewById(R.id.cb_32antenna7_group);
		cbAntennaGroup[7] = (CheckBox) findViewById(R.id.cb_32antenna8_group);
		// 第1组天线
		cbAntenna[0] = (CheckBox) findViewById(R.id.cb_32antenna1);
		cbAntenna[1] = (CheckBox) findViewById(R.id.cb_32antenna2);
		cbAntenna[2] = (CheckBox) findViewById(R.id.cb_32antenna3);
		cbAntenna[3] = (CheckBox) findViewById(R.id.cb_32antenna4);
		// 第2组天线
		cbAntenna[4] = (CheckBox) findViewById(R.id.cb_32antenna5);
		cbAntenna[5] = (CheckBox) findViewById(R.id.cb_32antenna6);
		cbAntenna[6] = (CheckBox) findViewById(R.id.cb_32antenna7);
		cbAntenna[7] = (CheckBox) findViewById(R.id.cb_32antenna8);
		// 第3组天线
		cbAntenna[8] = (CheckBox) findViewById(R.id.cb_32antenna9);
		cbAntenna[9] = (CheckBox) findViewById(R.id.cb_32antenna10);
		cbAntenna[10] = (CheckBox) findViewById(R.id.cb_32antenna11);
		cbAntenna[11] = (CheckBox) findViewById(R.id.cb_32antenna12);
		// 第4组天线
		cbAntenna[12] = (CheckBox) findViewById(R.id.cb_32antenna13);
		cbAntenna[13] = (CheckBox) findViewById(R.id.cb_32antenna14);
		cbAntenna[14] = (CheckBox) findViewById(R.id.cb_32antenna15);
		cbAntenna[15] = (CheckBox) findViewById(R.id.cb_32antenna16);
		// 第5组天线
		cbAntenna[16] = (CheckBox) findViewById(R.id.cb_32antenna17);
		cbAntenna[17] = (CheckBox) findViewById(R.id.cb_32antenna18);
		cbAntenna[18] = (CheckBox) findViewById(R.id.cb_32antenna19);
		cbAntenna[19] = (CheckBox) findViewById(R.id.cb_32antenna20);
		// 第6组天线
		cbAntenna[20] = (CheckBox) findViewById(R.id.cb_32antenna21);
		cbAntenna[21] = (CheckBox) findViewById(R.id.cb_32antenna22);
		cbAntenna[22] = (CheckBox) findViewById(R.id.cb_32antenna23);
		cbAntenna[23] = (CheckBox) findViewById(R.id.cb_32antenna24);
		// 第7组天线
		cbAntenna[24] = (CheckBox) findViewById(R.id.cb_32antenna25);
		cbAntenna[25] = (CheckBox) findViewById(R.id.cb_32antenna26);
		cbAntenna[26] = (CheckBox) findViewById(R.id.cb_32antenna27);
		cbAntenna[27] = (CheckBox) findViewById(R.id.cb_32antenna28);
		// 第8组天线
		cbAntenna[28] = (CheckBox) findViewById(R.id.cb_32antenna29);
		cbAntenna[29] = (CheckBox) findViewById(R.id.cb_32antenna30);
		cbAntenna[30] = (CheckBox) findViewById(R.id.cb_32antenna31);
		cbAntenna[31] = (CheckBox) findViewById(R.id.cb_32antenna32);

		for (int i = 0; i < cbAntennaGroup.length; i++) {
			cbAntennaGroup[i].setOnCheckedChangeListener(this);
		}
		for (int i = 0; i < cbAntenna.length; i++) {
			cbAntenna[i].setOnCheckedChangeListener(this);
		}
		checkedStr = new LinkedList<>();
		
		btnReaderAntenner = (Button)findViewById(R.id.btn_32read_antenna);
		btnSetAntenner = (Button)findViewById(R.id.btn_32set_antenna);
		
		spinnerWorkTime = (Spinner) findViewById(R.id.spinner_32work_time);
		spinnerPower = (Spinner) findViewById(R.id.spinner_32power);
		
		btnReaderAntenner.setOnClickListener(this);
		btnSetAntenner.setOnClickListener(this);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		boolean flag = true;
		String str = buttonView.getText().toString();
		switch (buttonView.getId()) {
		case R.id.cb_32antenna1_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 0, 4, isChecked);
			break;
		case R.id.cb_32antenna2_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 4, 8, isChecked);
			break;
		case R.id.cb_32antenna3_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 8, 12, isChecked);
			break;
		case R.id.cb_32antenna4_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 12, 16, isChecked);
			break;
		case R.id.cb_32antenna5_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 16, 20, isChecked);
			break;
		case R.id.cb_32antenna6_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 20, 24, isChecked);
			break;
		case R.id.cb_32antenna7_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 24, 28, isChecked);
			break;
		case R.id.cb_32antenna8_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 28, 32, isChecked);
			break;
		case R.id.cb_32antenna1:
		case R.id.cb_32antenna2:
		case R.id.cb_32antenna3:
		case R.id.cb_32antenna4:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,
					cbAntennaGroup, cbAntenna, 0, 4, 0, this);
			break;
		case R.id.cb_32antenna5:
		case R.id.cb_32antenna6:
		case R.id.cb_32antenna7:
		case R.id.cb_32antenna8:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,
					cbAntennaGroup, cbAntenna, 4, 8, 1, this);
			break;
		case R.id.cb_32antenna9:
		case R.id.cb_32antenna10:
		case R.id.cb_32antenna11:
		case R.id.cb_32antenna12:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,cbAntennaGroup, cbAntenna, 8, 12, 2, this);
			break;
		case R.id.cb_32antenna13:
		case R.id.cb_32antenna14:
		case R.id.cb_32antenna15:
		case R.id.cb_32antenna16:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,cbAntennaGroup, cbAntenna, 12, 16, 3, this);
			break;
		case R.id.cb_32antenna17:
		case R.id.cb_32antenna18:
		case R.id.cb_32antenna19:
		case R.id.cb_32antenna20:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,
					cbAntennaGroup, cbAntenna, 16, 20, 4, this);
			break;
		case R.id.cb_32antenna21:
		case R.id.cb_32antenna22:
		case R.id.cb_32antenna23:
		case R.id.cb_32antenna24:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,
					cbAntennaGroup, cbAntenna, 20, 24, 5, this);
			break;
		case R.id.cb_32antenna25:
		case R.id.cb_32antenna26:
		case R.id.cb_32antenna27:
		case R.id.cb_32antenna28:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,
					cbAntennaGroup, cbAntenna, 24, 28, 6, this);
			break;
		case R.id.cb_32antenna29:
		case R.id.cb_32antenna30:
		case R.id.cb_32antenna31:
		case R.id.cb_32antenna32:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,cbAntennaGroup, cbAntenna, 28, 32, 7, this);
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_32read_antenna:
			readAntenna();
			break;
		case R.id.btn_32set_antenna:
			setAntenna();
			break;
		}
	}

	@SuppressLint("ShowToast")
	private void setAntenna() {
		AntStruct ant = new AntStruct(ReaderUtil.readers.getChannel());
		for (int i = 0; i < cbAntenna.length; i++) {
			ant.enable[i] = (byte) (cbAntenna[i].isChecked() ? 1 : 0);
		}
		ant.dwellTime[0] = Integer.parseInt(spinnerWorkTime.getSelectedItem().toString());
		ant.power[0] = Integer.parseInt(spinnerPower.getSelectedItem().toString());
		
		boolean result = readerService.setAnt(ReaderUtil.readers,ant);
		if(result){
			Toast.makeText(getApplicationContext(), "设置天线成功!",Toast.LENGTH_SHORT).show();
		}
	}

	private void readAntenna() {
		AntStruct  ant = readerService.getAnt(ReaderUtil.readers);
		if(ant != null){
			Toast.makeText(getApplicationContext(), "读取天线成功!",Toast.LENGTH_SHORT).show();
		}
		for (int i = 0; i < cbAntenna.length; i++) {
			cbAntenna[i].setChecked(ant.enable[i] == 1);
		}
		spinnerWorkTime.setSelection(Multichannel32_16AntCb.positionWorkTime(ant.dwellTime[0]));
		spinnerPower.setSelection(Multichannel32_16AntCb.positionPower(ant.power[0]/10));
	}

	/************************************************************************************/
	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
