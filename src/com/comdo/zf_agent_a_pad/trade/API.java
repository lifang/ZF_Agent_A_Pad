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

	// selection terminal list
	public static final String TERMINAL_LIST = Config.PATHS
			+ "trade/record/getTerminals";
	// selection agent list
	public static final String AGENT_LIST = Config.PATHS
			+ "trade/record/getAgents";
	// trade record list
	public static final String TRADE_RECORD_LIST = Config.PATHS
			+ "trade/record/getTradeRecords";
	// trade record statistic
	public static final String TRADE_RECORD_STATISTIC = Config.PATHS
			+ "trade/record/getTradeRecordTotal";
	// trade record detail
	public static final String TRADE_RECORD_DETAIL = Config.PATHS
			+ "trade/getTradeRecord";


	// upload image url
	public static final String UPLOAD_IMAGE = Config.PATHS
			+ "comment/upload/tempImage";

	public static final String WNATBUY = Config.PATHS
			+ "paychannel/intention/add";

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
