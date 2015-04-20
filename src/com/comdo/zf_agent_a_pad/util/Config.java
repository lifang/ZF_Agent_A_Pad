package com.comdo.zf_agent_a_pad.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.comdo.zf_agent_a_pad.common.HttpCallback;
import com.comdo.zf_agent_a_pad.common.HttpRequest;
import com.comdo.zf_agent_a_pad.entity.Posport;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
//github.com/lifang/ZF_Agent_A_Pad
import android.content.Context;

import android.util.Log;

public class Config {

	public final static String PATHS = "http://114.215.149.242:28080/ZFAgent/api/";
	public final static String IMAGE_PATH = "";
	public static String checkVersion = PATHS + "";
	public static int ROWS = 10;
	public static String token = "123";
	public static final int CODE = 1;

	// upload image url
	public static final String UPLOAD_IMAGE = PATHS
			+ "/comment/upload/tempImage";
	public static String getmes = PATHS + "message/receiver/getAll";
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
	// upload image url
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

		public static final String POSLIST = PATHS + "good/list";
	// Get msglist
	public static final String GET_MSGLIST = PATHS + "message/receiver/getAll";
	//	public static final String GET_MSGLIST = "http://192.168.199.206:8080/zfagent/api/" + "message/receiver/getAll";
	// 售后记录---售后单记录---列表
	public static final String AGENTS_SEARCH = PATHS + "cs/agents/search";
	// 售后记录---售后单记录---取消申请
	public static final String AGENTS_CANCELAPPLY = PATHS
			+ "cs/agents/cancelApply";
	// 售后记录--售后单记录--增加物流信息
	public static final String AGENTS_ADDMARK = PATHS + "cs/agents/addMark";
	// 售后记录---售后单记录---详情
	public static final String AGENTS_GETBYID = PATHS + "cs/agents/getById";
	// 售后记录---注销记录---列表
	public static final String CANCELS_GETALL = PATHS + "cs/cancels/search";
	// 售后记录---注销记录---取消申请
	public static final String CANCELS_CANCELAPPLY = PATHS
			+ "cs/cancels/cancelApply";
	// 售后记录---注销记录---重新提交注销
	public static final String CANCELS_RESUBMITCANCEL = PATHS
			+ "cs/cancels/resubmitCancel";
	// 售后记录---注销记录---详情
	public static final String CANCELS_GETCANCELBYID = PATHS
			+ "cs/cancels/getCanCelById";
	// 售后记录---更新资料记录---列表
	public static final String INFO_GETALL = PATHS + "update/info/search";
	// 售后记录---更新资料记录---取消申请
	public static final String INFO_CANCELAPPLY = PATHS
			+ "update/info/cancelApply";
	// 售后记录---更新资料记录---详情
	public static final String INFO_GETINFOBYID = PATHS
			+ "update/info/getInfoById";
	// 用户管理--获得该代理商下面所有用户
	public static final String USER_GETUSER = PATHS + "user/getUser";
	// 用户管理--删除用户
	public static final String USER_DELECTAGENTUSER = PATHS
			+ "user/delectAgentUser";
	// 用户管理--获得该代理商下面某个用户的相关终端列表
	public static final String USER_GETTERMINALS = PATHS + "user/getTerminals";

