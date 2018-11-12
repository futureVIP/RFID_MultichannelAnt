package com.jietong.rfid.util;

import android.content.Context;
import android.widget.Toast;

public class Toasts {
	public static void makeTextShort(Context context, String showInfo) {
		Toast.makeText(context, showInfo, Toast.LENGTH_SHORT).show();
	}

	public static void makeTextLong(Context context, String showInfo) {
		Toast.makeText(context, showInfo, Toast.LENGTH_LONG).show();
	}

	public static void makeTextShort(Context context, int showInfo) {
		Toast.makeText(context, showInfo, Toast.LENGTH_SHORT).show();
	}

	public static void makeTextLong(Context context, int showInfo) {
		Toast.makeText(context, showInfo, Toast.LENGTH_LONG).show();
	}
}
