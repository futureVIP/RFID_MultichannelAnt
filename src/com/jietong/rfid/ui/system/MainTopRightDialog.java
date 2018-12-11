package com.jietong.rfid.ui.system;


import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;

public class MainTopRightDialog extends Activity implements OnClickListener {
	
	private LinearLayout layout;
	private TextView tvInvOnce;
	private TextView tvInventory;
	private TextView tvStopInv;
	
	private ReaderService readerService = new ReaderServiceImpl();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_main_top_right);
		inital();
	}

	private void inital() {
		layout = (LinearLayout) findViewById(R.id.main_dialog_layout);
		tvInvOnce = (TextView) findViewById(R.id.tv_basic_inv_once);
		tvInventory = (TextView) findViewById(R.id.tv_basic_inventory);
		tvStopInv = (TextView) findViewById(R.id.tv_basic_stop_inventory);
		
		tvInvOnce.setOnClickListener(this);
		tvInventory.setOnClickListener(this);
		tvStopInv.setOnClickListener(this);
		layout.setOnClickListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.main_dialog_layout:
			Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",Toast.LENGTH_SHORT).show();
			break;
		case R.id.tv_basic_inv_once:
			
			break;
		case R.id.tv_basic_inventory:
			break;
		case R.id.tv_basic_stop_inventory:
			break;
		}
	}
}
