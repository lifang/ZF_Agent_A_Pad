package com.comdo.zf_agent_a_pad.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.util.Log;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.HttpRequest;

public class Config {

	public final static String PATHS = "http://114.215.149.242:28080/ZFAgent/api/";
	public final static String IMAGE_PATH = "";
	public static String checkVersion = PATHS + "";
	public static int ROWS = 10;
	public static String token = "123";
	public static final int CODE = 1;
	public static String getmes=PATHS+"message/receiver/getAll";
	public static final String SHARED = "zfandroid";
	public static String CITY = "上海";
	public static boolean isFRIST = false;

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
    // Get msglist
	public static final String GET_MSGLIST = PATHS
			+ "message/receiver/getAll";
	
	//售后记录---售后单记录---列表
	public static final String AGENTS_SEARCH = PATHS + "cs/agents/search";
	//售后记录---售后单记录---取消申请
	public static final String AGENTS_CANCELAPPLY = PATHS + "cs/agents/cancelApply";
	//售后记录--售后单记录--增加物流信息
	public static final String AGENTS_ADDMARK = PATHS + "cs/agents/addMark";
	//售后记录---售后单记录---详情
	public static final String AGENTS_GETBYID = PATHS + "cs/agents/getById";
	//售后记录---注销记录---列表
	public static final String CANCELS_GETALL = PATHS + "cs/cancels/search";
	//售后记录---注销记录---取消申请
	public static final String CANCELS_CANCELAPPLY = PATHS + "cs/cancels/cancelApply";
	//售后记录---注销记录---重新提交注销
	public static final String CANCELS_RESUBMITCANCEL = PATHS + "cs/cancels/resubmitCancel";
	//售后记录---注销记录---详情
	public static final String CANCELS_GETCANCELBYID = PATHS + "cs/cancels/getCanCelById";
	//售后记录---更新资料记录---列表
	public static final String INFO_GETALL = PATHS + "update/info/search";
	//售后记录---更新资料记录---取消申请
	public static final String INFO_CANCELAPPLY = PATHS + "update/info/cancelApply";
	//售后记录---更新资料记录---详情
	public static final String INFO_GETINFOBYID = PATHS + "update/info/getInfoById";
	//用户管理--获得该代理商下面所有用户
	public static final String USER_GETUSER = PATHS + "user/getUser";
	//用户管理--删除用户
	public static final String USER_DELECTAGENTUSER = PATHS + "user/delectAgentUser";
	//用户管理--获得该代理商下面某个用户的相关终端列表
	public static final String USER_GETTERMINALS = PATHS + "user/getTerminals";
	
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
	
	
	public static void getAfterSaleList(
			Context context,
			int recordType,
			int customerId,
			String search,
			String q,
			int page,
			int rows,
			HttpCallback callback) {
		String url = null;
		switch (recordType) {
		case 0:
			url = AGENTS_SEARCH;
			break;
		case 1:
			url = CANCELS_GETALL;
			break;
		case 2:
			url = INFO_GETALL;
			break;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("search", search);
		params.put("q", q);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(url, params);
	}

	public static void cancelApply(
			Context context,
			int recordType,
			int id,
			HttpCallback callback) {
		String url = null;
		switch (recordType) {
		case 0:
			url = AGENTS_CANCELAPPLY;
			break;
		case 1:
			url = CANCELS_CANCELAPPLY;
			break;
		case 2:
			url = INFO_CANCELAPPLY;
			break;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		new HttpRequest(context, callback).post(url, params);
	}

	public static void getAfterSaleDetail(
			Context context,
			int recordType,
			int recordId,
			HttpCallback callback) {
		String url = null;
		switch (recordType) {
		case 0:
			url = AGENTS_GETBYID;
			break;
		case 1:
			url = CANCELS_GETCANCELBYID;
			break;
		case 2:
			url = INFO_GETINFOBYID;
			break;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		new HttpRequest(context, callback).post(url, params);
	}
	public static void resubmitCancel(
			Context context,
			int recordId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		new HttpRequest(context, callback).post(CANCELS_RESUBMITCANCEL, params);
	}
	public static void userGetUser(
			Context context,
			int customerId,
			int page,
			int rows,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(USER_GETUSER, params);
	}
	public static void userDelectAgentUser(
			Context context,
			JSONArray customerArrayId,
			int agentId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerArrayId", customerArrayId);
		params.put("agentId", agentId);
		new HttpRequest(context, callback).post(USER_DELECTAGENTUSER, params);
	}
	public static void userGetTerminals(
			Context context,
			int customerId,
			int page,
			int rows,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(USER_GETTERMINALS, params);
	}
	public static void agentsAddMark(
			Context context,
			int id,
			String computer_name,
			String track_number,
			int customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("computer_name", computer_name);
		params.put("track_number", track_number);
		params.put("customerId", customerId);
		new HttpRequest(context, callback).post(AGENTS_ADDMARK, params);
	}
	
}
