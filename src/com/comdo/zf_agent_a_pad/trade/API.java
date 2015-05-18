package com.comdo.zf_agent_a_pad.trade;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.comdo.zf_agent_a_pad.trade.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.common.HttpRequest;
import com.comdo.zf_agent_a_pad.util.Config;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;

public class API {

	static Gson gson = new Gson();

	public static final String SCHEMA = "http://";
	// public static final String HOST = "114.215.149.242:28080";
	//public static final String HOST = "121.40.84.2:28080";
	public static final String HOST = "121.40.84.2:28080";
	// public static final String HOST = "192.168.1.101:8080";

	// selection terminal list
	public static final String TERMINAL_LIST = SCHEMA + HOST
			+ "/ZFAgent/api/trade/record/getTerminals";
	// selection agent list
	public static final String AGENT_LIST = SCHEMA + HOST
			+ "/ZFAgent/api/trade/record/getAgents";
	// trade record list
	public static final String TRADE_RECORD_LIST = SCHEMA + HOST
			+ "/ZFAgent/api/trade/record/getTradeRecords";
	// trade record statistic
	public static final String TRADE_RECORD_STATISTIC = SCHEMA + HOST
			+ "/ZFAgent/api/trade/record/getTradeRecordTotal";
	// trade record detail
	public static final String TRADE_RECORD_DETAIL = SCHEMA + HOST
			+ "/ZFAgent/api/trade/getTradeRecord";


	// upload image url
	public static final String UPLOAD_IMAGE = SCHEMA + HOST
			+ "/ZFMerchant/api/comment/upload/tempImage";

	public static final String WNATBUY = SCHEMA + HOST
			+ "/ZFMerchant/api/paychannel/intention/add";

	public static void getTerminalList(Context context, int agentId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		Log.e("", "" + agentId);
		new HttpRequest(context, callback).post(TERMINAL_LIST, params);
	}

	public static void getAgentList(Context context, int agentId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		Log.e("", "" + agentId);
		new HttpRequest(context, callback).post(AGENT_LIST, params);
	}

	public static void getTradeRecordDetail(Context context, int recordId,
			int agentId, int isHaveProfit, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		params.put("agentId", agentId);
		params.put("isHaveProfit", isHaveProfit);
		Log.e("", recordId + "---" + agentId + "---" + isHaveProfit);
		new HttpRequest(context, callback).post(TRADE_RECORD_DETAIL, params);
	}

	public static void getTradeRecordList(Context context, int agentId,
			int sonagentId, int tradeTypeId, String terminalNumber,
			String startTime, String endTime, int page, int rows,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeTypeId", tradeTypeId);
		params.put("agentId", agentId);
		if (sonagentId > 0) {
			params.put("sonagentId", sonagentId);
		}
		if (terminalNumber !=null) {
			params.put("terminalNumber", terminalNumber);
		}
		if (startTime !=null) {
			params.put("startTime", startTime);
		}
		if (endTime !=null) {
			params.put("endTime", endTime);
		}

		//
		// params.put("terminalNumber", terminalNumber);
		// params.put("startTime", startTime);
		// params.put("endTime", endTime);
		 params.put("page", page);
		 params.put("rows", rows);

		new HttpRequest(context, callback).post(TRADE_RECORD_LIST, params);
	}

	public static void getTradeRecordStatistic(Context context, int agentId,
			int sonagentId, int tradeTypeId, String terminalNumber,
			String startTime, String endTime, int isHaveProfit,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tradeTypeId", tradeTypeId);
		params.put("agentId", agentId);
		if (sonagentId > 0) {
			params.put("sonagentId", sonagentId);
		}
		if (terminalNumber !=null) {
			params.put("terminalNumber", terminalNumber);
		}
		if (startTime !=null) {
			params.put("startTime", startTime);
		}
		if (endTime !=null) {
			params.put("endTime", endTime);
		}
		params.put("isHaveProfit", isHaveProfit);
		new HttpRequest(context, callback).post(TRADE_RECORD_STATISTIC, params);
	}
	
	public static void noticeVideo(
 			Context context,
 			int terminalId) {
		RequestParams params = new RequestParams();
 		params.put("terminalId", terminalId);
 		new HttpRequest(context, null).post(Config.URL_NOTICE_VIDEO, params);
 	}
}
