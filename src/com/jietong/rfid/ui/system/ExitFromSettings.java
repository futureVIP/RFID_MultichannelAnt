package com.jietong.rfid.ui.system;

import com.jietong.rfid.ui.MainActivity;
import com.jietong.rfid.ui.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class ExitFromSettings extends Activity implements OnClickListener {
	//private MyDialog dialog;
	private LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog_from_settings);
		inital();
	}

	private void inital() {
		layout=(LinearLayout)findViewById(R.id.exit_layout2);
		eventListener();
	}

	private void eventListener() {
		layout.setOnClickListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void exitbutton1(View v) {  
    	this.finish();    	
      }  
	public void exitbutton0(View v) {  
    	this.finish();
    	MainActivity.instance.finish();//关闭Main 这个Activity
      }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.exit_layout2:
			//Toasts.makeTextShort(this, "提示：点击窗口外部关闭窗口！");	
			break;
		}
	}  
}
