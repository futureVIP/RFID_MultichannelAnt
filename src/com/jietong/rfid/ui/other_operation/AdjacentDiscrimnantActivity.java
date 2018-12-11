package com.jietong.rfid.ui.other_operation;

import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.DataConvert;
import com.jietong.rfid.util.Regex;
import com.jietong.rfid.util.Toasts;

public class AdjacentDiscrimnantActivity extends Activity implements OnClickListener {

	private TextView tvAdjacentDiscriminantTime;
	private Button btnAdjacentDiscrimnantRead;
	private Button btnAdjacentDiscrimnantSet;
	
	private ReaderService readerService = new ReaderServiceImpl();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_other_operation_adjacent_discriminant);
		inital();
	}

	private void inital() {
		controlInital();
		eventListener();
	}

	private void controlInital() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		tvAdjacentDiscriminantTime = (TextView) findViewById(R.id.et_adjacent_discriminant);
		btnAdjacentDiscrimnantRead = (Button) findViewById(R.id.btn_adjacent_discriminant_read);
		btnAdjacentDiscrimnantSet = (Button) findViewById(R.id.btn_adjacent_discriminant_set);
	}
	
	private void eventListener() {
		btnAdjacentDiscrimnantRead.setOnClickListener(this);
		btnAdjacentDiscrimnantSet.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_adjacent_discriminant_read:
			readAdjacentDiscriminant();
			break;
		case R.id.btn_adjacent_discriminant_set:
			setAdjacentDiscriminant();
			break;
		}
	}

	private void setAdjacentDiscriminant() {
		String adjcentDiscrimnant = tvAdjacentDiscriminantTime.getText().toString();
		boolean result = Regex.isDecNumber(adjcentDiscrimnant);
		if (!result) {
			Toasts.makeTextShort(this, R.string.msg_other_set_adjacent_discriminant_value_scope);
			return;
		}
		int value = Integer.parseInt(adjcentDiscrimnant);
		if (value < 0 || value > 254) {
			Toasts.makeTextShort(this,R.string.msg_other_set_adjacent_discriminant_value_scope);
			return;
		}
		boolean total = readerService.setNeighJudge(ReaderUtil.readers,(byte) value);
		if (total) {
			Toasts.makeTextShort(this, R.string.msg_other_set_adjacent_discriminant_set_succeed);
		} else {
			Toasts.makeTextShort(this, R.string.msg_other_set_adjacent_discriminant_set_failed);
		}
	}

	private void readAdjacentDiscriminant() {
		Map<String,Byte> total = readerService.getNeighJudge(ReaderUtil.readers);
		if (null != total) {
			int neighJudgeTime = DataConvert.byteToInt(total.get("neighJudgeTime"));
			tvAdjacentDiscriminantTime.setText(String.valueOf(neighJudgeTime));
			Toasts.makeTextShort(this, R.string.msg_other_set_adjacent_discriminant_read_succeed);
		} else {
			Toasts.makeTextShort(this, R.string.msg_other_set_adjacent_discriminant_read_failed);
		}
	}

	public void shake_activity_back(View v) {
		this.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
