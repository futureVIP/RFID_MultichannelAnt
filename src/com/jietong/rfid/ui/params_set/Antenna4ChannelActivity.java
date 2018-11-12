package com.jietong.rfid.ui.params_set;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.jietong.rfid.uhf.entity.AntStruct;
import com.jietong.rfid.uhf.entity.OperationAntenna;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Toasts;

public class Antenna4ChannelActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {
	CheckBox cbAntenna[];
	Spinner spinnerWorkTime[];
	Spinner spinnerPower[];
	Button btnRead;
	Button btnSet;
	int workTime1;
	int workTime2;
	int workTime3;
	int workTime4;
	int power1;
	int power2;
	int power3;
	int power4;
	ReaderService service = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_params_set_antenna4_channel);
		inital();
	}

	private void inital() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		cbAntenna = new CheckBox[4];
		cbAntenna[0] = (CheckBox) findViewById(R.id.cb_4antenna1);
		cbAntenna[1] = (CheckBox) findViewById(R.id.cb_4antenna2);
		cbAntenna[2] = (CheckBox) findViewById(R.id.cb_4antenna3);
		cbAntenna[3] = (CheckBox) findViewById(R.id.cb_4antenna4);

		spinnerWorkTime = new Spinner[4];
		spinnerWorkTime[0] = (Spinner) findViewById(R.id.spinner_4antenna_work_time1);
		spinnerWorkTime[1] = (Spinner) findViewById(R.id.spinner_4antenna_work_time2);
		spinnerWorkTime[2] = (Spinner) findViewById(R.id.spinner_4antenna_work_time3);
		spinnerWorkTime[3] = (Spinner) findViewById(R.id.spinner_4antenna_work_time4);
		
		spinnerPower = new Spinner[4];
		spinnerPower[0] = (Spinner) findViewById(R.id.spinner_4antenna_power1);
		spinnerPower[1] = (Spinner) findViewById(R.id.spinner_4antenna_power2);
		spinnerPower[2] = (Spinner) findViewById(R.id.spinner_4antenna_power3);
		spinnerPower[3] = (Spinner) findViewById(R.id.spinner_4antenna_power4);

		for (int i = 0; i < spinnerWorkTime.length; i++) {
			spinnerWorkTime[i].setOnItemSelectedListener(this);
			spinnerPower[i].setOnItemSelectedListener(this);
		}
		btnRead = (Button) findViewById(R.id.btn_4antenna_read);
		btnSet = (Button) findViewById(R.id.btn_4antenna_set);

		btnRead.setOnClickListener(this);
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
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		int temp = Integer.parseInt(parent.getSelectedItem().toString());
		switch (parent.getId()) {
		case R.id.spinner_4antenna_work_time1:
			workTime1 = temp;
			break;
		case R.id.spinner_4antenna_work_time2:
			workTime2 = temp;
			break;
		case R.id.spinner_4antenna_work_time3:
			workTime3 = temp;
			break;
		case R.id.spinner_4antenna_work_time4:
			workTime4 = temp;
			break;
		case R.id.spinner_4antenna_power1:
			power1 = temp;
			break;
		case R.id.spinner_4antenna_power2:
			power2 = temp;
			break;
		case R.id.spinner_4antenna_power3:
			power3 = temp;
			break;
		case R.id.spinner_4antenna_power4:
			power4 = temp;
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_4antenna_read:
			antennaRead();
			break;
		case R.id.btn_4antenna_set:
			antennaSet();
			break;
		}
	}

	private void antennaSet() {
		if(null == ReaderUtil.readers){
			return;
		}
		AntStruct ant = new AntStruct(ReaderUtil.readers.getChannel());
		for (int i = 0; i < 4; i++) {
			ant.enable[i] = (byte) (cbAntenna[i].isChecked() == true ? 1 : 0);
		}
		
		for (int i = 0; i < 4; i++) {
			String workTime = spinnerWorkTime[i].getSelectedItem().toString();
			ant.dwellTime[i] = Integer.parseInt(workTime);
		}
		 
		for (int i = 0; i < 4; i++) {
			String power = spinnerPower[i].getSelectedItem().toString();
			ant.power[i] = Integer.parseInt(power);
		}
		boolean result = service.setAnt(ReaderUtil.readers, ant);
		if(result){
			Toasts.makeTextShort(this, "设置天线成功!");
		}else{
			Toasts.makeTextShort(this, "设置天线失败!");
		}
	}

	private void antennaRead() {
		AntStruct ant = service.getAnt(ReaderUtil.readers);
		if(ant != null){
			Toasts.makeTextShort(this, "获取天线成功!");
		}else{
			Toasts.makeTextShort(this, "获取天线失败!");
		}
		for (int i = 0; i < 4; i++) {
			cbAntenna[i].setChecked(ant.enable[i] == 1);
		}
		for (int i = 0; i < 4; i++) {
			int workTime = OperationAntenna.positionWorkTime(ant.dwellTime[i]);
			spinnerWorkTime[i].setSelection(workTime);
		}
		for (int i = 0; i < 4; i++) {
			int power = OperationAntenna.positionPower(ant.power[i]);
			spinnerPower[i].setSelection(power);
		}
	}
}
