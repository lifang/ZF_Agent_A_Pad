package com.comdo.zf_agent_a_pad.util;



import android.content.Context;


public class Config {

	public final static String PATHS = "http://114.215.149.242:18080/ZFMerchant/api/";
	public final static String IMAGE_PATH = "";
	public static String checkVersion = PATHS + "";
	public static int ROWS = 10;
	public static String token = "123";
	public static final int CODE = 1;
	public static String getmes=PATHS+"message/receiver/getAll";
	public static final String UPLOAD_IMAGE = PATHS
			+ "comment/upload/tempImage";
	public static final String SHARED = "zfandroid";
	public static String CITY = "上海";
	public static boolean isFRIST = false;
	public static boolean CheckIsLogin(Context c) {
		return true;

	}

}
