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
import android_serialport_api.service.SerialPortsService;
import android_serialport_api.service.impl.SerialPortsServiceImpl;
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
		spinnerSerialPort = (Spinner) findViewById(R.id.spinner_serialPorts);
		spinnerBaudRate = (Spinner) findViewById(R.id.spinner_baudRates);
		autoConnect = (Button) findViewById(R.id.btn_auto_connect);
		deviceConnect = (Button) findViewById(R.id.btn_device_connect);
		btnEntryPage = (Button) findViewById(R.id.btn_entry_page);
	}

	private void eventListener() {
		autoConnect.setOnClickListener(this);
		deviceConnect.setOnClickListener(this);
		btnEntryPage.setOnClickListener(this);
	}

	private void getSerialPorts() {
		spinnerBaudRate.setSelection(4);
		
		SerialPortsService  serialPortsService = new SerialPortsServiceImpl();
		List<String> serialPorts = serialPortsService.findSerialPorts();
		
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
			Toasts.makeTextShort(this,R.string.msg_get_serialPort_succeed);
			deviceConnect.setEnabled(true);
		} else {
			Toasts.makeTextShort(this,R.string.msg_get_serialPort_failure);
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
		default:
			break;
		}
	}

	public void connect() {
		deviceConnect.setText("");
		switch (isConnect) {
		case 0:
			connectDev();
			break;
		case 1:
			disconnect();
			break;
		}
	}

	private void disconnect() {
		if (ReaderUtil.readers != null) {
			readerService.disconnect(ReaderUtil.readers);
			ReaderUtil.readers = null;
			Toasts.makeTextShort(this,R.string.msg_disconnect_connect);
		}
		deviceConnect.setText(R.string.btn_connect_device);
		isConnect = 0;
	}

	private void connectDev() {
		comm = spinnerSerialPort.getSelectedItem().toString();
		baudRate = Integer.parseInt(spinnerBaudRate.getSelectedItem().toString());
		if (ReaderUtil.readers == null) {
			ReaderUtil.readers = readerService.serialPortConnect(comm,baudRate);
			if (ReaderUtil.readers != null) {
				Toasts.makeTextShort(this,R.string.msg_connect_succeed);
				readerService.version(ReaderUtil.readers);
				Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
				startActivity(intent);
			} else {
				Toasts.makeTextShort(this,R.string.msg_connect_failure);
			}
		}
		isConnect = 1;
		deviceConnect.setText(R.string.msg_disconnect_connect);
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
