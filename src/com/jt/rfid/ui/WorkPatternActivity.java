package com.jt.rfid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Toast;

import com.jt.rfid.ui.ShakeListener.OnShakeListener;

public class WorkPatternActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_work_pattern);
		// drawerSet ();//���� drawer���� �л� ��ť�ķ���
	}

	public void shake_activity_back(View v) { // ������ ���ذ�ť
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}