package com.jietong.rfid.ui.system;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.jietong.rfid.ui.MainActivity;
import com.jietong.rfid.ui.R;

public class Exit extends Activity implements OnClickListener {

	private LinearLayout layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog);
		inital();
	}

	private void inital() {
		layout = (LinearLayout) findViewById(R.id.ll_exit_program);
		eventListener();
	}

	private void eventListener() {
		layout.setOnClickListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return true;
	}

	public void btn_exit_no(View v) {
		this.finish();
	}

	public void btn_exit_yes(View v) {
		this.finish();
		MainActivity.instance.finish();// 关闭Main 这个Activity
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_exit_program:
			//Toasts.makeTextShort(this, "提示：点击窗口外部关闭窗口！");
			break;
		}
	}
}
