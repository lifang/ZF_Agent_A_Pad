package com.comdo.zf_agent_a_pad.util;

import java.util.HashMap;
import java.util.Map;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.HttpRequest;

import android.content.Context;

public class Config {

	public final static String PATHS = "http://114.215.149.242:18080/ZFMerchant/api/";
	public final static String IMAGE_PATH = "";
	public static String checkVersion = PATHS + "";
	public static int ROWS = 10;
	public static String token = "123";
	public static final int CODE = 1;

	// upload image url
	public static final String UPLOAD_IMAGE = PATHS
			+ "/comment/upload/tempImage";

	// Apply List
	public static final String APPLY_LIST = PATHS + "/apply/getApplyList";
	// Apply Detail
	public static final String APPLY_DETAIL = PATHS + "/apply/getApplyDetails";

	// Get the Merchant Detail
	public static final String APPLY_MERCHANT_DETAIL = PATHS
			+ "/apply/getMerchant";

	// Get the Channel List
	public static final String APPLY_CHANNEL_LIST = PATHS
			+ "/apply/getChannels";

	// Get the Bank List
	public static final String APPLY_BANK_LIST = PATHS + "/apply/ChooseBank";

	// find pos password
	public static final String TERMINAL_FIND_POS = PATHS
			+ "/terminal/Encryption";

	// Terminal list
	public static final String TERMINAL_APPLY_LIST = PATHS
			+ "/terminal/getApplyList";

	// Terminal detail
	public static final String TERMINAL_DETAIL = PATHS
			+ "/terminal/getApplyDetails";

	public static boolean CheckIsLogin(Context c) {
		return true;

	}

	public static void getApplyList(Context context, int customerId, int page,
			int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customersId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(APPLY_LIST, params);
	}

	public static void getApplyDetail(Context context, int customerId,
			int terminalId, int status, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("terminalsId", terminalId);
		params.put("status", status);
		new HttpRequest(context, callback).post(APPLY_DETAIL, params);
	}

	public static void getApplyMerchantDetail(Context context, int merchantId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantId", merchantId);
		new HttpRequest(context, callback).post(APPLY_MERCHANT_DETAIL, params);
	}

	public static void getApplyChannelList(Context context,
			HttpCallback callback) {
		new HttpRequest(context, callback).post(APPLY_CHANNEL_LIST);
	}

	public static void getApplyBankList(Context context, String terminalNumber,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("serial_num", terminalNumber);
		new HttpRequest(context, callback).post(APPLY_BANK_LIST, params);
	}

	public static void findPosPassword(Context context, int terminalId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalid", terminalId);
		new HttpRequest(context, callback).post(TERMINAL_FIND_POS, params);
	}

	public static void getTerminalApplyList(Context context, int customerId,
			int page, int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customersId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(TERMINAL_APPLY_LIST, params);
	}

	public static void getTerminalDetail(Context context, int terminalId,
			int customerId, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("terminalsId", terminalId);
		params.put("customerId", customerId);
		new HttpRequest(context, callback).post(TERMINAL_DETAIL, params);
	}
}
