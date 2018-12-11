package com.jietong.rfid.ui.params_set;

import java.util.List;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class FrequencySetCheckBox {
	
	public static void selectCheckAll(boolean checkFoUser,CheckBox[] cbAntenna,int start,int end,boolean isChecked){
		if (checkFoUser) {
			for (int i = start; i < end; i++) {
				cbAntenna[i].setChecked(isChecked);
			}
			return;
		}
	} 
	
	public static void selectCheckSingle(boolean flag,String str,boolean isChecked,List<String> checkedStr,CheckBox cbAntennaGroup[],CheckBox[] cbAntenna,int start,int end,int antennaGroup,OnCheckedChangeListener listener){
		if (isChecked) {
			checkedStr.add(str);
		} else {
			checkedStr.remove(str);
		}
		cbAntennaGroup[antennaGroup].setOnCheckedChangeListener(null);
		for (int i = start; i < end; i++) {
			if(!cbAntenna[i].isChecked()){
				flag = false;
				break;
			}
		}
		if(flag){
			// 表示如果都选中时，把全选按钮也选中
			cbAntennaGroup[antennaGroup].setChecked(true);
		}else{
			// 否则就全选按钮去不选中，但是这样会触发checkboxall的监听，会把所有的都取消掉
			cbAntennaGroup[antennaGroup].setChecked(false);
		}
		// checkFoUser=true;
		/* 第二种方法 */
		cbAntennaGroup[antennaGroup].setOnCheckedChangeListener(listener);
	}
}
