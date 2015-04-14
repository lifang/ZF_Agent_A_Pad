package com.comdo.zf_agent_a_pad.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.HttpRequest;
import com.comdo.zf_agent_a_pad.entity.Posport;
import com.google.gson.Gson;

import android.content.Context;
import android.util.Log;

public class Config {

	public final static String PATHS = "http://114.215.149.242:28080/ZFAgent/api/";
	public final static String IMAGE_PATH = "";
	public static String checkVersion = PATHS + "";
	public static int ROWS = 10;
	public static String token = "123";
	public static final int CODE = 1;
<<<<<<< HEAD

	public static final String POSLIST = PATHS + "good/list";

=======
>>>>>>> 6b51111d49294903d900810bce443f20b6a8261c
	public static String getmes=PATHS+"message/receiver/getAll";
	public static final String SHARED = "zfandroid";
	public static String CITY = "上海";
	public static boolean isFRIST = false;


	static Gson gson = new Gson();
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
<<<<<<< HEAD


=======
    // Get msglist
	public static final String GET_MSGLIST = PATHS
			+ "message/receiver/getAll";
>>>>>>> 6b51111d49294903d900810bce443f20b6a8261c
	public static boolean CheckIsLogin(Context c) {
		return true;

	}
	public static void PostSearch(Context context, int agentId,int type,String keys, int city_id,
			int rows, int page, int orderType, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("keys", keys);
		params.put("city_id", city_id);
		params.put("rows", rows);
		params.put("page", page);
		params.put("orderType", orderType);
		params.put("type", type);
		params.put("hasLease", Posport.has_purchase);
		params.put("minPrice", Posport.minPrice);
		params.put("maxPrice", Posport.maxPrice);
		try {
			params.put("brands_id",
					new JSONArray(gson.toJson(Posport.brands_id)));
			params.put("category", new JSONArray(gson.toJson(Posport.category)));
			params.put("pay_channel_id",
					new JSONArray(gson.toJson(Posport.pay_channel_id)));
			params.put("pay_card_id",
					new JSONArray(gson.toJson(Posport.pay_card_id)));
			params.put("trade_type_id",
					new JSONArray(gson.toJson(Posport.trade_type_id)));
			params.put("sale_slip_id",
					new JSONArray(gson.toJson(Posport.sale_slip_id)));
			params.put("tDate", new JSONArray(gson.toJson(Posport.tDate)));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new HttpRequest(context, callback).post(Config.POSLIST, params);
		System.out.println("参数--" + params.toString());
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
	public static void getMsgList(Context context,int customerId,
			int page,int rows,HttpCallback callback){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(GET_MSGLIST, params);
		Log.e("params", String.valueOf(params));
		Log.e("url", GET_MSGLIST);
	}
}
