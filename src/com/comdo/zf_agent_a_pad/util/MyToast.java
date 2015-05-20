package com.comdo.zf_agent_a_pad.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MyToast {

	public static void showToast(Context context, String message) {
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
