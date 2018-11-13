package com.jietong.rfid.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.util.Toasts;

public class ConnectActivity extends Activity implements OnItemClickListener,OnClickListener {

	public static ConnectActivity instance = null;
	private TextView tvConnectHint;
	private Spinner spinnerSerialPort;
	private Spinner spinnerBaudRate;
	private Button autoConnect;
	private Button deviceConnect;
	private Button btnEntryPage;
	private static String comm;
	private static int baudRate;
	private int isConnect = 0;
	private TextView tvLanguageSet;
	private ReaderService readerService = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connect_device);
		inital();
	}

	private void inital() {
		instance = this;
		controlInital();
		eventListener();
		getSerialPorts();
	}

	private void controlInital() {
		tvConnectHint = (TextView) findViewById(R.id.tv_connect_hint);
		spinnerSerialPort = (Spinner) findViewById(R.id.spinner_buzzer_on_off);
		spinnerBaudRate = (Spinner) findViewById(R.id.spinner_connect_port);
		autoConnect = (Button) findViewById(R.id.btn_auto_connect);
		deviceConnect = (Button) findViewById(R.id.btn_device_connect);
		btnEntryPage = (Button) findViewById(R.id.btn_entry_page);
		tvLanguageSet = (TextView) findViewById(R.id.tv_language);
	}

	private void eventListener() {
		autoConnect.setOnClickListener(this);
		deviceConnect.setOnClickListener(this);
		btnEntryPage.setOnClickListener(this);
		tvLanguageSet.setOnClickListener(this);
	}

	private void getSerialPorts() {
		spinnerBaudRate.setSelection(4);
		List<String> serialPorts = readerService.findSerialPorts();
		ArrayAdapter<String> aspnDevices = null;
		if (serialPorts != null) {
			int simple_spinner_item = android.R.layout.simple_spinner_item;
			int simple_spinner_dropdown_item = android.R.layout.simple_spinner_dropdown_item;
			
			aspnDevices = new ArrayAdapter<String>(this, simple_spinner_item,serialPorts);
			aspnDevices.setDropDownViewResource(simple_spinner_dropdown_item);
			spinnerSerialPort.setAdapter(aspnDevices);
			if (serialPorts.size() > 6) {
				spinnerSerialPort.setSelection(6);
			}
			tvConnectHint.setText(R.string.msg_get_serialPort_succeed);
			deviceConnect.setEnabled(true);
		} else {
			tvConnectHint.setText(R.string.msg_get_serialPort_failure);
			deviceConnect.setEnabled(false);
		}
	}

	public void connect_back(View v) {
		this.finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_device_connect:
			connect();
			break;
		case R.id.btn_auto_connect:
			getVersion();
			break;
		case R.id.btn_entry_page:
			Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_language:
			break;
		default:
			break;
		}
	}

	public void connect() {
		deviceConnect.setText("");
		switch (isConnect) {
		case 0:
			comm = spinnerSerialPort.getSelectedItem().toString();
			baudRate = Integer.parseInt(spinnerBaudRate.getSelectedItem().toString());
			if (ReaderUtil.readers == null) {
				ReaderUtil.readers = readerService.serialPortConnect(comm,baudRate);
				if (ReaderUtil.readers != null) {
					tvConnectHint.setText(R.string.msg_connect_succeed);
					readerService.version(ReaderUtil.readers);
				} else {
					tvConnectHint.setText(R.string.msg_connect_failure);
				}
			}
			isConnect = 1;
			deviceConnect.setText(R.string.msg_disconnect_connect);
			break;
		case 1:
			if (ReaderUtil.readers != null) {
				readerService.disconnect(ReaderUtil.readers);
				ReaderUtil.readers = null;
				tvConnectHint.setText(R.string.msg_disconnect_connect);
			}
			deviceConnect.setText(R.string.btn_connect_device);
			isConnect = 0;
			break;
		}
	}

	private void getVersion() {
		String version = readerService.version(ReaderUtil.readers);
		if (null != version) {
			Toasts.makeTextShort(this, version);
		} else {
			Toasts.makeTextShort(this,R.string.msg_get_version_failure);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		spinnerBaudRate.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				baudRate = Integer.parseInt(arg0.getSelectedItem().toString());
				Toasts.makeTextShort(getApplicationContext(), baudRate);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