	// get code
	public static final String GETCODE4PHONE = PATHS
			+ "/user/sendPhoneVerificationCodeReg";
   // get info
	public static final String GETMYBASEINFO = PATHS
			+ "agents/getOne";
	//delect onemsg
	public static final String DELECT_ONE = PATHS
			+ "message/receiver/deleteById";
	// get msg_detail
	public static final String GET_MSGDETAIL = PATHS
			+ "message/receiver/getById";
	// delect more msg
	public static final String DELECT_MORE = PATHS
			+ "message/receiver/batchDelete";
	// is read
	public static final String IS_READ = PATHS
			+ "message/receiver/batchRead";
	//public static final String IS_READ = "http://192.168.199.206:8080/zfagent/api/" +
	//				"message/receiver/batchRead";
	//get myinfo
	public static final String GET_INFO = PATHS
			+ "agents/getOne";
	//get address_list
	public static final String GET_ADDRESS_LIST = PATHS
			+ "agents/getAddressList";
	//add address
	public static final String ADD_ADDRESS = PATHS
			+ "agents/insertAddress/";
	//get staffmanager_list
	public static final String GET_STAFFMANAGER_LIST = PATHS
			+ "customerManage/getList";
	//add staffmanager
		public static final String ADD_STAFFMANAGER = PATHS
				+ "customerManage/insert";
	// change paw
		public static final String CHANGE_PAW = PATHS
				+ "agents/updatePassword";
    // get lowerAgent_list
		public static final String AGENT_LIST = PATHS
				+ "lowerAgent/list";
   //  get agent_detail
		public static final String AGENT_DETAIL = PATHS
				+ "lowerAgent/info";
	// reset Profit
		public static final String RESET_PROFIT = PATHS
				+ "lowerAgent/changeProfit";
	// upload File
		public static final String UPLOAD_FILE = PATHS
				+ "lowerAgent/uploadImg";
	// edit address
		public static final String EDIT_ADDRESS = PATHS
				+ "customers/updateAddress";
		//public static final String EDIT_ADDRESS = "http://192.168.199.206:8080/zfagent/api/" +
		//		"message/receiver/batchRead";
    // creat agent
		public static final String CREAT_AGENT = PATHS
				+ "lowerAgent/createNew";
    // get distribute_list
		public static final String GET_DISTRIBUTE = PATHS
				+ "preparegood/list";
	// get agent_list
		public static final String GET_AGENTLIST = PATHS
				+ "preparegood/getsonagent";
    // get distribute_detail
		public static final String GET_DISTRIBUTE_DETAIL = PATHS
				+ "preparegood/info";
    // get transgood_list
		public static final String GET_TRANS_LIST = PATHS
				+ "exchangegood/list";
	// save insert_customer
		public static final String INSERT_CUSTOMER = PATHS
				+ "customerManage/insert";
	public static boolean CheckIsLogin(Context c) {
		return true;

	}

