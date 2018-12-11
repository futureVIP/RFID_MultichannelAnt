package com.jietong.rfid.ui.basic_operation;

import java.util.List;

import com.jietong.rfid.uhf.entity.EPC;

public class DataFilter {
	public static void dataFilter(List<EPC> listEPC, String epc,String rssi, String ant,String deviceNo,String direction) {
		if (listEPC.isEmpty()) {
			EPC epcTag = new EPC();
			epcTag.setId(1);
			epcTag.setEpc(epc);
			epcTag.setRssi(rssi);
			epcTag.setAnt(ant);
			epcTag.setDeviceNo(deviceNo);
			epcTag.setDirection(direction);
			epcTag.setCount(1);
			listEPC.add(epcTag);
		} else {
			for (int i = 0; i < listEPC.size(); i++) {
				int count = listEPC.size();
				EPC mEPC = listEPC.get(i);
				if (epc.equals(mEPC.getEpc()) && ant.equals(mEPC.getAnt())) {
					mEPC.setCount(mEPC.getCount() + 1);
					mEPC.setRssi(rssi);
					listEPC.set(i, mEPC);
					break;
				} else if (i == (listEPC.size() - 1)) {
					count++;
					EPC newEPC = new EPC();
					newEPC.setId(count);
					newEPC.setEpc(epc);
					newEPC.setRssi(rssi);
					newEPC.setAnt(ant);
					newEPC.setDeviceNo(deviceNo);
					newEPC.setDirection(direction);
					newEPC.setCount(1);
					listEPC.add(newEPC);
				}
			}
		}
	}
	
	public static void dataFilter(List<EPC> listEPC, String epc, String ant) {
		if (listEPC.isEmpty()) {
			EPC epcTag = new EPC();
			epcTag.setId(1);
			epcTag.setEpc(epc);
			epcTag.setAnt(ant);
			epcTag.setCount(1);
			listEPC.add(epcTag);
		} else {
			for (int i = 0; i < listEPC.size(); i++) {
				int count = listEPC.size();
				EPC mEPC = listEPC.get(i);
				if (epc.equals(mEPC.getEpc()) && ant.equals(mEPC.getAnt())) {
					mEPC.setCount(mEPC.getCount() + 1);
					listEPC.set(i, mEPC);
					break;
				} else if (i == (listEPC.size() - 1)) {
					count++;
					EPC newEPC = new EPC();
					newEPC.setId(count);
					newEPC.setEpc(epc);
					newEPC.setAnt(ant);
					newEPC.setCount(1);
					listEPC.add(newEPC);
				}
			}
		}
	}
}
