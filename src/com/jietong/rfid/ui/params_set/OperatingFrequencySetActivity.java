package com.jietong.rfid.ui.params_set;

import java.util.List;
import java.util.Vector;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.jietong.rfid.uhf.entity.FrequencyPoint;
import com.jietong.rfid.uhf.service.ReaderService;
import com.jietong.rfid.uhf.service.impl.ReaderServiceImpl;
import com.jietong.rfid.uhf.tool.ReaderUtil;
import com.jietong.rfid.ui.R;
import com.jietong.rfid.util.Regex;
import com.jietong.rfid.util.Toasts;

public class OperatingFrequencySetActivity extends Activity implements OnClickListener,OnCheckedChangeListener,OnItemSelectedListener{
	private Button btnRead;
	private Button btnSet;
	private Spinner spinnerFrequencyArea;
	private TextView tvFrequencyFixed;
	private TextView tvFrequencyUnit;
	private Spinner spinnerFrequencyFixed;
	private EditText etFrequencyFixed;
	// 操作取消一个时，全选取消，这个变量是是否是用户点击
	private boolean checkFoUser = true;
	//902.5-927.0Mhz
	private CheckBox [] cbFrequencyPoints;
	private CheckBox cbFrequencyPointsGroup[];
	private List<String> checkedStr;
	private ReaderService readerService = new ReaderServiceImpl();
	/**
	 * 选取下拉框位置
	 */
	private int frequencyAreaPostion = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_params_set_frequency_set);
		inital();
	}

	private void inital() {
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		btnRead = (Button) findViewById(R.id.btn_params_set_frequency_read);
		btnSet = (Button) findViewById(R.id.btn_params_set_frequency_set);
		spinnerFrequencyArea = (Spinner) findViewById(R.id.Spinner_params_set_frequency_area);
		spinnerFrequencyFixed = (Spinner) findViewById(R.id.spinner_params_set_frequency_fixed_frequency);
		tvFrequencyFixed =  (TextView) findViewById(R.id.tv_params_set_frequency_fixed_frequency);
		etFrequencyFixed = (EditText) findViewById(R.id.et_params_set_frequency_fixed_frequency);
		tvFrequencyUnit = (TextView) findViewById(R.id.tv_unit_Khz);
		
		frequencyRange();
		
		btnRead.setOnClickListener(this);
		btnSet.setOnClickListener(this);
		tvFrequencyUnit.setOnClickListener(this);
		
		spinnerFrequencyArea.setOnItemSelectedListener(this);
		spinnerFrequencyFixed.setOnItemSelectedListener(this);
		
		btnRead.performClick();
	}

	private void frequencyRange() {
		
		cbFrequencyPointsGroup = new CheckBox[10];
		
		cbFrequencyPointsGroup[0] = (CheckBox) findViewById(R.id.cb_frequency_point1_group);
		cbFrequencyPointsGroup[1] = (CheckBox) findViewById(R.id.cb_frequency_point2_group);
		cbFrequencyPointsGroup[2] = (CheckBox) findViewById(R.id.cb_frequency_point3_group);
		cbFrequencyPointsGroup[3] = (CheckBox) findViewById(R.id.cb_frequency_point4_group);
		cbFrequencyPointsGroup[4] = (CheckBox) findViewById(R.id.cb_frequency_point5_group);
		cbFrequencyPointsGroup[5] = (CheckBox) findViewById(R.id.cb_frequency_point6_group);
		cbFrequencyPointsGroup[6] = (CheckBox) findViewById(R.id.cb_frequency_point7_group);
		cbFrequencyPointsGroup[7] = (CheckBox) findViewById(R.id.cb_frequency_point8_group);
		cbFrequencyPointsGroup[8] = (CheckBox) findViewById(R.id.cb_frequency_point9_group);
		cbFrequencyPointsGroup[9] = (CheckBox) findViewById(R.id.cb_frequency_point10_group);
		
		cbFrequencyPoints = new CheckBox[50];
		
		cbFrequencyPoints[0] = (CheckBox) findViewById(R.id.cb_frequency_point1);
		cbFrequencyPoints[1] = (CheckBox) findViewById(R.id.cb_frequency_point2);
		cbFrequencyPoints[2] = (CheckBox) findViewById(R.id.cb_frequency_point3);
		cbFrequencyPoints[3] = (CheckBox) findViewById(R.id.cb_frequency_point4);
		cbFrequencyPoints[4] = (CheckBox) findViewById(R.id.cb_frequency_point5);
		cbFrequencyPoints[5] = (CheckBox) findViewById(R.id.cb_frequency_point6);
		cbFrequencyPoints[6] = (CheckBox) findViewById(R.id.cb_frequency_point7);
		cbFrequencyPoints[7] = (CheckBox) findViewById(R.id.cb_frequency_point8);
		cbFrequencyPoints[8] = (CheckBox) findViewById(R.id.cb_frequency_point9);
		cbFrequencyPoints[9] = (CheckBox) findViewById(R.id.cb_frequency_point10);
		cbFrequencyPoints[10] = (CheckBox) findViewById(R.id.cb_frequency_point11);
		cbFrequencyPoints[11] = (CheckBox) findViewById(R.id.cb_frequency_point12);
		cbFrequencyPoints[12] = (CheckBox) findViewById(R.id.cb_frequency_point13);
		cbFrequencyPoints[13] = (CheckBox) findViewById(R.id.cb_frequency_point14);
		cbFrequencyPoints[14] = (CheckBox) findViewById(R.id.cb_frequency_point15);
		cbFrequencyPoints[15] = (CheckBox) findViewById(R.id.cb_frequency_point16);
		cbFrequencyPoints[16] = (CheckBox) findViewById(R.id.cb_frequency_point17);
		cbFrequencyPoints[17] = (CheckBox) findViewById(R.id.cb_frequency_point18);
		cbFrequencyPoints[18] = (CheckBox) findViewById(R.id.cb_frequency_point19);
		cbFrequencyPoints[19] = (CheckBox) findViewById(R.id.cb_frequency_point20);
		cbFrequencyPoints[20] = (CheckBox) findViewById(R.id.cb_frequency_point21);
		cbFrequencyPoints[21] = (CheckBox) findViewById(R.id.cb_frequency_point22);
		cbFrequencyPoints[22] = (CheckBox) findViewById(R.id.cb_frequency_point23);
		cbFrequencyPoints[23] = (CheckBox) findViewById(R.id.cb_frequency_point24);
		cbFrequencyPoints[24] = (CheckBox) findViewById(R.id.cb_frequency_point25);
		cbFrequencyPoints[25] = (CheckBox) findViewById(R.id.cb_frequency_point26);
		cbFrequencyPoints[26] = (CheckBox) findViewById(R.id.cb_frequency_point27);
		cbFrequencyPoints[27] = (CheckBox) findViewById(R.id.cb_frequency_point28);
		cbFrequencyPoints[28] = (CheckBox) findViewById(R.id.cb_frequency_point29);
		cbFrequencyPoints[29] = (CheckBox) findViewById(R.id.cb_frequency_point30);
		cbFrequencyPoints[30] = (CheckBox) findViewById(R.id.cb_frequency_point31);
		cbFrequencyPoints[31] = (CheckBox) findViewById(R.id.cb_frequency_point32);
		cbFrequencyPoints[32] = (CheckBox) findViewById(R.id.cb_frequency_point33);
		cbFrequencyPoints[33] = (CheckBox) findViewById(R.id.cb_frequency_point34);
		cbFrequencyPoints[34] = (CheckBox) findViewById(R.id.cb_frequency_point35);
		cbFrequencyPoints[35] = (CheckBox) findViewById(R.id.cb_frequency_point36);
		cbFrequencyPoints[36] = (CheckBox) findViewById(R.id.cb_frequency_point37);
		cbFrequencyPoints[37] = (CheckBox) findViewById(R.id.cb_frequency_point38);
		cbFrequencyPoints[38] = (CheckBox) findViewById(R.id.cb_frequency_point39);
		cbFrequencyPoints[39] = (CheckBox) findViewById(R.id.cb_frequency_point40);
		cbFrequencyPoints[40] = (CheckBox) findViewById(R.id.cb_frequency_point41);
		cbFrequencyPoints[41] = (CheckBox) findViewById(R.id.cb_frequency_point42);
		cbFrequencyPoints[42] = (CheckBox) findViewById(R.id.cb_frequency_point43);
		cbFrequencyPoints[43] = (CheckBox) findViewById(R.id.cb_frequency_point44);
		cbFrequencyPoints[44] = (CheckBox) findViewById(R.id.cb_frequency_point45);
		cbFrequencyPoints[45] = (CheckBox) findViewById(R.id.cb_frequency_point46);
		cbFrequencyPoints[46] = (CheckBox) findViewById(R.id.cb_frequency_point47);
		cbFrequencyPoints[47] = (CheckBox) findViewById(R.id.cb_frequency_point48);
		cbFrequencyPoints[48] = (CheckBox) findViewById(R.id.cb_frequency_point49);
		cbFrequencyPoints[49] = (CheckBox) findViewById(R.id.cb_frequency_point50);
		//
		for (int i = 0; i < cbFrequencyPointsGroup.length; i++) {
			cbFrequencyPointsGroup[i].setOnCheckedChangeListener(this);
		}
		for (int i = 0; i < cbFrequencyPoints.length; i++) {
			cbFrequencyPoints[i].setOnCheckedChangeListener(this);
		}
		checkedStr = new Vector<String>();
		etFrequencyFixed.setText("");
		changeFrequencyStatus(false,Color.GRAY);
		changeFrequencyFixedStatus(View.INVISIBLE);
	}
	
	private void changeFrequencyFixedStatus(int status){
		spinnerFrequencyFixed.setVisibility(status);
		tvFrequencyFixed.setVisibility(status);
		etFrequencyFixed.setVisibility(status);
		tvFrequencyUnit.setVisibility(status);
	}
	
	private void changeFrequencyStatus(boolean status,int color){
		for (CheckBox value : cbFrequencyPoints) {
			value.setEnabled(status);
			value.setTextColor(color);
		}
		
		for (CheckBox value : cbFrequencyPointsGroup) {
			value.setEnabled(status);
			value.setTextColor(color);
		}
	}

	public void shake_activity_back(View v) { // 标题栏 返回按钮
		this.finish();
	}
	
	private double getFrequencyFixedControlValue(String point){
		double frequencyFixed = 0;
		if(point.length() > 0){
			if(Regex.IsMatch(point) && Regex.IsMatchDouble(point,4)){
				frequencyFixed = Double.parseDouble(point);
				if(frequencyFixed > 1000){
					Toasts.makeTextShort(this, R.string.msg_params_set_input_frequency_error);
					return frequencyFixed;
				}
			}else{
				Toasts.makeTextShort(this, R.string.msg_params_set_input_frequency_error);
				return frequencyFixed;
			}
		}else{
			String fixed = spinnerFrequencyFixed.getSelectedItem().toString();
			int start = fixed.indexOf("-");
			int end = fixed.lastIndexOf("M");
			String value = fixed.substring(start + 1, end);
			frequencyFixed = Double.valueOf(value);
		}
		return frequencyFixed;
	}

	private void frequencySet() {
		int type = frequencyAreaPostion + 1;
		double frequencyFixed = 0;
		boolean [] frequencyHopping = new boolean[50];
		if(type == 5){
			for (int i = 0; i < cbFrequencyPoints.length; i++) {
				frequencyHopping[i] = cbFrequencyPoints[i].isChecked();
			}
		}else if(type == 6){
			String point = etFrequencyFixed.getText().toString();
			if(getFrequencyFixedControlValue(point) <= 0){
				return;
			}
			frequencyFixed = getFrequencyFixedControlValue(point);
		}
		boolean result = readerService.setFrequency(ReaderUtil.readers, type,frequencyFixed, frequencyHopping);
		setFrequencyTotalInfo(result,type);
	}
	
	private void setFrequencyTotalInfo(boolean result,int type){
		if(result){
			if(type == 5){
				Toasts.makeTextShort(this, R.string.msg_params_set_set_hopping_frequency_succeed);
				return;
			}else if(type == 6){
				Toasts.makeTextShort(this, R.string.msg_params_set_set_fixed_frequency_succeed);
				return;
			}
			Toasts.makeTextShort(this, R.string.msg_params_set_set_frequency_succeed);
			return;
		}else{
			if(type == 5){
				Toasts.makeTextShort(this, R.string.msg_params_set_set_hopping_frequency_failed);
				return;
			}else if(type == 6){
				Toasts.makeTextShort(this, R.string.msg_params_set_set_fixed_frequency_failed);
				return;
			}
			Toasts.makeTextShort(this, R.string.msg_params_set_set_frequency_failed);
			return;
		}
	}

	private void frequencyRead() {
		FrequencyPoint frequencyPoint = readerService.getFrequency(ReaderUtil.readers);
		int type = 0;
		if(null != frequencyPoint){
			type = frequencyPoint.getType();
			spinnerFrequencyArea.setSelection(type -1);
			if(type == 5){
				List<Boolean> frequency = frequencyPoint.getFrequencyHopping();
				int index = 0;
				for (Boolean value : frequency) {
					cbFrequencyPoints[index].setChecked(value);
					index++;
				}
				Toasts.makeTextShort(this, R.string.msg_params_set_read_hopping_frequency_succeed);
				return;
			}else if(type == 6){
				etFrequencyFixed.setText(String.valueOf(frequencyPoint.getFrequencyFixed()));
				Toasts.makeTextShort(this, R.string.msg_params_set_read_fixed_frequency_succeed);
				return;
			}
			Toasts.makeTextShort(this, R.string.msg_params_set_read_frequency_succeed);
			return;
		}else{
			Toasts.makeTextShort(this, R.string.msg_params_set_read_frequency_failed);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_params_set_frequency_read:
			frequencyRead();
			break;
		case R.id.btn_params_set_frequency_set:
			frequencySet();
			break;
		case R.id.tv_unit_Khz:
			etFrequencyFixed.setText("");
			break;
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		boolean flag = true;
		String str = buttonView.getText().toString();
		switch (buttonView.getId()) {
		case R.id.cb_frequency_point1_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 0, 5,isChecked);
			break;
		case R.id.cb_frequency_point2_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 5, 10,isChecked);
			break;
		case R.id.cb_frequency_point3_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 10, 15,isChecked);
			break;
		case R.id.cb_frequency_point4_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 15, 20,isChecked);
			break;
		case R.id.cb_frequency_point5_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 20, 25,isChecked);
			break;
		case R.id.cb_frequency_point6_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 25, 30,isChecked);
			break;
		case R.id.cb_frequency_point7_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 30, 35,isChecked);
			break;
		case R.id.cb_frequency_point8_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 35, 40,isChecked);
			break;
		case R.id.cb_frequency_point9_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 40, 45,isChecked);
			break;
		case R.id.cb_frequency_point10_group:
			FrequencySetCheckBox.selectCheckAll(checkFoUser, cbFrequencyPoints, 45, 50,isChecked);
			break;
			
		case R.id.cb_frequency_point1:
		case R.id.cb_frequency_point2:
		case R.id.cb_frequency_point3:
		case R.id.cb_frequency_point4:
		case R.id.cb_frequency_point5:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 0, 5, 0, this);
			break;
		case R.id.cb_frequency_point6:
		case R.id.cb_frequency_point7:
		case R.id.cb_frequency_point8:
		case R.id.cb_frequency_point9:
		case R.id.cb_frequency_point10:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 5, 10, 1, this);
			break;
		case R.id.cb_frequency_point11:
		case R.id.cb_frequency_point12:
		case R.id.cb_frequency_point13:
		case R.id.cb_frequency_point14:
		case R.id.cb_frequency_point15:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 10, 15, 2, this);
			break;
		case R.id.cb_frequency_point16:
		case R.id.cb_frequency_point17:
		case R.id.cb_frequency_point18:
		case R.id.cb_frequency_point19:
		case R.id.cb_frequency_point20:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 15, 20, 3, this);
			break;
		case R.id.cb_frequency_point21:
		case R.id.cb_frequency_point22:
		case R.id.cb_frequency_point23:
		case R.id.cb_frequency_point24:
		case R.id.cb_frequency_point25:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 20, 25, 4, this);
			break;
		case R.id.cb_frequency_point26:
		case R.id.cb_frequency_point27:
		case R.id.cb_frequency_point28:
		case R.id.cb_frequency_point29:
		case R.id.cb_frequency_point30:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 25, 30, 5, this);
			break;
		case R.id.cb_frequency_point31:
		case R.id.cb_frequency_point32:
		case R.id.cb_frequency_point33:
		case R.id.cb_frequency_point34:
		case R.id.cb_frequency_point35:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 30, 35, 6, this);
			break;
		case R.id.cb_frequency_point36:
		case R.id.cb_frequency_point37:
		case R.id.cb_frequency_point38:
		case R.id.cb_frequency_point39:
		case R.id.cb_frequency_point40:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 35, 40, 7, this);
			break;
		case R.id.cb_frequency_point41:
		case R.id.cb_frequency_point42:
		case R.id.cb_frequency_point43:
		case R.id.cb_frequency_point44:
		case R.id.cb_frequency_point45:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 40, 45, 8, this);
			break;
		case R.id.cb_frequency_point46:
		case R.id.cb_frequency_point47:
		case R.id.cb_frequency_point48:
		case R.id.cb_frequency_point49:
		case R.id.cb_frequency_point50:
			FrequencySetCheckBox.selectCheckSingle(flag, str, isChecked,checkedStr, cbFrequencyPointsGroup, cbFrequencyPoints, 45, 50, 9, this);
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		switch(parent.getId()){
		case R.id.Spinner_params_set_frequency_area:
			frequencyAreaPostion = position;
			if(position == 4){
				changeFrequencyStatus(true,Color.BLACK);
			}else{
				changeFrequencyStatus(false,Color.GRAY);
			}
			if(position == 5){
				changeFrequencyFixedStatus(View.VISIBLE);
			}else{
				changeFrequencyFixedStatus(View.INVISIBLE);
			}
			break;
		case R.id.spinner_params_set_frequency_fixed_frequency:
			etFrequencyFixed.setText("");
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
