package com.jietong.rfid.ui.params_set;

import java.util.Map;

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
import com.jietong.rfid.util.DataConvert;
import com.jietong.rfid.util.Toasts;

public class Antenna4ChannelActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {
	private CheckBox cbAntenna[];
	private Spinner spinnerWorkTime[];
	private Spinner spinnerPower[];
	private Button btnRead;
	private Button btnSet;
	private Button btnCheck;
	private ReaderService readerService = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_params_set_antenna4_channel);
		inital();
	}

	private void inital() {
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
			spinnerWorkTime[i].setSelection(9);
			spinnerPower[i].setSelection(10);
		}
		btnRead = (Button) findViewById(R.id.btn_4antenna_read);
		btnSet = (Button) findViewById(R.id.btn_4antenna_set);
		btnCheck = (Button) findViewById(R.id.btn_4check_antenna);
		
		btnRead.setOnClickListener(this);
		btnSet.setOnClickListener(this);
		btnCheck.setOnClickListener(this);
		
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
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		int temp = Integer.parseInt(parent.getSelectedItem().toString());
		switch (parent.getId()) {
		case R.id.spinner_4antenna_work_time1:
			break;
		case R.id.spinner_4antenna_work_time2:
			break;
		case R.id.spinner_4antenna_work_time3:
			break;
		case R.id.spinner_4antenna_work_time4:
			break;
		case R.id.spinner_4antenna_power1:
			break;
		case R.id.spinner_4antenna_power2:
			break;
		case R.id.spinner_4antenna_power3:
			break;
		case R.id.spinner_4antenna_power4:
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
		case R.id.btn_4check_antenna:
			antennaCheck();
			break;
		}
	}

	private void antennaCheck() {
		Toasts.makeTextShort(this, R.string.msg_params_set_detection_antenna_please_wait);
		new Thread(new Runnable() {
			public void run() {
				final Map<String, Byte> antennaState = readerService.getAntState(ReaderUtil.readers);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if (null == antennaState) {
							Toasts.makeTextShort(getApplicationContext(),R.string.msg_params_set_detection_antenna_fails);
							return;
						}
						int length = DataConvert.byteToInt(antennaState.get("Channel"));
						for (int i = 0; i < length; i++) {
							cbAntenna[i].setChecked(antennaState.get("Ant" + (i + 1)) == 1);
						}
						Toasts.makeTextShort(getApplicationContext(), R.string.msg_params_set_detection_antenna_successful);
					}
				});
			}
		}).start();
	}

	private void antennaSet() {
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
		boolean result = readerService.setAnt(ReaderUtil.readers, ant);
		if(result){
			Toasts.makeTextShort(this,R.string.msg_antenna_params_set_succeed);
		}else{
			Toasts.makeTextShort(this,R.string.msg_antenna_params_set_failed);
		}
	}

	private void antennaRead() {
		AntStruct ant = readerService.getAnt(ReaderUtil.readers);
		if(ant != null){
			Toasts.makeTextShort(this,R.string.msg_antenna_params_read_succeed);
		}else{
			Toasts.makeTextShort(this,R.string.msg_antenna_params_read_failed);
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
