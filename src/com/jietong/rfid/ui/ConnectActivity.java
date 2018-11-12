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

public class ConnectActivity extends Activity implements OnItemClickListener,
		OnClickListener {

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
	}

	private void eventListener() {
		autoConnect.setOnClickListener(this);
		deviceConnect.setOnClickListener(this);
		btnEntryPage.setOnClickListener(this);
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
				spinnerSerialPort.setSelection(6);// 默认串口为"/dev/ttyS0"
			}
			tvConnectHint.setText("获取串口成功!");
			deviceConnect.setEnabled(true);
		} else {
			tvConnectHint.setText("未找到串口号,请检查设备是否有串口!");
			deviceConnect.setEnabled(false);
		}
	}

	public void connect_back(View v) { // 标题栏 返回按钮
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
			comm = spinnerSerialPort.getSelectedItem().toString();
			baudRate = Integer.parseInt(spinnerBaudRate.getSelectedItem().toString());
			if (ReaderUtil.readers == null) {
				ReaderUtil.readers = readerService.serialPortConnect(comm,baudRate);
				if (ReaderUtil.readers != null) {
					tvConnectHint.setText("连接成功!");
					readerService.version(ReaderUtil.readers);
				} else {
					tvConnectHint.setText("连接失败!");
				}
			}
			isConnect = 1;
			deviceConnect.setText("断开连接");
			break;
		case 1:
			if (ReaderUtil.readers != null) {
				readerService.disconnect(ReaderUtil.readers);
				ReaderUtil.readers = null;
				tvConnectHint.setText("断开连接!");
			}
			deviceConnect.setText("连接设备");
			isConnect = 0;
			break;
		}
	}

	private void getVersion() {
		String version = readerService.version(ReaderUtil.readers);
		if (null != version) {
			Toasts.makeTextShort(this, version);
		} else {
			Toasts.makeTextShort(this, "获取失败，请检查连接是否正常!");
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
