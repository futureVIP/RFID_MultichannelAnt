package com.jietong.rfid.ui.params_set;

import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.jietong.rfid.uhf.entity.AntStruct;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;

public class Antenna16ChannelActivity extends Activity  implements OnCheckedChangeListener, OnClickListener {
	CheckBox cbAntennaGroup[];
	CheckBox cbAntenna[];
	List<String> checkedStr;
	// ����ȡ��һ��ʱ��ȫѡȡ��������������Ƿ����û����
	boolean checkFoUser = true;
	Spinner spinnerWorkTime;
	Spinner spinnerPower;
	Button btnReaderAntenner;
	Button btnSetAntenner;
	ReaderService readerService = new ReaderServiceImpl();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.dialog_params_set_antenna16_channel);
		intial();
	}
	
	private void intial() {
		// ����activityʱ���Զ�����������
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		cbAntennaGroup = new CheckBox[4];
		cbAntenna = new CheckBox[16];
		// ��������
		cbAntennaGroup[0] = (CheckBox) findViewById(R.id.cb_16antenna1_group);
		cbAntennaGroup[1] = (CheckBox) findViewById(R.id.cb_16antenna2_group);
		cbAntennaGroup[2] = (CheckBox) findViewById(R.id.cb_16antenna3_group);
		cbAntennaGroup[3] = (CheckBox) findViewById(R.id.cb_16antenna4_group);

		// ��1������
		cbAntenna[0] = (CheckBox) findViewById(R.id.cb_16antenna1);
		cbAntenna[1] = (CheckBox) findViewById(R.id.cb_16antenna2);
		cbAntenna[2] = (CheckBox) findViewById(R.id.cb_16antenna3);
		cbAntenna[3] = (CheckBox) findViewById(R.id.cb_16antenna4);
		// ��2������
		cbAntenna[4] = (CheckBox) findViewById(R.id.cb_16antenna5);
		cbAntenna[5] = (CheckBox) findViewById(R.id.cb_16antenna6);
		cbAntenna[6] = (CheckBox) findViewById(R.id.cb_16antenna7);
		cbAntenna[7] = (CheckBox) findViewById(R.id.cb_16antenna8);
		// ��3������
		cbAntenna[8] = (CheckBox) findViewById(R.id.cb_16antenna9);
		cbAntenna[9] = (CheckBox) findViewById(R.id.cb_16antenna10);
		cbAntenna[10] = (CheckBox) findViewById(R.id.cb_16antenna11);
		cbAntenna[11] = (CheckBox) findViewById(R.id.cb_16antenna12);
		// ��4������
		cbAntenna[12] = (CheckBox) findViewById(R.id.cb_16antenna13);
		cbAntenna[13] = (CheckBox) findViewById(R.id.cb_16antenna14);
		cbAntenna[14] = (CheckBox) findViewById(R.id.cb_16antenna15);
		cbAntenna[15] = (CheckBox) findViewById(R.id.cb_16antenna16);

		for (int i = 0; i < cbAntennaGroup.length; i++) {
			cbAntennaGroup[i].setOnCheckedChangeListener(this);
		}
		for (int i = 0; i < cbAntenna.length; i++) {
			cbAntenna[i].setOnCheckedChangeListener(this);
		}
		checkedStr = new LinkedList<>();
		
		btnReaderAntenner = (Button)findViewById(R.id.btn_16read_antenna);
		btnSetAntenner = (Button)findViewById(R.id.btn_16set_antenna);
		
		spinnerWorkTime = (Spinner) findViewById(R.id.spinner_16work_time);
		spinnerPower = (Spinner) findViewById(R.id.spinner_16power);
		
		btnReaderAntenner.setOnClickListener(this);
		btnSetAntenner.setOnClickListener(this);
	}

	public void shake_activity_back(View v) { // ������ ���ذ�ť
		this.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_16read_antenna:
			readAntenna();
			break;
		case R.id.btn_16set_antenna:
			setAntenna();
			break;
		}
	}

	private void setAntenna() {
		AntStruct ant = new AntStruct(ReaderUtil.readers.getChannel());
		for (int i = 0; i < cbAntenna.length; i++) {
			ant.enable[i] = (byte) (cbAntenna[i].isChecked() ? 1 : 0);
		}
		ant.dwellTime[0] = Integer.parseInt(spinnerWorkTime.getSelectedItem().toString());
		ant.power[0] = Integer.parseInt(spinnerPower.getSelectedItem().toString());
		
		boolean result = readerService.setAnt(ReaderUtil.readers,ant);
		if(result){
			Toast.makeText(getApplicationContext(), "�������߳ɹ�!",Toast.LENGTH_SHORT).show();
		}
	}

	private void readAntenna() {
		AntStruct  ant = readerService.getAnt(ReaderUtil.readers);
		if(ant != null){
			Toast.makeText(getApplicationContext(), "��ȡ���߳ɹ�!",Toast.LENGTH_SHORT).show();
		}
		for (int i = 0; i < cbAntenna.length; i++) {
			cbAntenna[i].setChecked(ant.enable[i] == 1);
		}
		spinnerWorkTime.setSelection(Multichannel32_16AntCb.positionWorkTime(ant.dwellTime[0]));
		spinnerPower.setSelection(Multichannel32_16AntCb.positionPower(ant.power[0]/10));
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		boolean flag = true;
		String str = buttonView.getText().toString();
		switch (buttonView.getId()) {
		case R.id.cb_16antenna1_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 0, 4, isChecked);
			break;
		case R.id.cb_16antenna2_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 4, 8, isChecked);
			break;
		case R.id.cb_16antenna3_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 8, 12, isChecked);
			break;
		case R.id.cb_16antenna4_group:
			Multichannel32_16AntCb.selectCheckAll(checkFoUser, cbAntenna, 12, 16, isChecked);
			break;
		case R.id.cb_16antenna1:
		case R.id.cb_16antenna2:
		case R.id.cb_16antenna3:
		case R.id.cb_16antenna4:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,cbAntennaGroup, cbAntenna, 0, 4, 0, this);
			break;
		case R.id.cb_16antenna5:
		case R.id.cb_16antenna6:
		case R.id.cb_16antenna7:
		case R.id.cb_16antenna8:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,
					cbAntennaGroup, cbAntenna, 4, 8, 1, this);
			break;
		case R.id.cb_16antenna9:
		case R.id.cb_16antenna10:
		case R.id.cb_16antenna11:
		case R.id.cb_16antenna12:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,cbAntennaGroup, cbAntenna, 8, 12, 2, this);
			break;
		case R.id.cb_16antenna13:
		case R.id.cb_16antenna14:
		case R.id.cb_16antenna15:
		case R.id.cb_16antenna16:
			Multichannel32_16AntCb.selectCheckSingle(flag, str, isChecked, checkedStr,cbAntennaGroup, cbAntenna, 12, 16, 3, this);
			break;
		}
	}
}