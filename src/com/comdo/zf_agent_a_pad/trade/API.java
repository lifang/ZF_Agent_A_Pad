package com.comdo.zf_agent_a_pad.trade;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.comdo.zf_agent_a_pad.trade.common.HttpCallback;
import com.comdo.zf_agent_a_pad.trade.common.HttpRequest;
import com.google.gson.Gson;

import com.google.gson.Gson;

public class API {

	static Gson gson = new Gson();

	public static final String SCHEMA = "http://";
//	public static final String HOST = "114.215.149.242:28080";
	public static final String HOST = "121.40.84.2:28080";
	
	// public static final String HOST = "192.168.1.101:8080";
	public static String GET_USERINFO = SCHEMA + HOST
			+ "/ZFMerchant/api/customers/getOne/";
	// change userinfo
	public static String CHANGE_USERINFO = SCHEMA + HOST
			+ "/ZFMerchant/api/customers/update/";
	public static String CHANGE_PAW = SCHEMA + HOST
			+ "/ZFMerchant/api/customers/updatePassword";
	// get addresslist
	public static String GET_ADRESS = SCHEMA + HOST
			+ "/ZFMerchant/api/customers/getAddressList/";

	// creat merchant
	public static String CREAT_MERCHANT = SCHEMA + HOST
			+ "/ZFMerchant/api/merchant/insert/";
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

	// After sale record list
	public static final String AFTER_SALE_MAINTAIN_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/repair/getAll";
	public static final String AFTER_SALE_RETURN_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/return/getAll";
	public static final String AFTER_SALE_CANCEL_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/cancels/getAll";
	public static final String AFTER_SALE_CHANGE_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/change/getAll";
	public static final String AFTER_SALE_UPDATE_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/update/info/getAll";
	public static final String AFTER_SALE_LEASE_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/lease/returns/getAll";

	// After sale record detail
	public static final String AFTER_SALE_MAINTAIN_DETAIL = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/repair/getRepairById";
	public static final String AFTER_SALE_RETURN_DETAIL = SCHEMA + HOST
			+ "/ZFMerchant/api/return/getReturnById";
	public static final String AFTER_SALE_CANCEL_DETAIL = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/cancels/getCanCelById";
	public static final String AFTER_SALE_CHANGE_DETAIL = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/change/getChangeById";
	public static final String AFTER_SALE_UPDATE_DETAIL = SCHEMA + HOST
			+ "/ZFMerchant/api/update/info/getInfoById";
	public static final String AFTER_SALE_LEASE_DETAIL = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/lease/returns/getById";

	// After sale record cancel apply
	public static final String AFTER_SALE_MAINTAIN_CANCEL = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/repair/cancelApply";
	public static final String AFTER_SALE_RETURN_CANCEL = SCHEMA + HOST
			+ "/ZFMerchant/api/return/cancelApply";
	public static final String AFTER_SALE_CANCEL_CANCEL = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/cancels/cancelApply";
	public static final String AFTER_SALE_CHANGE_CANCEL = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/change/cancelApply";
	public static final String AFTER_SALE_UPDATE_CANCEL = SCHEMA + HOST
			+ "/ZFMerchant/api/update/info/cancelApply";
	public static final String AFTER_SALE_LEASE_CANCEL = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/lease/returns/cancelApply";

	// After sale resubmit cancel
	public static final String AFTER_SALE_RESUBMIT_CANCEL = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/cancels/resubmitCancel";

	// After sale add mark
	public static final String AFTER_SALE_MAINTAIN_ADD_MARK = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/repair/addMark";
	public static final String AFTER_SALE_RETURN_ADD_MARK = SCHEMA + HOST
			+ "/ZFMerchant/api/return/addMark";
	public static final String AFTER_SALE_CHANGE_ADD_MARK = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/change/addMark";
	public static final String AFTER_SALE_LEASE_ADD_MARK = SCHEMA + HOST
			+ "/ZFMerchant/api/cs/lease/returns/addMark";

	// Terminal list
	public static final String TERMINAL_APPLY_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/terminal/getApplyList";
	// Channel list
	public static final String CHANNEL_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/terminal/getFactories";
	// Terminal Add
	public static final String TERMINAL_ADD = SCHEMA + HOST
			+ "/ZFMerchant/api/terminal/addTerminal";
	// Terminal detail
	public static final String TERMINAL_DETAIL = SCHEMA + HOST
			+ "/ZFMerchant/api/terminal/getApplyDetails";

	// synchronise terminal
	public static final String TERMINAL_SYNC = SCHEMA + HOST
			+ "/ZFMerchant/api/terminal/synchronous";
	// find pos password
	public static final String TERMINAL_FIND_POS = SCHEMA + HOST
			+ "/ZFMerchant/api/terminal/Encryption";

	// Apply List
	public static final String APPLY_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/apply/getApplyList";
	// Apply Detail
	public static final String APPLY_DETAIL = SCHEMA + HOST
			+ "/ZFMerchant/api/apply/getApplyDetails";
	// Get the Merchant Detail
	public static final String APPLY_MERCHANT_DETAIL = SCHEMA + HOST
			+ "/ZFMerchant/api/apply/getMerchant";
	// Get the Channel List
	public static final String APPLY_CHANNEL_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/apply/getChannels";
	// Get the Bank List
	public static final String APPLY_BANK_LIST = SCHEMA + HOST
			+ "/ZFMerchant/api/apply/ChooseBank";