	public static void PostSearch(Context context, int agentId, int type,
			String keys, int city_id, int rows, int page, int orderType,
			HttpCallback callback) {
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

	public static void getMsgList(Context context, int customerId, int page,
			int rows, HttpCallback callback) {
		RequestParams params=new RequestParams();
		//Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(GET_MSGLIST, params);
		Log.e("params", String.valueOf(params));
		Log.e("url", GET_MSGLIST);
	}

	public static void getAfterSaleList(Context context, int recordType,
			int customerId, String search, String q, int page, int rows,
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

	public static void cancelApply(Context context, int recordType, int id,
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

	public static void getAfterSaleDetail(Context context, int recordType,
			int recordId, HttpCallback callback) {
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

	public static void resubmitCancel(Context context, int recordId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", recordId);
		new HttpRequest(context, callback).post(CANCELS_RESUBMITCANCEL, params);
	}

	public static void userGetUser(Context context, int customerId, int page,
			int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(USER_GETUSER, params);
	}

	public static void userDelectAgentUser(Context context,
			JSONArray customerArrayId, int agentId, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerArrayId", customerArrayId);
		params.put("agentId", agentId);
		new HttpRequest(context, callback).post(USER_DELECTAGENTUSER, params);
	}

	public static void userGetTerminals(Context context, int customerId,
			int page, int rows, HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("page", page);
		params.put("rows", rows);
		new HttpRequest(context, callback).post(USER_GETTERMINALS, params);
	}

	public static void agentsAddMark(Context context, int id,
			String computer_name, String track_number, int customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("computer_name", computer_name);
		params.put("track_number", track_number);
		params.put("customerId", customerId);
		new HttpRequest(context, callback).post(AGENTS_ADDMARK, params);
	}

	public static void AddAdres1(Context context, String codeNumber,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codeNumber", codeNumber);

		new HttpRequest(context, callback).post(GETCODE4PHONE, params);
	}
	public static void GetMyInfo(Context context, int  customerId,
			HttpCallback callback) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);

		new HttpRequest(context, callback).post(GETMYBASEINFO, params);
	}
	public static void DelectOneMsg(Context context, int  id,int customerId,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		params.put("id", id);
		params.put("customerId", customerId);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(DELECT_ONE, params);
	}
	public static void GetMsgDetail(Context context, int  id,int customerId,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		params.put("id", id);
		params.put("customerId", customerId);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(GET_MSGDETAIL, params);
	}
	public static void DelectMoreMsg(Context context, String[] ids,
			int customerId,HttpCallback callback) {
		RequestParams params=new RequestParams();
		try {
			params.put("ids", new JSONArray(gson.toJson(ids)));
			params.put("customerId", customerId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(DELECT_MORE, params);
	}
	public static void IsRead(Context context, String[] ids,
			int customerId,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		try {
			params.put("ids", new JSONArray(gson.toJson(ids)));
			params.put("customerId", customerId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(IS_READ, params);
	}
	public static void GetInfo(Context context, int customerId,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
			params.put("customerId", customerId);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(GET_INFO, params);
	}
	public static void GetAdressList(Context context, int  customerId,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		params.put("customerId", customerId);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(GET_ADDRESS_LIST, params);
		Log.e("customerId", customerId+"");
		Log.e("GET_ADDRESS_LIST", GET_ADDRESS_LIST);
	}
	public static void getStaffList(Context context, int agentId, int page,
			int rows, HttpCallback callback) {
		RequestParams params=new RequestParams();
		//Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("page", page);
		params.put("rows", rows);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(GET_STAFFMANAGER_LIST, params);
		Log.e("params", String.valueOf(params));
		Log.e("url", GET_MSGLIST);
	}
	public static void AddAdress(Context context, String cityId,
			String receiver,String moblephone,String zipCode,
			String address,int isDefault,int customerId,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		params.put("cityId", cityId);
		params.put("receiver", receiver);
		params.put("moblephone", moblephone);
		params.put("zipCode", zipCode);
		params.put("address", address);
		params.put("isDefault", isDefault);
		params.put("customerId", customerId);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(ADD_ADDRESS, params);
		Log.e("params", params+"");
		Log.e("ADD_ADDRESS", ADD_ADDRESS);
	}
	public static void addStaffList(Context context, 
			String userName,
			String loginId,
			String roles,
			int agentsId,
			String pwd,
			String pwd1,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		//Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("loginId", loginId);
		params.put("roles", roles);
		params.put("agentsId", agentsId);
		params.put("pwd", pwd);
		params.put("pwd1", pwd1);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(ADD_STAFFMANAGER, params);
	}
	public static void changePaw(Context context,
            int customerId, 
            String passwordOld,
            String password, 
            HttpCallback callback) {
		RequestParams params=new RequestParams();
		//Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("passwordOld", passwordOld);
		params.put("password", password);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(CHANGE_PAW, params);
	}
	public static void GetAgentList(Context context, int agentsId,
			int page,
			int rows,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
			params.put("agentsId", agentsId);
			params.put("page", page);
			params.put("rows", rows);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(AGENT_LIST, params);
	}
	public static void GetAgentDetail(Context context, int sonAgentsId,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
			params.put("sonAgentsId", sonAgentsId);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(AGENT_DETAIL, params);
	}
	public static void resetprofit(Context context, int defaultProfit,
			int agentsId,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
			params.put("defaultProfit", defaultProfit);
			params.put("agentsId", agentsId);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(RESET_PROFIT, params);
	}
	public static void changeAdres(Context context, 
			int id, 
			String cityId,
			String receiver, 
			String moblephone, 
			String zipCode, 
			String address,
			int customerId,
			int isDefault, HttpCallback callback) {
		RequestParams params=new RequestParams();
		params.put("id", id);
		params.put("cityId", cityId);
		params.put("receiver", receiver);
		params.put("moblephone", moblephone);
		params.put("zipCode", zipCode);
		params.put("address", address);
		params.put("customerId", customerId);
		params.put("isDefault", isDefault);
		params.setUseJsonStreamer(true);
		Log.e("params", String.valueOf(params));
		new HttpRequest(context, callback).post(EDIT_ADDRESS, params);
	}
	public static void uploadPic(Context context, 
			File img,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		try {
			params.put("img", img);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new HttpRequest(context, callback).post(UPLOAD_FILE, params);
	}
	public static void creatAgent(Context context, 
			int agentsId, 
			String loginId,
			int agentType, 
			String agentName,
			String agentCardId,
			String companyName,
			String companyId,
			String phoneNum,
			String emailStr,
			String addressStr,
			String pwd,
			String pwd1,
			int isProfit,
			int cityId,
			String cardPhotoPath,
			String licensePhotoPath,
			String taxPhotoPath,
			String taxNumStr,
			HttpCallback callback) {
		//RequestParams params=new RequestParams();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentsId", agentsId);
		params.put("loginId", loginId);
		params.put("agentType", agentType);
		params.put("agentName", agentName);
		params.put("agentCardId", agentCardId);
		params.put("companyName", companyName);
		params.put("companyId", companyId);
		params.put("phoneNum", phoneNum);
		params.put("emailStr", emailStr);
		params.put("addressStr", addressStr);
		params.put("pwd", pwd);
		params.put("pwd1", pwd1);
		params.put("isProfit", isProfit);
		params.put("cityId", cityId);
		params.put("cardPhotoPath", cardPhotoPath);
		params.put("licensePhotoPath", licensePhotoPath);
		params.put("taxPhotoPath", taxPhotoPath);
		params.put("taxNumStr", taxNumStr);
		//params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(CREAT_AGENT, params);
		Log.e("params", String.valueOf(params));
		Log.e("url", GET_MSGLIST);
	}
	public static void getDistributeList(Context context, 
			int agentId, 
			int sonAgentId, 
			String startTime,
			String endTime,
			int page,
			int rows,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		//Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("sonAgentId", sonAgentId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("page", page);
		params.put("rows", rows);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(GET_DISTRIBUTE, params);
		//Log.e("params", String.valueOf(params));
		//Log.e("url", GET_DISTRIBUTE);
	}
	public static void getlowerAgentList(Context context, 
			int agentId, 
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		//Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(GET_AGENTLIST, params);
		Log.e("params", String.valueOf(params));
		Log.e("url", GET_DISTRIBUTE);
	}
	public static void getDistributeDetail(Context context, 
			int id, 
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		//Map<String, Object> params = new HashMap<String, Object>();
		params.put("ageidntId", id);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(GET_DISTRIBUTE_DETAIL, params);
		Log.e("params", String.valueOf(params));
		Log.e("url", GET_DISTRIBUTE_DETAIL);
	}
	public static void getTranslist(Context context, 
			int agentId, 
			int sonAgentId, 
			String startTime,
			String endTime,
			int page,
			int rows,
			HttpCallback callback) {
		RequestParams params=new RequestParams();
		//Map<String, Object> params = new HashMap<String, Object>();
		params.put("agentId", agentId);
		params.put("sonAgentId", sonAgentId);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		params.put("page", page);
		params.put("rows", rows);
		params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(GET_TRANS_LIST, params);
		//Log.e("params", String.valueOf(params));
		//Log.e("url", GET_DISTRIBUTE);
	}
	public static void insertCustomer(Context context, 
			String userName, 
			String loginId, 
			String roles,
			int agentsId,
			String pwd,
			String pwd1,
			HttpCallback callback) {
		//RequestParams params=new RequestParams();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		params.put("loginId", loginId);
		params.put("roles", roles);
		params.put("agentsId", agentsId);
		params.put("pwd", pwd);
		params.put("pwd1", pwd1);
		//params.setUseJsonStreamer(true);
		new HttpRequest(context, callback).post(INSERT_CUSTOMER, params);
		Log.e("params", String.valueOf(params));
		Log.e("url", INSERT_CUSTOMER);
	}
}
