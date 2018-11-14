package com.jietong.rfid.ui.tag_operation;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;

import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Regex;
import com.jietong.rfid.util.Toasts;

public class DesignatedAreaReadAndWriteActivity extends Activity implements	OnClickListener, OnItemSelectedListener {
	private Spinner spinnerDesignatedArea;
	private Spinner spinnerDesignatedStartAddress;
	private Spinner spinnerDesignatedLength;
	private ArrayAdapter<String> areaAdapter = null;
	private ArrayAdapter<String> startAddressAdapter = null; 
	private ArrayAdapter<String> lengthAdapter = null; 
	private int areaPosition = 0;
	private int startAddressPosition = 0;
	private int lengthPosition = 0;
	private EditText etVisitPwd;
	private Button btnContinueRead;
	private Button btnStopRead;
	private Button btnRead;
	private Button btnWrite;
	private Button btnClear;
	private MultiAutoCompleteTextView mactvDataArea;
	/**
	 * 连续读卡计数
	 */
	private static int counts = 1;
	private Timer timer = null;
	private int simple_spinner_item = android.R.layout.simple_spinner_item;
	private int simple_spinner_dropdown_item = android.R.layout.simple_spinner_dropdown_item;
	private ReaderService readerService = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_tag_operation_designated_area_read_and_write);
		inital();
	}

	@Override
	protected void onPause() {
		cancelTimer();
		super.onPause();
	}

	private void inital() {
		controlInital();
		spinnerInital();
		eventListener();
	}

	private void controlInital() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		spinnerDesignatedArea = (Spinner) findViewById(R.id.spinner_designated_area);
		spinnerDesignatedStartAddress = (Spinner) findViewById(R.id.spinner_designated_start_address);
		spinnerDesignatedLength = (Spinner) findViewById(R.id.spinner_designated_length);
		etVisitPwd = (EditText) findViewById(R.id.et_designated_area_visit_pwd);
		btnContinueRead = (Button) findViewById(R.id.btn_designated_area_continue);
		btnStopRead = (Button) findViewById(R.id.btn_designated_area_stop);
		btnRead = (Button) findViewById(R.id.btn_designated_area_read);
		btnWrite = (Button) findViewById(R.id.btn_designated_area_write);
		btnClear = (Button) findViewById(R.id.btn_designated_area_clear);
		mactvDataArea = (MultiAutoCompleteTextView) findViewById(R.id.mactv_designated_area_data);
	}

	private void spinnerInital() {
		// 默认加载操作区域
		String [] area = OperationAreaMap.getArea();
		areaAdapter = new ArrayAdapter<String>(this,simple_spinner_item,area);
		areaAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
		spinnerDesignatedArea.setAdapter(areaAdapter);
		areaPosition = spinnerDesignatedArea.getSelectedItemPosition();
		String operationArea = "0_" + areaPosition;
		// 默认加载起始地址
		String []address = OperationAreaMap.getAddress(operationArea);
		startAddressAdapter = new ArrayAdapter<String>(this,simple_spinner_item,address);
		startAddressAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
		spinnerDesignatedStartAddress.setAdapter(startAddressAdapter);
		startAddressPosition = spinnerDesignatedStartAddress.getSelectedItemPosition();
		String startAddress = "0_" + areaPosition + "_" + startAddressPosition;
		// 默认加载长度
		String [] length = OperationAreaMap.getLength(startAddress);
		lengthAdapter = new ArrayAdapter<String>(this,simple_spinner_item,length);
		lengthAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
		spinnerDesignatedLength.setAdapter(lengthAdapter);
	}

	private void eventListener() {
		spinnerDesignatedArea.setOnItemSelectedListener(this);
		spinnerDesignatedStartAddress.setOnItemSelectedListener(this);
		spinnerDesignatedLength.setOnItemSelectedListener(this);
		btnContinueRead.setOnClickListener(this);
		btnStopRead.setOnClickListener(this);
		btnRead.setOnClickListener(this);
		btnWrite.setOnClickListener(this);
		btnClear.setOnClickListener(this);
	}

	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void getStartAddress() {
		// 操作区域下拉框
		String operationArea = "0_" + areaPosition;
		String[] address = OperationAreaMap.getAddress(operationArea);
		startAddressAdapter = new ArrayAdapter<String>(this,simple_spinner_item, address);
		startAddressAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
		spinnerDesignatedStartAddress.setAdapter(startAddressAdapter);
	}

	public void getLength() {
		// 起始地址
		String startAddress = "0_" + areaPosition + "_" + startAddressPosition;
		String [] length = OperationAreaMap.getLength(startAddress);
		lengthAdapter = new ArrayAdapter<String>(this,simple_spinner_item,length);
		lengthAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
		spinnerDesignatedLength.setAdapter(lengthAdapter);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.spinner_designated_area:
			areaPosition = position;
			startAddressPosition = 0;
			getStartAddress();
			getLength();
			break;
		case R.id.spinner_designated_start_address:
			startAddressPosition = position;
			getLength();
			break;
		case R.id.spinner_designated_length:
			lengthPosition = position;
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_designated_area_read:
			cancelTimer();
			readTag();
			break;
		case R.id.btn_designated_area_write:
			cancelTimer();
			writeTag();
			break;
		case R.id.btn_designated_area_clear:
			mactvDataArea.setText("");
			counts = 1;
			break;
		case R.id.btn_designated_area_continue:
			cancelTimer();
			continueReadTag();
			break;
		case R.id.btn_designated_area_stop:
			cancelTimer();
			break;
		}
	}

	private void cancelTimer() {
		if (null != timer) {
			timer.cancel();
		}
	}

	private void continueReadTag() {
		if(null == ReaderUtil.readers){
			return;
		}
		final byte bank = (byte) areaPosition;
		final byte begin = (byte) Integer.parseInt(spinnerDesignatedStartAddress.getSelectedItem().toString());
		final byte size = (byte) Integer.parseInt(spinnerDesignatedLength.getSelectedItem().toString());
		final byte[] password = new byte[4];
		final String visit = etVisitPwd.getText().toString().trim();
		if (visit.length() != 8) {
			Toasts.makeTextShort(getApplicationContext(), R.string.msg_pwd_must_eight);
			return;
		} else if (!Regex.isHexCharacter(visit)) {
			Toasts.makeTextShort(getApplicationContext(),R.string.msg_pwd_Invalid_char);
			return;
		}
		for (int i = 0; i < 4; ++i) {
			String str = visit.substring(i * 2, (2 + i * 2));
			password[i] = Byte.parseByte(str, 16);
		}
		
		mactvDataArea.setText("");
		counts = 1;
		timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				String data = readerService.readTagData(ReaderUtil.readers, bank,begin, size, password);
				showData(data);
			}
		};
		timer.schedule(task, 1000, 2000);
	}

	private void showData(final String data) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (null != data) {
					if (mactvDataArea.getText().equals("")) {
						mactvDataArea.setText(counts + "." + " " + data);
						counts += 1;
					} else {
						StringBuffer sb = new StringBuffer();
						sb.append(mactvDataArea.getText());
						sb.append("\r");
						sb.append("\n");
						sb.append(counts);
						sb.append(".");
						sb.append(" ");
						sb.append(data);
						mactvDataArea.setText(sb.toString());
						counts += 1;
					}
					Toasts.makeTextShort(getApplicationContext(),R.string.msg_read_data_success);
				} else {
					Toasts.makeTextShort(getApplicationContext(),R.string.msg_read_data_failure);
				}
			}
		});
	}

	private void writeTag() {
		if(null == ReaderUtil.readers){
			return;
		}
		byte bank = (byte) areaPosition;
		byte begin = (byte) Integer.parseInt(spinnerDesignatedStartAddress
				.getSelectedItem().toString());
		byte size = (byte) Integer.parseInt(spinnerDesignatedLength
				.getSelectedItem().toString());
		byte[] password = new byte[4];
		String visit = etVisitPwd.getText().toString().trim();
		if (visit.length() != 8) {
			Toasts.makeTextShort(this, R.string.msg_pwd_must_eight);
			return;
		} else if (!Regex.isHexCharacter(visit)) {
			Toasts.makeTextShort(this, R.string.msg_pwd_Invalid_char);
			return;
		}
		String inData = mactvDataArea.getText().toString().replace(" ", "");
		if (inData.length() % 4 != 0 || inData.length() / 4 != size) {
			Toasts.makeTextShort(this,R.string.msg_length_diff);
			return;
		}
		if (!Regex.isHexCharacter(inData)) {
			Toasts.makeTextShort(this, R.string.msg_pwd_Invalid_char);
			return;
		}
		boolean result = readerService.writeTagData(ReaderUtil.readers, bank, begin, size, inData,password);
		if(result){
			Toasts.makeTextShort(this, R.string.msg_write_to_successful);
		}else{
			Toasts.makeTextShort(this, R.string.msg_write_to_failure);
		}
	}

	private void readTag() {
		if(null == ReaderUtil.readers){
			return;
		}
		byte bank = (byte) areaPosition;
		byte begin = (byte) Integer.parseInt(spinnerDesignatedStartAddress.getSelectedItem().toString());
		byte size = (byte) Integer.parseInt(spinnerDesignatedLength.getSelectedItem().toString());
		byte[] password = new byte[4];
		String visit = etVisitPwd.getText().toString().trim();
		if (visit.length() != 8) {
			Toasts.makeTextShort(getApplicationContext(),R.string.msg_pwd_must_eight);
			return;
		} else if (!Regex.isHexCharacter(visit)) {
			Toasts.makeTextShort(getApplicationContext(),R.string.msg_pwd_Invalid_char);
			return;
		}
		for (int i = 0; i < 4; ++i) {
			String str = visit.substring(i * 2, (2 + i * 2));
			password[i] = Byte.parseByte(str, 16);
		}

		String data = readerService.readTagData(ReaderUtil.readers, bank, begin,size, password);
		if (data != null) {
			Toasts.makeTextShort(getApplicationContext(),R.string.msg_read_data_success);
			mactvDataArea.setText(data);
		} else {
			Toasts.makeTextShort(getApplicationContext(),R.string.msg_read_data_failure);
			return;
		}
	}

	public void shake_activity_back() {
		this.finish();
	}
}