	// upload image url
	public static final String UPLOAD_IMAGE = SCHEMA + HOST
			+ "/ZFMerchant/api/comment/upload/tempImage";

	public static final String WNATBUY = SCHEMA + HOST
			+ "/ZFMerchant/api/paychannel/intention/add";

	// Apply Opening Progress Query
	public static final String APPLY_PROGRESS = SCHEMA + HOST
			+ "/ZFMerchant/api/terminal/openStatus";
	// Get merchant list
	public static String GET_MERCHANTLIST = SCHEMA + HOST
			+ "/ZFMerchant/api/merchant/getList/";
	// Add address
	public static final String Add_ress = SCHEMA + HOST
			+ "/ZFMerchant/api/customers/insertAddress/";
	// update address
	public static final String update_ress = SCHEMA + HOST
			+ "/ZFMerchant/api/customers/updateAddress/";
	// get totalscore
	public static String total_score = SCHEMA + HOST
			+ "/ZFMerchant/api/customers/getIntegralTotal/";
	// exchange score
	public static String exchange_score = SCHEMA + HOST
			+ "/ZFMerchant/api/customers/insertIntegralConvert";

	public static final String GETCODE4PHONE = SCHEMA + HOST
			+ "/ZFMerchant/api/user/sendPhoneVerificationCodeReg";
	public static final String ZHUCHE = SCHEMA + HOST
			+ "/ZFMerchant/api/user/userRegistration";
	public static final String GETEMAILPASS = SCHEMA + HOST
			+ "/ZFMerchant/api/user/sendEmailVerificationCode";
	// Apply Submit
	public static final String APPLY_SUBMIT = SCHEMA + HOST
			+ "/ZFMerchant/api/apply/addOpeningApply";
	// delect merchant
	public static String DELECT_MERCHANTLIST = SCHEMA + HOST
			+ "/ZFMerchant/api/merchant/delete/";
	// update merchant
	 	public static String UPDATE_MERCHANT=SCHEMA + HOST
	 			+"/ZFMerchant/api/merchant/update/";
	// update file
	 	public static String UPDATE_FILE=SCHEMA + HOST
	 			+"/ZFMerchant/api/merchant/upload/file";
	 	
	 	public static void getTerminalList(Context context, int agentId,
	 			HttpCallback callback) {
	 		Map<String, Object> params = new HashMap<String, Object>();
			params.put("agentId", agentId);
			Log.e("", ""+agentId);
	 		new HttpRequest(context, callback).post(TERMINAL_LIST,params);
	 	}
	 	
	 	public static void getAgentList(Context context, int agentId,
				HttpCallback callback) {
	 		Map<String, Object> params = new HashMap<String, Object>();
			params.put("agentId", agentId);
			Log.e("", ""+agentId);
			new HttpRequest(context, callback).post(AGENT_LIST,params);
		}
	 	
	 	
	 	public static void getTradeRecordDetail(Context context,int recordId,int agentId,int isHaveProfit, HttpCallback callback) {
	 		Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("id", recordId);
	 		params.put("agentId", agentId);
	 		params.put("isHaveProfit", isHaveProfit);
	 		Log.e("", recordId+"---"+agentId+"---"+isHaveProfit);
			new HttpRequest(context, callback).post(TRADE_RECORD_DETAIL,params);
		}
	 	
	 	public static void getTradeRecordList(Context context,int agentId,int sonagentId, int tradeTypeId,
				String terminalNumber, String startTime, String endTime, int page,
				int rows, HttpCallback callback) {
	 		Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("tradeTypeId", tradeTypeId);
	 		params.put("agentId", agentId);
	 		params.put("sonagentId", sonagentId);
	 		params.put("terminalNumber", terminalNumber);
	 		params.put("startTime", startTime);
	 		params.put("endTime", endTime);
	 		params.put("page", page);
			params.put("rows", rows);
			
			Log.e("", "tradeTypeId=="+tradeTypeId+"agentId=="+agentId+"sonagentId=="+sonagentId+
					"terminalNumber=="+terminalNumber);
			new HttpRequest(context, callback).post(TRADE_RECORD_LIST, params);
		}
	 	
	 	public static void getTradeRecordStatistic(Context context,int agentId,int sonagentId, int tradeTypeId,
				String terminalNumber, String startTime, String endTime,
				int isHaveProfit, HttpCallback callback) {
	 		Map<String, Object> params = new HashMap<String, Object>();
	 		params.put("tradeTypeId", tradeTypeId);
	 		params.put("agentId", agentId);
	 		params.put("sonagentId", sonagentId);
	 		params.put("terminalNumber", terminalNumber);
	 		params.put("startTime", startTime);
	 		params.put("endTime", endTime);
	 		params.put("isHaveProfit", isHaveProfit);
	 		Log.e("", "tradeTypeId=="+tradeTypeId+"agentId=="+agentId+"sonagentId=="+sonagentId+
					"terminalNumber=="+terminalNumber+"isHaveProfit=="+isHaveProfit);
			new HttpRequest(context, callback).post(TRADE_RECORD_STATISTIC, params);
		}
}
