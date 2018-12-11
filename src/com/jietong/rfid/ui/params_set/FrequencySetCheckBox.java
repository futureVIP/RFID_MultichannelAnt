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
			// ��ʾ�����ѡ��ʱ����ȫѡ��ťҲѡ��
			cbAntennaGroup[antennaGroup].setChecked(true);
		}else{
			// �����ȫѡ��ťȥ��ѡ�У����������ᴥ��checkboxall�ļ�����������еĶ�ȡ����
			cbAntennaGroup[antennaGroup].setChecked(false);
		}
		// checkFoUser=true;
		/* �ڶ��ַ��� */
		cbAntennaGroup[antennaGroup].setOnCheckedChangeListener(listener);
	}
}
