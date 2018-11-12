package com.jietong.rfid.ui.tag_operation;

import java.util.LinkedHashMap;
import java.util.Map;

public class OperationAreaMap {
	/**
	 * address Reserve_EPC_TID_User Mapping Collection
	 */
	public static Map<String, String[]> model = new LinkedHashMap<String, String[]>();
	static {
		// four operation area
		model.put("0", new String[] { "Reserve", "EPC", "TID", "User" });
		/****************************************************************
		 * Reserve district start address 0-3
		 ***************************************************************/
		model.put("0_0", new String[] { "0", "1", "2", "3" });
		model.put("0_0_0", new String[] { "1", "2", "3", "4" });
		model.put("0_0_1", new String[] { "1", "2", "3", });
		model.put("0_0_2", new String[] { "1", "2" });
		model.put("0_0_3", new String[] { "1" });
		/****************************************************************
		 * EPC district start address 2-7
		 ***************************************************************/
		model.put("0_1", new String[] { "2", "3", "4", "5", "6", "7" });
		model.put("0_1_0", new String[] { "1", "2", "3", "4", "5", "6" });
		model.put("0_1_1", new String[] { "1", "2", "3", "4", "5" });
		model.put("0_1_2", new String[] { "1", "2", "3", "4" });
		model.put("0_1_3", new String[] { "1", "2", "3" });
		model.put("0_1_4", new String[] { "1", "2" });
		model.put("0_1_5", new String[] { "1" });
		/****************************************************************
		 * TID district start address 0-5
		 ***************************************************************/
		model.put("0_2", new String[] { "0", "1", "2", "3", "4", "5" });
		model.put("0_2_0", new String[] { "1", "2", "3", "4", "5", "6" });
		model.put("0_2_1", new String[] { "1", "2", "3", "4", "5" });
		model.put("0_2_2", new String[] { "1", "2", "3", "4" });
		model.put("0_2_3", new String[] { "1", "2", "3" });
		model.put("0_2_4", new String[] { "1", "2" });
		model.put("0_2_5", new String[] { "1" });
		/****************************************************************
		 * User district start address 0-31
		 ***************************************************************/
		userAddressAndLength();
	}

	/**
	 * User district start address 0-31和长度
	 */
	public static void userAddressAndLength() {
		String[] user = new String[32];
		for (int i = 0; i < 32; i++) {
			user[i] = String.valueOf(i);
		}
		// User district start address 0-31
		model.put("0_3", user);
		int userCount = user.length;
		for (int i = 0; i < user.length; i++) {
			String key = "0_3_" + i;
			String[] str = new String[userCount];
			for (int j = 0; j < userCount; j++) {
				str[j] = String.valueOf(j + 1);
			}
			model.put(key, str);
			userCount--;
		}
	}

	/**************************************************************************
	 * get address Reserve EPC TID User district
	 * 
	 * @return
	 **************************************************************************/
	public static String[] getArea() {
		String [] arrArea = OperationAreaMap.model.get("0");
		return arrArea;
	}

	/**
	 * 根据选取的区域来获取地址
	 * 
	 * @param selectArea
	 * @return
	 */
	public static String[] getAddress(String selectArea) {
		String[] arrAddress = OperationAreaMap.model.get(selectArea);
		return arrAddress;
	}

	/**
	 * 根据选取的地址来获取长度
	 * 
	 * @param selectArea
	 * @return
	 */
	public static String[] getLength(String selectAddress) {
		String[] arrLength = OperationAreaMap.model.get(selectAddress);
		return arrLength;
	}
}